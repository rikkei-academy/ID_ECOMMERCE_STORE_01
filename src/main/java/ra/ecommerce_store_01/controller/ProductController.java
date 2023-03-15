package ra.ecommerce_store_01.controller;

import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ra.ecommerce_store_01.model.entity.*;
import ra.ecommerce_store_01.model.service.*;
import ra.ecommerce_store_01.payload.request.ProductModel;
import ra.ecommerce_store_01.payload.respone.MessageResponse;
import ra.ecommerce_store_01.payload.respone.ProductResponse;

import java.util.HashMap;


import java.util.List;
import java.util.Map;


@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/api/v1/product")
@RestController
@AllArgsConstructor
public class ProductController {
    private CatalogService catalogService;
    private ProductService productService;
    private ImageService imageService;
    private BrandService brandService;
    @GetMapping
    public List<Product> getAllProduct() {
        return productService.findAll();
    }

    @PatchMapping("/delete/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteProduct(@PathVariable int productId) {
        boolean check = productService.deleteProduct(productId);
        if (check) {
           return ResponseEntity.ok("Xoa thanh cong");
        } else {
            return ResponseEntity.ok("Xoa that bai");
        }
    }


    @GetMapping("/getPaggingAndSortByName")
    public ResponseEntity<Map<String, Object>> getPaggingAndSortByName(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10 ") int size,
            @RequestParam String direction) {
        Sort.Order order;
        if (direction.equals("asc")) {
            order = new Sort.Order(Sort.Direction.ASC, "productName");
        } else {
            order = new Sort.Order(Sort.Direction.DESC, "productName");
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(order));
        Page<Product> pageProduct = productService.getPagging(pageable);
        Map<String, Object> data = new HashMap<>();
        data.put("products", pageProduct.getContent());
        data.put("total", pageProduct.getSize());
        data.put("totalItems", pageProduct.getTotalElements());
        data.put("totalPages", pageProduct.getTotalPages());
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> creatNew(@RequestBody ProductModel productModel) {
        try {
            Product product = new Product();
            product.setProductName(productModel.getProductName());
            product.setPrice(productModel.getPrice());
            product.setImageLink(productModel.getImageLink());
            product.setDelivery(productModel.isDelivery());
            product.setDescription(productModel.getDescription());
            product.setProductStatus(true);
            Catalog catalog = catalogService.findById(productModel.getCatalogId());
            product.setCatalog(catalog);
            Brand brand = brandService.findById(productModel.getBrandId());
            product.setBrand(brand);
            productService.saveOrUpdate(product);
            for (String str: productModel.getListImg()) {
                Image image = new Image();
                image.setImageLink(str);
                image.setProduct(product);
                imageService.saveOrUpdate(image);
            }
            return ResponseEntity.ok("Creat new product successfully");
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new MessageResponse("Create failed! Please try again!"));
        }
    }
    @PatchMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateProduct(@RequestParam("productId") int productId, @RequestBody ProductModel productModel) {
      try {
          Product product = productService.findById(productId);
          product.setProductName(productModel.getProductName());
          product.setPrice(productModel.getPrice());
          product.setImageLink(productModel.getImageLink());
          product.setDelivery(productModel.isDelivery());
          product.setDescription(productModel.getDescription());
          Catalog catalog = catalogService.findById(productModel.getCatalogId());
          product.setCatalog(catalog);
          Brand brand = brandService.findById(productModel.getBrandId());
          product.setBrand(brand);
          productService.saveOrUpdate(product);
          if (productModel.getListImg()==null) {
              product.setListImage(product.getListImage());
          } else {
              for (Image image : product.getListImage()) {
                  imageService.delete(image.getImageId());
              }
              for (String str: productModel.getListImg()) {
                  Image image = new Image();
                  image.setImageLink(str);
                  image.setProduct(product);
                  imageService.saveOrUpdate(image);
              }
          }
          return ResponseEntity.ok("Creat new product successfully");
       } catch (Exception e){
          e.printStackTrace();
          return ResponseEntity.ok("Update failed! Please try again!");
      }
    }

    @GetMapping("/searchByName")
    public ResponseEntity<Map<String, Object>> searchProductByName(@RequestParam String productName, @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10 ") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> pageProduct = productService.findByName(pageable,productName);
        Map<String, Object> data = new HashMap<>();
        data.put("products", pageProduct.getContent());
        data.put("total", pageProduct.getSize());
        data.put("totalItems", pageProduct.getTotalElements());
        data.put("totalPages", pageProduct.getTotalPages());
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getByProductId(@PathVariable int productId) {
        Product pr = productService.findById(productId);
        pr.setViews(pr.getViews()+1); //tăng lượt views lên 1
        try {
            pr = productService.saveOrUpdate(pr);
            ProductResponse productResponse = new ProductResponse();
            productResponse.setProductId(pr.getProductId());
            productResponse.setProductName(pr.getProductName());
            productResponse.setDelivery(pr.isDelivery());
            productResponse.setImageLink(pr.getImageLink());
            productResponse.setPrice(pr.getPrice());
            productResponse.setDescription(pr.getDescription());
            productResponse.setBrandId(pr.getBrand().getBrandId());
            productResponse.setBrandName(pr.getBrand().getBrandName());
            productResponse.setCatalogId(pr.getCatalog().getCatalogId());
            productResponse.setCatalogName(pr.getCatalog().getCatalogName());
            productResponse.setViews(pr.getViews());
            for (Image image :pr.getListImage()) {
                productResponse.getListImage().add(image.getImageLink());
            }
            return ResponseEntity.ok(productResponse);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.ok("có lỗi trong quá trình xử lý vui lòng thử lại!");
        }
    }
    @GetMapping("searchByCatalogId/{catalogId}")
    public ResponseEntity<Map<String,Object>> searchByCatalogId(@PathVariable int catalogId,
                                                                @RequestParam(defaultValue ="0") int page,
                                                                @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Product> pageProduct = productService.findByCatalog_CatalogId(catalogId,pageable);
        Map<String,Object> data = new HashMap<>();
        data.put("products", pageProduct.getContent());
        data.put("total",pageProduct.getSize());
        data.put("totalItems",pageProduct.getTotalElements());
        data.put("totalPages",pageProduct.getTotalPages());
        return new  ResponseEntity<>(data,HttpStatus.OK);
    }

        @GetMapping("/searchByPriceBetween")
    public ResponseEntity<Map<String,Object>> searchByPrice(@RequestParam float starPrice,@RequestParam float endPrice,
                                           @RequestParam(defaultValue ="0") int page,
                                           @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<Product> products = productService.searchProductByPriceBetween(starPrice,endPrice,pageable);
        Map<String,Object> data = new HashMap<>();
        data.put("products",products.getContent());
        data.put("total",products.getSize());
        data.put("totalItems",products.getTotalElements());
        data.put("totalPages",products.getTotalPages());
        return new ResponseEntity<>(data,HttpStatus.OK);
    }



    /*
       Get featured Products
       output: List<Product>
     */
    @GetMapping("featuredProducts")
    public ResponseEntity<?> featuredProducts(){
        List<ProductResponse> list = productService.featuredProducts();
        return ResponseEntity.ok(list);
    }
}

