package ra.ecommerce_store_01.model.serviveImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ra.ecommerce_store_01.model.entity.Review;
import ra.ecommerce_store_01.model.repository.ReviewRepositiry;
import ra.ecommerce_store_01.model.service.ReviewService;

import java.util.List;

@Service
public class ReviewServiceImp implements ReviewService {
    @Autowired
    private ReviewRepositiry reviewRepositiry;

    @Override
    public Review saveOrUpdate(Review review) {
        return reviewRepositiry.save(review);
    }

    @Override
    public Review findById(int reviewId) {
        return reviewRepositiry.findById(reviewId).get();
    }

    @Override
    public List<Review> findAll() {
        return reviewRepositiry.findAll();
    }

    @Override
    public Page<Review> getPaging(Pageable pageable) {
        return reviewRepositiry.findAll(pageable);
    }

    @Override
    public void deleteReview(int reviewId) {
         reviewRepositiry.delete(reviewRepositiry.findById(reviewId).get());
    }

    @Override
    public Page<Review> getReviewByProductId(int proId, Pageable pageable) {
        return reviewRepositiry.findReviewsByProductProductId(proId,pageable);
    }

    @Override
    public Page<Review> getReviewByUserId(int userId, Pageable pageable) {
        return reviewRepositiry.findReviewsByUserUserId(userId,pageable);
    }
}
