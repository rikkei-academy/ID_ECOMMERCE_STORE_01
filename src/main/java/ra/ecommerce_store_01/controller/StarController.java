package ra.ecommerce_store_01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ra.ecommerce_store_01.model.entity.Product;
import ra.ecommerce_store_01.model.entity.Star;
import ra.ecommerce_store_01.model.entity.User;
import ra.ecommerce_store_01.model.service.ProductService;
import ra.ecommerce_store_01.model.service.StarService;
import ra.ecommerce_store_01.model.service.UserService;
import ra.ecommerce_store_01.payload.request.StarRequest;
import ra.ecommerce_store_01.payload.respone.MessageResponse;
import ra.ecommerce_store_01.security.CustomUserDetails;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/v1/star")
public class StarController {
    @Autowired
    private StarService starService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<?> createStar(@RequestBody StarRequest starRequest) {
        try {
            Product product = productService.findById(starRequest.getProductId());
            CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userService.findByUserId(userDetails.getUserId());
            Star star = new Star();
            star.setStar(starRequest.getStar());
            star.setProduct(product);
            star.setUser(user);
            starService.save(star);
            return ResponseEntity.ok(new MessageResponse("Đánh giá thành công"));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed!");
        }
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getStar(@PathVariable("productId") int productId) {
        List<Star> listStar = starService.countStar(productId);
        float getStar = 0f;
        for (Star star:listStar) {
            getStar += star.getStar();
        }
        float star = getStar/listStar.size();
        return ResponseEntity.ok(star);
    }
}