package ra.ecommerce_store_01.model.serviveImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ra.ecommerce_store_01.model.entity.Location;
import ra.ecommerce_store_01.model.repository.LocationRepository;
import ra.ecommerce_store_01.model.service.LocationService;

import java.util.List;

@Service
public class LocationServiceImp implements LocationService {
    @Autowired
    private LocationRepository locationRepository;
    @Override
    public Location saveOrUpdate(Location location) {
        return locationRepository.save(location);
    }

    @Override
    public Location findById(int locationId) {
        return locationRepository.findById(locationId).get();
    }

    @Override
    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    @Override
    public Page<Location> searchByName(String locationName, Pageable pageable) {
        return locationRepository.findLocationByLocationNameContaining(locationName,pageable);
    }

    @Override
    public Page<Location> getPaging(Pageable pageable) {
        return locationRepository.findAll(pageable);
    }

    @Override
    public boolean deleteLocation(int locationId) {
        Location location = locationRepository.findById(locationId).get();
        location.setLocationStatus(false);
        try {
            locationRepository.save(location);
            return true;
        } catch (Exception ex){
            return false;
        }
    }

    @Override
    public List<Location> sortLocationByName(String direction) {
        if (direction.equals("asc")){
            return locationRepository.findAll(Sort.by("locationName").ascending());
        } else {
            return locationRepository.findAll(Sort.by("locationName").descending());
        }

    }
}
