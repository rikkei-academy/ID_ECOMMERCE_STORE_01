package ra.ecommerce_store_01.payload.respone;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class OrderResponse {
    private float subtotal;
    private float shipping;
    private float totalAmount;
    private  float tax;
    private Date createDate;
    private int orderStatus;
    private String address;
    private String contact;
    private String firstName;
    private String lastName;
    private String country;
    private String state;
    private String zipCode;
    private String Note;
    private String city;
    private String userName;
    private int userId;
    private int orderId;
    List<OrderDetailResponse> listOrderDetail = new ArrayList<>();
}
