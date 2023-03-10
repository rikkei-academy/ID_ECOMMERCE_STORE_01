package ra.ecommerce_store_01.payload.request;

import lombok.Data;

@Data
public class BlogCatalogRequest {
    private int blogCatId;
    private String blogCatName;
    private boolean blogcatStatus = true;
}
