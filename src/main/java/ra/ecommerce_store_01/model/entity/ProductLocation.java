package ra.ecommerce_store_01.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "productlocation")
public class ProductLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "ProductLocationID")
    private int productLocationId;
    @JoinColumn(name = "Quantity")
    private int quantity;
    @JoinColumn(name = "discount")
    private float discount;
    @JoinColumn(name = "ProductLocationStatus")
    private boolean productLocationStatus;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "ProductID")
    private Product product;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "LocationID")
    private Location location;
}
