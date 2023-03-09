package ra.ecommerce_store_01.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "LocationID")
    private int locationId;
    @JoinColumn(name = "LocationName")
    private String locationName;
    @JoinColumn(name = "LocationStatus")
    private boolean locationStatus;
    @OneToMany(mappedBy = "location")
    private List<ProductLocation> productLocations = new ArrayList<>();
}
