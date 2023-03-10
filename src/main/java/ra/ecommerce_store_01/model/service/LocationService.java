package ra.ecommerce_store_01.model.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ra.ecommerce_store_01.model.entity.Location;

import java.util.List;

public interface LocationService {
    Location saveOrUpdate(Location location);
    Location findById(int locationId);
    List<Location> findAll();
    Page<Location> searchByName(String locationName, Pageable pageable);
    Page<Location> getPaging(Pageable pageable);

    boolean deleteLocation(int locationId);
    List<Location> sortLocationByName(String direction);
}
