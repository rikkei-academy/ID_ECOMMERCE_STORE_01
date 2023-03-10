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
import ra.ecommerce_store_01.model.entity.Product;
import ra.ecommerce_store_01.model.service.CatalogService;
import ra.ecommerce_store_01.model.service.ProductService;
import ra.ecommerce_store_01.payload.request.ProductModel;

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
    @GetMapping
    public List<Product> getAllProduct(){
        return productService.findAll();
    }
    @DeleteMapping("/{productId}")
    public void deleteProduct(@RequestParam int productId){
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
        return ResponseEntity.ok("Creat new product successfully");
    }
//    @GetMapping("/searchByName")
//    public List<Product> searchProductByName(@RequestParam String productName){
//        return productService.findByName(productName);
//    }
    @GetMapping("/getByProductId")
    public ResponseEntity<?> getByProductId(int productId){
        Product pr=  productService.findById(productId);
        return ResponseEntity.ok(pr);
    }

}
