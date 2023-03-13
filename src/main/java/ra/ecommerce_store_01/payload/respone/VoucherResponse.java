package ra.ecommerce_store_01.payload.respone;

import lombok.Data;

@Data
public class VoucherResponse {
    private int voucherId;
    private int quantity;
    private float discount;
    private String productName;
}
