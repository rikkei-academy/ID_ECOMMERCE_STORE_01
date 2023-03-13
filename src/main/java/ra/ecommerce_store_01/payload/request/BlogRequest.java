package ra.ecommerce_store_01.payload.request;

import lombok.Data;

import java.util.Date;

@Data
public class BlogRequest {
    private int blogId;
    private String blogName;
    private String blogContent;
    private String tag;
    private boolean blogStatus = true;
    private Date createDate;
    private int blogCatalogId;
}
