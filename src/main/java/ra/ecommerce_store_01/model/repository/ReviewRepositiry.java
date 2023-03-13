package ra.ecommerce_store_01.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.ecommerce_store_01.model.entity.Review;




@Repository
public interface ReviewRepositiry extends JpaRepository<Review,Integer> {
   Page<Review> findReviewsByProductProductId(int proId, Pageable pageable);
   Page<Review> findReviewsByUserUserId(int userId,Pageable pageable);
}
