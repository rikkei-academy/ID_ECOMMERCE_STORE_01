package ra.ecommerce_store_01.payload.request;

import lombok.Data;

@Data
public class StarRequest {
    private float star;
    private int productId;
}
