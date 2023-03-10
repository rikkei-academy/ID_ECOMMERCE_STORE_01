package ra.ecommerce_store_01.payload.request;
import lombok.Data;

import java.util.Date;
@Data
public class CommentModel {
    private int commentId;
    private Date createdDate;
    private String comment;
    private boolean commentStatus;
    private int blogId;
    private int userId;
}
