package ra.ecommerce_store_01.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "Banner")
@Data
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "BannerId")
    private int bannerId;
    @JoinColumn(name = "Content", nullable = false)
    private String content;
    @JoinColumn(name = "Image", nullable = false)
    private String image;
    @JoinColumn(name = "BannerStatus")
    private boolean bannerStatus;
}
