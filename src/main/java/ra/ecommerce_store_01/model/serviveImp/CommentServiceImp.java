package ra.ecommerce_store_01.model.serviveImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ra.ecommerce_store_01.model.entity.Comment;
import ra.ecommerce_store_01.model.repository.CommentRepository;
import ra.ecommerce_store_01.model.service.CommentService;

import java.util.List;

@Service
public class CommentServiceImp implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Override
    public Comment saveOrUpdate(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment findById(int commentId) {
        return commentRepository.findById(commentId).get();
    }

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Page<Comment> searchByName(String commentName, Pageable pageable) {
        return commentRepository.findCommentByCommentId(commentName,pageable);
    }


    @Override
    public Page<Comment> getPaging(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }

    @Override
    public boolean blockComment(int id) {
        Comment comment = commentRepository.findById(id).get();
        comment.setCommentStatus(false);
        try {
            commentRepository.save(comment);
            return true;
        } catch (Exception ex){
            return false;
        }
    }

    @Override
    public boolean unBlockComment(int id) {
        Comment comment = commentRepository.findById(id).get();
        comment.setCommentStatus(true);
        try {
            commentRepository.save(comment);
            return true;
        } catch (Exception ex){
            return false;
        }
    }

    @Override
    public List<Comment> findAllByUserId(int userId) {
        return commentRepository.findAllByUser_UserId(userId);
    }

    @Override
    public List<Comment> findAllByBlogId(int blogId) {
        return commentRepository.findAllByBlog_BlogId(blogId);
    }
}
