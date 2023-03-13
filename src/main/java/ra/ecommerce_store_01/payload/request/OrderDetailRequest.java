package ra.ecommerce_store_01.payload.request;

import lombok.Data;

@Data
public class OrderDetailRequest {
    private int productId;
    private int quantity;
    private int orderDetailId;
    private int userId;
}
