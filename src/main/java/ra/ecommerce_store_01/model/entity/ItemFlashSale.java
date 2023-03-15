package ra.ecommerce_store_01.model.entity;

import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name ="ItemFlashSale")
@Data
public class ItemFlashSale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "ItemFlashSaleId")
    private int itemFlashSaleId;
    @JoinColumn(name = "Discount",nullable = false)
    private float discount;
    @JoinColumn(name ="Quantity",nullable = false)
    private int quantity;
    @JoinColumn(name = "SoldQuantity")
    private int soldQuantity;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER )
    @JoinColumn(name = "ProductID")
    private Product product;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FlashSaleId")
    private FlashSale flashSale;
}
