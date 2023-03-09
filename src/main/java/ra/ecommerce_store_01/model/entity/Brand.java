package ra.ecommerce_store_01.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "brand")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "brandId")
    private int brandId;
    @JoinColumn(name = "brandName")
    private String brandName;
    @JoinColumn(name = "brandStatus")
    private boolean brandStatus;
    @OneToMany(mappedBy = "brand")
    private List<Product> listProduct;

}
