package ra.ecommerce_store_01.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "ImageID")
    private int imageId;
    @JoinColumn(name = "ImageLink")
    private String imageLink;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProductID")
    private Product product;
}
