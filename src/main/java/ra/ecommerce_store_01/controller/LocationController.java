package ra.ecommerce_store_01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.ecommerce_store_01.model.entity.Location;
import ra.ecommerce_store_01.model.service.LocationService;
import ra.ecommerce_store_01.payload.request.LocationModel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/v1/location")
public class LocationController {
    @Autowired
    private LocationService locationService;

    @GetMapping
    public List<Location> getAllLocation() {

        return locationService.findAll();
    }

    @GetMapping("/getById")
    public Location getLocationById(@RequestParam int locationId) {
        return locationService.findById(locationId);
    }

    @PostMapping("/create")
    public Location createNewLocation(@RequestBody LocationModel locationModel) {
        Location location = new Location();
        location.setLocationName(locationModel.getLocationName());
        location.setLocationStatus(true);
        Location location1 = locationService.saveOrUpdate(location);
        return location1;
    }

    @PatchMapping("/update")
    public Location updateLocation(@RequestParam int locationId, @RequestBody LocationModel locationModel) {
        Location locationUpdate = locationService.findById(locationId);
        locationUpdate.setLocationName(locationModel.getLocationName());
        return locationService.saveOrUpdate(locationUpdate);
    }

    @PatchMapping("/delete")
    public ResponseEntity<?> deleteLocation(@RequestParam int locationId) {
        boolean check = locationService.deleteLocation(locationId);
        if (check) {
            return ResponseEntity.ok("Xoa location thanh cong");
        } else {
            return ResponseEntity.ok("Xoa location that bai");
        }
    }

    @GetMapping("/paging")
    public ResponseEntity<?> getPaging(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Location> locations = locationService.getPaging(pageable);
        Map<String, Object> data = new HashMap<>();
        data.put("location", locations.getContent());
        data.put("total",locations.getSize());
        data.put("totalItems", locations.getTotalElements());
        data.put("totalPages", locations.getTotalPages());
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/searchByName")
    public ResponseEntity<Map<String, Object>> searchByName(@RequestParam String locationName,
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10 ") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Location> pageLocation = locationService.searchByName(locationName,pageable);
        Map<String, Object> data = new HashMap<>();
        data.put("location", pageLocation.getContent());
        data.put("total", pageLocation.getSize());
        data.put("totalItems", pageLocation.getTotalElements());
        data.put("totalPages", pageLocation.getTotalPages());
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
