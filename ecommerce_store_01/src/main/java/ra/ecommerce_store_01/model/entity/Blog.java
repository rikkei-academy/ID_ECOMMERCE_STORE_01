package ra.ecommerce_store_01.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "BlogID")
    private int blogId;
    @JoinColumn(name = "BlogName")
    private String blogName;
    @JoinColumn(name = "BlogContent")
    private String blogContent;
    @JoinColumn(name = "Created")
    private Date createdDate;
    @JoinColumn(name = "Tag")
    private String tag;
    @JoinColumn(name = "BlogStatus")
    private boolean blogStatus;
    @ManyToOne(fetch = FetchType.EAGER)
    private BlogCatalog blogCatalog;

    @OneToMany(mappedBy = "blog")
    private List<Comment> listComment = new ArrayList<>();

}
