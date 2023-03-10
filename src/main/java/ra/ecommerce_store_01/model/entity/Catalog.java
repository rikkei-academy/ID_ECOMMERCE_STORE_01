package ra.ecommerce_store_01.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "catalog")
public class Catalog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "CatalogID")
    private int catalogId;
    @JoinColumn(name = "CatalogName")
    private String catalogName;
    @JoinColumn(name = "CatalogStatus")
    private boolean catalogStatus;
    @JsonIgnore
    @OneToMany(mappedBy = "catalog")
    private List<Product> listProduct = new ArrayList<>();
}
