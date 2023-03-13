package ra.ecommerce_store_01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.ecommerce_store_01.model.entity.Location;
import ra.ecommerce_store_01.model.entity.Product;
import ra.ecommerce_store_01.model.entity.ProductLocation;
import ra.ecommerce_store_01.model.service.LocationService;
import ra.ecommerce_store_01.model.service.ProductLocationService;
import ra.ecommerce_store_01.model.service.ProductService;
import ra.ecommerce_store_01.payload.request.ProductLocationRequest;
import ra.ecommerce_store_01.payload.respone.MessageResponse;
import ra.ecommerce_store_01.payload.respone.ProductLocationResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/v1/productLocation")
public class ProductLocationController {
    @Autowired
    private ProductLocationService productLocationService;
    @Autowired
    private ProductService productService;
    @Autowired
    private LocationService locationService;

    @PostMapping
    public ResponseEntity<?> createProductLocation(@RequestBody ProductLocationRequest productLocationRequest) {
        try {
            ProductLocation productLocation = new ProductLocation();
            Product product = productService.findById(productLocationRequest.getProductId());
            Location location = locationService.findById(productLocationRequest.getLocationId());
            productLocation.setProductLocationStatus(true);
            productLocation.setQuantity(productLocationRequest.getQuantity());
            productLocation.setDiscount(productLocationRequest.getDiscount());
            productLocation.setLocation(location);
            productLocation.setProduct(product);
            productLocationService.saveOrUpdate(productLocation);
            return ResponseEntity.ok(new MessageResponse("Create successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Create failed!"));
        }
    }

    @DeleteMapping("{productLocationId}")
    public ResponseEntity<?> deleteProductLocation(@PathVariable("productLocationId") int productLocationId) {
        try {
            productLocationService.deleteProductFromLocation(productLocationId);
            return ResponseEntity.ok(new MessageResponse("Delete successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Delete failed!"));
        }
    }

    @PatchMapping("changeStatus/{productLocationId}")
    public ResponseEntity<?> changeProductLocationStatus(@PathVariable("productLocationId") int productLocationId, @RequestParam("action") String action) {
        try {
            ProductLocation productLocation = productLocationService.findById(productLocationId);
            if (action.equals("lock")) {
                productLocation.setProductLocationStatus(false);
                productLocationService.saveOrUpdate(productLocation);
            }
            if (action.equals("unlock")) {
                productLocation.setProductLocationStatus(true);
                productLocationService.saveOrUpdate(productLocation);
            }
            return ResponseEntity.ok(new MessageResponse("Change product location status successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Change product location status failed!"));
        }
    }

    @GetMapping("filterByLocation/{locationId}")
    public ResponseEntity<?> filterByLocation(@PathVariable("locationId") int locationId,
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "3") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductLocation> paging = productLocationService.getPaging(pageable);
        List<ProductLocation> listProductLocation = productLocationService.filterProductByLocation(locationId, pageable);
        List<ProductLocationResponse> listProductResponse = new ArrayList<>();
        for (ProductLocation product : listProductLocation) {
            ProductLocationResponse pro = new ProductLocationResponse();
            pro.setDiscount(product.getDiscount());
            pro.setLocationName(product.getLocation().getLocationName());
            pro.setProductName(product.getProduct().getProductName());
            pro.setQuantity(product.getQuantity());
            pro.setProductLocationStatus(product.isProductLocationStatus());
            listProductResponse.add(pro);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("listProduct", listProductResponse);
        data.put("totalPages", paging.getTotalPages());
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
