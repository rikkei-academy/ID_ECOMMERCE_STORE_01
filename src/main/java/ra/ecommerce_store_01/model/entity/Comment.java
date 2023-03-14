package ra.ecommerce_store_01.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "CommentID")
    private int commentId;
    @JoinColumn(name = "Created")
    private Date createdDate;
    @JoinColumn(name = "CommentContent", nullable = false)
    private String comment;
    @JoinColumn(name = "CommentStatus")
    private boolean commentStatus;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Blog blog;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
}
