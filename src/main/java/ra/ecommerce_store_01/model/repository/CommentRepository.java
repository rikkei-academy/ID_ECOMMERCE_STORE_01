package ra.ecommerce_store_01.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ra.ecommerce_store_01.model.entity.Comment;
import ra.ecommerce_store_01.model.entity.Product;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer> {
    Page<Comment> findCommentByCommentId(String productName, Pageable pageable);

    List<Comment> findAllByUser_UserId(int Id);
    List<Comment> findAllByBlog_BlogId(int Id);
}
