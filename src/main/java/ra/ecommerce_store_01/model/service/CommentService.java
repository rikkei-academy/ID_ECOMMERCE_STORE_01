package ra.ecommerce_store_01.model.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ra.ecommerce_store_01.model.entity.Comment;

import java.util.List;

public interface CommentService {
    Comment saveOrUpdate(Comment comment);
    Comment findById(int commentId);
    List<Comment> findAll();
    Page<Comment> searchByName(String commentName,Pageable pageable);
    Page<Comment> getPaging(Pageable pageable);
    boolean blockComment(int id);
    boolean unBlockComment(int id);
    List<Comment> findAllByUserId(int userId);
    List<Comment> findAllByBlogId(int blogId);
}
