package ra.ecommerce_store_01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ra.ecommerce_store_01.model.entity.Review;
import ra.ecommerce_store_01.model.service.ProductService;
import ra.ecommerce_store_01.model.service.ReviewService;
import ra.ecommerce_store_01.model.service.UserService;
import ra.ecommerce_store_01.payload.request.ReviewModel;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/v1/review")
public class ReviewControler {
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @GetMapping
    public List<Review> getAllReview(){
        return reviewService.findAll();
    }
    @GetMapping("/{reviewId}")
    public Review getById(@PathVariable int reviewId){
        return reviewService.findById(reviewId);
    }
    @PostMapping("create")
    @PreAuthorize("hasRole('USER')")
    public Review CreateReview(@RequestBody ReviewModel reviewModel){
        Review review = new Review();
        review.setContent(reviewModel.getContent());
        review.setCreateDate(new Date());
        review.setUser(userService.findByUserId(reviewModel.getUserId()));
        review.setProduct(productService.findById(reviewModel.getProductId()));
        return reviewService.saveOrUpdate(review);
    }
    @PatchMapping("/update/{reviewId}")
    @PreAuthorize("hasRole('USER')")
    public Review updateReview(@PathVariable int reviewId,@RequestBody ReviewModel reviewModel){
        Review reviewUpdate = reviewService.findById(reviewId);
        reviewUpdate.setContent(reviewModel.getContent());
        reviewUpdate.setProduct(productService.findById(reviewModel.getProductId()));
        reviewUpdate.setUser(userService.findByUserId(reviewModel.getUserId()));
        reviewUpdate.setCreateDate(new Date());
        return reviewService.saveOrUpdate(reviewUpdate);
    }
    @DeleteMapping("/{reviewId}")
    @PreAuthorize("hasRole('USER')")
    public void deleteReview(@PathVariable int reviewId){
         reviewService.deleteReview(reviewId);
    }
    @GetMapping("/getByProductId/{productId}")
    public ResponseEntity<Map<String,Object>> getByProductId(@PathVariable int productId,
                                                             @RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int size
                                                             ){
        Pageable pageable = PageRequest.of(page,size);
        Page<Review> reviews = reviewService.getReviewByProductId(productId,pageable);
        Map<String,Object> data = new HashMap<>();
        data.put("reviews",reviews.getContent());
        data.put("total",reviews.getSize());
        data.put("totalItems",reviews.getTotalElements());
        data.put("totalPages",reviews.getTotalPages());
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    @GetMapping("/paging")
    public ResponseEntity<?> getPaging(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Review> reviews = reviewService.getPaging(pageable);
        Map<String, Object> data = new HashMap<>();
        data.put("review", reviews.getContent());
        data.put("total",reviews.getSize());
        data.put("totalItems", reviews.getTotalElements());
        data.put("totalPages", reviews.getTotalPages());
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    @GetMapping("/getByUserId/{userId}")
    public ResponseEntity<Map<String,Object>> getByUserId(@PathVariable int userId,
                                                             @RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page,size);
        Page<Review> reviews = reviewService.getReviewByUserId(userId,pageable);
        Map<String,Object> data = new HashMap<>();
        data.put("reviews",reviews.getContent());
        data.put("total",reviews.getSize());
        data.put("totalItems",reviews.getTotalElements());
        data.put("totalPages",reviews.getTotalPages());
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
