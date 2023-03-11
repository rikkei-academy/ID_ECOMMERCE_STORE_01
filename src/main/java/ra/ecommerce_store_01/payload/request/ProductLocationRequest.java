package ra.ecommerce_store_01.payload.request;

import lombok.Data;

@Data
public class ProductLocationRequest {
    private int quantity;
    private float discount;
    private boolean productLocationStatus;
    private int productId;
    private int locationId;
}
