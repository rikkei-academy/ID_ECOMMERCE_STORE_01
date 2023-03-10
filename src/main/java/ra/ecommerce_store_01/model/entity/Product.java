package ra.ecommerce_store_01.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "ProductID")

    private int productId;
    @JoinColumn(name = "ProductName")
    private String productName;
    @JoinColumn(name = "Image")
    private String imageLink;
    @JoinColumn(name = "Price")
    private float price;
    @JoinColumn(name = "Delivery")
    private boolean delivery;
    @JoinColumn(name = "Descriptions")
    private String description;
    @JoinColumn(name = "ProductStatus")
    private boolean productStatus;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CatalogID")
    private Catalog catalog;
    @OneToMany(mappedBy = "product")
    private List<ProductLocation> listProductLocation = new ArrayList<>();
    @OneToMany(mappedBy = "product")
    private List<Star> listStar = new ArrayList<>();
    @OneToMany(mappedBy = "product")
    private List<Image> listImage = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<Voucher> listVoucher = new ArrayList<>();
    @OneToMany(mappedBy = "product")
    private List<Review> listReview = new ArrayList<>();
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "brand_brandId")
    private Brand brand;
}
