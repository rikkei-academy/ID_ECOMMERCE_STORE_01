package ra.ecommerce_store_01.payload.request;

import lombok.Data;

import java.util.Date;

@Data
public class FlashSaleRequest {
    private Date starTime;
    private Date endTime;
    private String  title;
    private String descriptions;
}
