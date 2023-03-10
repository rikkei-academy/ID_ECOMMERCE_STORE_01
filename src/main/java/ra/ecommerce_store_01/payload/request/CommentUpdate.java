package ra.ecommerce_store_01.payload.request;

import lombok.Data;

@Data
public class CommentUpdate {
    private String comment;
    private int blogId;
    private int userId;
}
