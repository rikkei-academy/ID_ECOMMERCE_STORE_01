package ra.ecommerce_store_01.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "FlashSaleOrder")
public class FlashSaleOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "FlashSaleOrderId")
    private int flashSaleOrderId;
    @JoinColumn(name = "Quantity")
    private int quantity;
    @JoinColumn(name ="Price")
    private float price;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ItemFlashSaleId")
    private ItemFlashSale itemFlashSale;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "OrderID")
    @JsonIgnore
    private Orders orders;
}
