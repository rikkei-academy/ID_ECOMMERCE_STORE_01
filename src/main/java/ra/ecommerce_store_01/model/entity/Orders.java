package ra.ecommerce_store_01.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "orderproduct")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "OrderID")
    private int orderId;
    @JoinColumn(name = "Subtotal")
    private float subTotal;
    @JoinColumn(name = "Shipping")
    private float shipping;
    @JoinColumn(name = "TotalAmount")
    private float totalAmount;
    @JoinColumn(name = "Tax")
    private float tax;
    @JoinColumn(name = "CreateDate")
    private Date createDate;
    @JoinColumn(name = "OrderStatus")
    private int orderStatus;
    @JoinColumn(name = "Address")
    private String address;
    @JoinColumn(name = "FirstName")
    private String firstName;
    @JoinColumn(name = "Contact")
    private String contact;
    @JoinColumn(name = "LastName")
    private String lastName;
    @JoinColumn(name = "Country")
    private String country;
    @JoinColumn(name = "City")
    private String city;
    @JoinColumn(name = "State")
    private String state;
    @JoinColumn(name = "Zipcode")
    private String zipCode;
    @JoinColumn(name = "Note")
    private String node;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "UserId")
    @JsonIgnore
    private User user;
    @OneToMany(mappedBy = "orders")
    private List<OrderDetail> listOrderDetail = new ArrayList<>();
}
