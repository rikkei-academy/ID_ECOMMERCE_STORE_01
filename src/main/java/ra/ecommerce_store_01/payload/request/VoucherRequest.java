package ra.ecommerce_store_01.payload.request;

import lombok.Data;

@Data
public class VoucherRequest {
    private int quantity;
    private float discount;
    private int productId;
}
