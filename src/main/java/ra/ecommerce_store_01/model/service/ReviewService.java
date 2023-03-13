package ra.ecommerce_store_01.model.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ra.ecommerce_store_01.model.entity.Review;
import java.util.List;
public interface ReviewService {
    Review saveOrUpdate(ra.ecommerce_store_01.model.entity.Review review);
    Review findById(int reviewId);
    List<Review> findAll();
    Page<Review> getPaging(Pageable pageable);

    void deleteReview(int reviewId);
    Page<Review> getReviewByProductId(int proId,Pageable pageable);
    Page<Review> getReviewByUserId(int userId,Pageable pageable);
}
