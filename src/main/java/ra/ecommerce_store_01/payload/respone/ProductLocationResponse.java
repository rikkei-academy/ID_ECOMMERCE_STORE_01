package ra.ecommerce_store_01.payload.respone;

import lombok.Data;

@Data
public class ProductLocationResponse {
    private int quantity;
    private float discount;
    private boolean productLocationStatus;
    private String productName;
    private String locationName;
}
