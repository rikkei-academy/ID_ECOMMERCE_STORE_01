package ra.ecommerce_store_01.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "blogcatalog")
public class BlogCatalog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "BlogCatalogID")
    private int blogCatalogId;
    @JoinColumn(name = "BlogCatalogName")
    private String blogCatalogName;
    @JoinColumn(name = "BlogCatalogStatus")
    private boolean blogCatalogStatus;
    @OneToMany(mappedBy = "blogCatalog")
    private List<Blog> listBlog = new ArrayList<>();
}
