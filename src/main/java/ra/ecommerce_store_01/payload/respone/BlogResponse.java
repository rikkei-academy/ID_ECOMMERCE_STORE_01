package ra.ecommerce_store_01.payload.respone;

import lombok.Data;

import java.util.Date;

@Data
public class BlogResponse {
    private int blogId;
    private String blogName;
    private String blogContent;
    private Date createDate;
    private String tag;
    private boolean blogStatus;
    private BlogCatalogResponse blogCatalogResponse;
}
