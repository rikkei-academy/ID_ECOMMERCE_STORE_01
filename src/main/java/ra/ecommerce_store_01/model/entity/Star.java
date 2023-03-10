package ra.ecommerce_store_01.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "Star")
public class Star {
    @Id
    @GeneratedValue
    @JoinColumn(name = "StarID")
    private int starId;
    @JoinColumn(name = "Star")
    private float Star;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "ProductID")
    private Product product;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "UserId")
    private User user;
}
