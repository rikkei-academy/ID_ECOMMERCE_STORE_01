package ra.ecommerce_store_01.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "voucher")
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "VoucherID")
    private int voucherId;
    @JoinColumn(name = "Quantity", nullable = false)
    private int quantity;
    @JoinColumn(name = "discount", nullable = false)
    private float discount;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ProductID")
    private Product product;
}
