package ra.ecommerce_store_01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.ecommerce_store_01.model.entity.Catalog;
import ra.ecommerce_store_01.model.entity.Comment;
import ra.ecommerce_store_01.model.service.BlogService;
import ra.ecommerce_store_01.model.service.CommentService;
import ra.ecommerce_store_01.model.service.UserService;
import ra.ecommerce_store_01.payload.request.CommentModel;
import ra.ecommerce_store_01.payload.request.CommentUpdate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/v1/comment")
public class CommentControler {
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private BlogService blogService;

    @GetMapping
    public List<Comment> getAllComment() {
        return commentService.findAll();
    }

    @GetMapping("/findById")
    public Comment getCommentById(@RequestParam int commentId) {
        return commentService.findById(commentId);
    }

    @PatchMapping("/update")
    public Comment updateComment(@RequestParam int commentId, @RequestBody CommentUpdate commentUpdate) {
        Comment comment = commentService.findById(commentId);
        comment.setComment(commentUpdate.getComment());
        comment.setCreatedDate(new Date());
        comment.setUser(userService.findByUserId(commentUpdate.getUserId()));
        comment.setBlog(blogService.getById(commentUpdate.getBlogId()));
        return commentService.saveOrUpdate(comment);
    }

    @PostMapping("/creatComment")
    public Comment createComment(@RequestBody CommentModel commentModel) {
        Comment newComment = new Comment();
        newComment.setComment(commentModel.getComment());
        newComment.setCreatedDate(new Date());
        newComment.setCommentStatus(true);
        newComment.setUser(userService.findByUserId(commentModel.getUserId()));
        newComment.setBlog(blogService.getById(commentModel.getBlogId()));
        return commentService.saveOrUpdate(newComment);
    }

    @PatchMapping("/blockComment")
    public ResponseEntity<?> blockComment(@RequestParam int commentId) {
        boolean check = commentService.blockComment(commentId);
        if (check) {
            return ResponseEntity.ok("Da khoa comment thanh cong");
        } else {
            return ResponseEntity.ok("Khoa comment that bai");
        }
    }

    @PatchMapping("/unBlockComment")
    public ResponseEntity<?> unBlockComment(@RequestParam int commentId) {
        Comment comment = commentService.findById(commentId);
        if (comment.isCommentStatus()) {
            return ResponseEntity.ok("Comment chua bi khoa");
        } else {
            boolean check = commentService.unBlockComment(commentId);
            if (check) {
                return ResponseEntity.ok("Da mo khoa comment thanh cong");
            } else {
                return ResponseEntity.ok("Mo khoa comment that bai");
            }
        }
    }

    @GetMapping("getCommentByUserId")
    public List<Comment> getCommentByUserId(@RequestParam int userId) {
        return commentService.findAllByUserId(userId);
    }
    @GetMapping("getCommentByBlogId")
    public List<Comment> getCommentByBlogId(@RequestParam int blogId) {
        return commentService.findAllByBlogId(blogId);
    }
    @GetMapping("paging")
    public ResponseEntity<?> getPaging(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<Comment> comments = commentService.getPaging(pageable);
        Map<String,Object> data = new HashMap<>();
        data.put("comment",comments.getContent());
        data.put("totalPages",comments.getTotalPages());
        return  new ResponseEntity<>(data, HttpStatus.OK);
    }
}
