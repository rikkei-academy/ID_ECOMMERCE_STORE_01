package ra.ecommerce_store_01.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@Table(name = "FlashSale")
public class FlashSale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "FlashSaleId")
    private int flashSaleId;
    @JoinColumn(name = "StarTime")
    private Date starTime;
    @JoinColumn(name = "EndTime")
    private Date endTime;
    @JoinColumn(name = "Title")
    private String  title;
    @JoinColumn(name = "Descriptions")
    private String descriptions;
    @JoinColumn(name = "Status")
    private boolean status;
    @OneToMany(mappedBy = "flashSale")
    List<ItemFlashSale> listItem = new ArrayList<>();
}
