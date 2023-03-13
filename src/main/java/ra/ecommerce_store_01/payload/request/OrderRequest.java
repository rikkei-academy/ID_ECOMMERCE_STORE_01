package ra.ecommerce_store_01.payload.request;

import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
public class OrderRequest {
    private float shipping;
    private  float tax;
    private Date createDate;
    private int orderStatus;
    private String address;
    private String context;
    private String firstName;
    private String lastName;
    private String country;
    private String state;
    private String zipCode;
    private String Note;
    private String city;
    private int orderId;
    private int userId;
    private Set<Integer> listCart = new HashSet<>();
}
