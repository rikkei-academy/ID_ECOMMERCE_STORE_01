package ra.ecommerce_store_01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ra.ecommerce_store_01.model.entity.Brand;
import ra.ecommerce_store_01.model.service.BrandService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/v1/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @GetMapping
    public ResponseEntity<?> getAllBrandAndPaging(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Brand> brands = brandService.getPaging(pageable);
        Map<String,Object> data = new HashMap<>();
        data.put("brand",brands.getContent());
        data.put("totalPages",brands.getTotalPages());
        return  new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createBrand(@RequestBody Brand brand) {
        try {
            brand.setBrandStatus(true);
            brandService.saveOrUpdate(brand);
            return ResponseEntity.ok("Create brand successfully!");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Create brand failed!");
        }
    }

    @PutMapping("{brandId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateBrand(@PathVariable("brandId") int brandId, @RequestBody Brand brand) {
        Brand brandUpdate = brandService.findById(brandId);
        try {
            brandUpdate.setBrandName(brand.getBrandName());
            brandService.saveOrUpdate(brandUpdate);
            return ResponseEntity.ok("Update brand successfully!");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Update brand failed!");
        }
    }

    @PatchMapping("changeBrandStatus/{brandId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> changeBrandStatus(@PathVariable("brandId") int brandId, @RequestParam("action") String action) {
        Brand brand = brandService.findById(brandId);
        try {
            if (action.equals("lock")) {
                brand.setBrandStatus(false);
                brandService.saveOrUpdate(brand);
                return ResponseEntity.ok("Lock brand successfully!");
            } else {
                brand.setBrandStatus(true);
                brandService.saveOrUpdate(brand);
                return ResponseEntity.ok("Unlock brand successfully!");
            }
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Action failed!");
        }
    }

    @DeleteMapping("{brandId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteBrand(@PathVariable("brandId") int brandId) {
        try {
            brandService.deleteBrand(brandId);
            return ResponseEntity.ok("Delete brand successfully!");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Delete brand failed!");
        }
    }

    @GetMapping("searchByBrandName")
    public ResponseEntity<?> getPaging(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam("brandName") String brandName){
        Pageable pageable = PageRequest.of(page,size);
        List<Brand> listBrand = brandService.findByBrandName(brandName,pageable);
        Page<Brand> brands = brandService.getPaging(pageable);
        Map<String,Object> data = new HashMap<>();
        data.put("brand",listBrand);
        data.put("totalPages",brands.getTotalPages());
        return  new ResponseEntity<>(data, HttpStatus.OK);
    }
}
