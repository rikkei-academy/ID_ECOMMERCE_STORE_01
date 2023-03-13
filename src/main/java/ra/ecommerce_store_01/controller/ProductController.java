package ra.ecommerce_store_01.controller;

import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.ecommerce_store_01.model.entity.Catalog;
import ra.ecommerce_store_01.model.entity.Product;
import ra.ecommerce_store_01.model.service.CatalogService;
import ra.ecommerce_store_01.model.service.ImageService;
import ra.ecommerce_store_01.model.service.ProductService;
import ra.ecommerce_store_01.payload.request.ProductModel;
import ra.ecommerce_store_01.payload.respone.MessageResponse;
import java.time.LocalDate;
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

    @GetMapping
    public List<Product> getAllProduct() {
        return productService.findAll();
    }

    @PatchMapping("/delete/{productId}")
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
            productService.saveOrUpdate(product);
            return ResponseEntity.ok("Creat new product successfully");
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new MessageResponse("Create failed! Please try again!"));
        }
    }
    @PatchMapping("/update")
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
          productService.saveOrUpdate(product);
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
        return ResponseEntity.ok(pr);
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
}

