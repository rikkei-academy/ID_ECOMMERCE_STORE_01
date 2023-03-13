package ra.ecommerce_store_01.payload.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class ProductModel {
    private String productName;
    private String imageLink;
    private float price;
    private boolean delivery;
    private String description;
    private int catalogId;
//    private int brandId;
//    private List<String> listImg = new ArrayList<>();
}
