package ra.ecommerce_store_01.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "orderdetail")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "OrderDetailID")
    private int orderDetailId;
    @JoinColumn(name = "Price")
    private float price;
    @JoinColumn(name = "OrderDetailStatus")
    private int orderDetailStatus;
    @JoinColumn(name = "Quantity")
    private int quantity;
    @JoinColumn(name = "TotalPrice")
    private float totalPrice;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "ProductID")
    private Product product;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "orderId")
    private Orders orders;

}
