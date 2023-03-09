package ra.ecommerce_store_01.payload.respone;

import lombok.Data;

import java.util.Date;

@Data
public class CommentResponse {
    private int commentId;
    private Date createdDate;
    private String content;
    private String userName;
}
