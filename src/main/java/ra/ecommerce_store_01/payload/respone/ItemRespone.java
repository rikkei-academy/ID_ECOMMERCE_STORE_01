package ra.ecommerce_store_01.payload.respone;

import lombok.Data;

@Data
public class ItemRespone {
    private int ItemFlashSaleId;
    private float discount;
    private int quantity;
    private int soldQuantity;
    private int productId;
    private String productName;
    private float price;
}
