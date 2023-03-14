package ra.ecommerce_store_01.payload.request;

import lombok.Data;
@Data
public class ItemFlashSaleRequest {
    private float discount;
    private int quantity;
    private int soldQuantity;
    private int productId;
    private int flashSaleId;
}
