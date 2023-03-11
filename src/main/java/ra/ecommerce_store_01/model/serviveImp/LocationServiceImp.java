package ra.ecommerce_store_01.model.serviveImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.ecommerce_store_01.model.entity.Location;
import ra.ecommerce_store_01.model.repository.LocationRepository;
import ra.ecommerce_store_01.model.service.LocationService;

@Service
public class LocationServiceImp implements LocationService {
    @Autowired
    LocationRepository locationRepository;
    @Override
    public Location findById(int locationId) {
        return locationRepository.findById(locationId).get();
    }
}
