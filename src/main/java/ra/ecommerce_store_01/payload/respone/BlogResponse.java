package ra.ecommerce_store_01.payload.respone;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class BlogResponse {
    private int blogId;
    private String blogName;
    private String blogContent;
    private Date createDate;
    private String tag;
    private boolean blogStatus;
    private int blogCatalogId;
    private String blogCatalogName;
    private List<CommentResponse> listComment = new ArrayList<>();
}
