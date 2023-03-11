package ra.ecommerce_store_01.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ra.ecommerce_store_01.model.entity.Location;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location,Integer> {
    Page<Location> findLocationByLocationNameContaining(String name, Pageable pageable);

//    @Query(value = "select l.LocationID, LocationName,LocationStatus  from location l where LocationID in (select LocationID\n" +
//            "                                            from ProductLocation join product p on ProductLocation.ProductID = p.ProductID\n" +
//            "                                            where ProductName:locationName)",nativeQuery = true)
//    List<Location> findLocationByLocationName(String locationName);
}
