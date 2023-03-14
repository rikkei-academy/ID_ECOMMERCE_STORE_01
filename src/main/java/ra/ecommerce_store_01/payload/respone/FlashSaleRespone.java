package ra.ecommerce_store_01.payload.respone;

import lombok.Data;

import java.util.Date;

@Data
public class FlashSaleRespone {
    private Date starTime;
    private Date endTime;
    private String  title;
    private String descriptions;
}
