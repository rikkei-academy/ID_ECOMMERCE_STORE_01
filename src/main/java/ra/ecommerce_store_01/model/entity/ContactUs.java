package ra.ecommerce_store_01.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "contactus")
public class ContactUs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "ContactID")
    private int contactId;
    @JoinColumn(name = "NameCompany")
    private String nameCompany;
    @JoinColumn(name = "Email")
    private String email;
    @JoinColumn(name = "Feedback")
    private String feedBack;
    @JoinColumn(name = "ContactStatus")
    private boolean contactStatus;
}
