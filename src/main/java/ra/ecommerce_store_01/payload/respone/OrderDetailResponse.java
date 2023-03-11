package ra.ecommerce_store_01.payload.respone;

import lombok.Data;

@Data
public class OrderDetailResponse {
    private int orderDetailId;
    private float price;
    private int quantity;
    private float totalPrice;
    private String productName;
    private int productId;
}
