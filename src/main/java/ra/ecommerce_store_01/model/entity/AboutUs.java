package ra.ecommerce_store_01.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "aboutus")
public class AboutUs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "AboutID")
    private int aboutId;
    @JoinColumn(name = "AboutName", nullable = false)
    private String aboutName;
    @JoinColumn(name = "Content", nullable = false)
    private String content;
}
