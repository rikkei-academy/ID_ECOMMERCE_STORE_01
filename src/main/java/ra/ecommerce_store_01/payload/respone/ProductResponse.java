package ra.ecommerce_store_01.payload.respone;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductResponse {
    private int productId;
    private String productName;
    private boolean delivery;
    private String imageLink;
    private float price;
    private String description;
    private int catalogId;
    private String catalogName;
    private int brandId;
    private String brandName;
    private int views;
    private List<String> listImage = new ArrayList<>();
}
