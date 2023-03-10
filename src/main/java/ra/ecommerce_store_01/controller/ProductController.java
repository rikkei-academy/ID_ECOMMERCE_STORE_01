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
import ra.ecommerce_store_01.model.entity.Catalog;
import ra.ecommerce_store_01.model.entity.Image;
import ra.ecommerce_store_01.model.entity.Product;
import ra.ecommerce_store_01.model.service.CatalogService;
import ra.ecommerce_store_01.model.service.ImageService;
import ra.ecommerce_store_01.model.service.ProductService;
import ra.ecommerce_store_01.payload.request.ProductModel;
import ra.ecommerce_store_01.payload.respone.MessageResponse;

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

    @DeleteMapping("/{productId}")
    public void deleteProduct(@RequestParam int productId) {
        productService.delete(productId);
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

    @PostMapping("/creatNew")
    public ResponseEntity<?> creatNew(@RequestBody ProductModel model) {
        try {
            Product product = new Product();
            product.setProductId(model.getProductId());
            product.setProductName(model.getProductName());
            product.setPrice(model.getPrice());
            product.setImageLink(model.getImageLink());
            product.setDelivery(model.isDelivery());
            product.setDescription(model.getDescription());
            product.setProductStatus(true);
            Catalog catalog = catalogService.findById(model.getCatalogId());
            product.setCatalog(catalog);
            productService.saveOrUpdate(product);
            for (String str: model.getListImg()) {
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
    @PutMapping("{productID}")
    public ResponseEntity<?> updateProduct(@PathVariable("productID") int productId, @RequestBody ProductModel productRequest) {
        try {
            Catalog catalog = catalogService.findById(productRequest.getCatalogId());
            Product productUpdate = productService.findById(productId);
            productUpdate.setProductId(productRequest.getProductId());
            productUpdate.setProductName(productRequest.getProductName());
            productUpdate.setPrice(productRequest.getPrice());
            productUpdate.setImageLink(productRequest.getImageLink());
            productUpdate.setDelivery(productRequest.isDelivery());
            productUpdate.setDescription(productRequest.getDescription());
            productUpdate.setProductStatus(productRequest.isProductStatus());
            productUpdate.setCatalog(catalog);
            productService.saveOrUpdate(productUpdate);
            if (productRequest.getListImg()==null) {
                productUpdate.setListImage(productUpdate.getListImage());
            } else {
                for (Image image : productUpdate.getListImage()) {
                    imageService.delete(image.getImageId());
                }
                for (String str: productRequest.getListImg()) {
                    Image image = new Image();
                    image.setImageLink(str);
                    image.setProduct(productUpdate);
                    imageService.saveOrUpdate(image);
                }
            }
            return ResponseEntity.ok(new MessageResponse("Update product successfully!"));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Update failed! Please try again!"));
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

    @GetMapping("/getByProductId")
    public ResponseEntity<?> getByProductId(int productId) {
        Product pr = productService.findById(productId);
        return ResponseEntity.ok(pr);
    }
}
