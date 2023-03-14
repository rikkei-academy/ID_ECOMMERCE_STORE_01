package ra.ecommerce_store_01.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "brand")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "brandId")
    private int brandId;
    @JoinColumn(name = "brandName", nullable = false, unique = true)
    private String brandName;
    @JoinColumn(name = "brandStatus")
    private boolean brandStatus;
}
