package ra.ecommerce_store_01.payload.request;

import lombok.Data;


import java.util.Date;

@Data
public class ReviewModel {
    private int reviewId;
    private String content;
    private Date createDate;
    private int productId;
    private int userId;
}
