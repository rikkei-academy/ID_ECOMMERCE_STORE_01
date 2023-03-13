package ra.ecommerce_store_01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.ecommerce_store_01.model.entity.Banner;
import ra.ecommerce_store_01.model.service.BannerService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/v1/banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;

    @GetMapping
    public ResponseEntity<?> getAllBannerAndPaging(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<Banner> banners = bannerService.getPaging(pageable);
        Map<String,Object> data = new HashMap<>();
        data.put("banner",banners.getContent());
        data.put("totalPages",banners.getTotalPages());
        return  new ResponseEntity<>(data, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<?> createBanner(@RequestBody Banner banner) {
        try {
            banner.setBannerStatus(true);
            bannerService.saveOrUpdate(banner);
            return ResponseEntity.ok("Create banner successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Create failed");
        }
    }

    @PutMapping("updateBanner/{bannerId}")
    public ResponseEntity<?> updateBanner(@PathVariable("bannerId") int bannerId, @RequestBody Banner banner) {
        try {
            Banner bannerUpdate = bannerService.findById(bannerId);
            bannerUpdate.setContent(banner.getContent());
            bannerUpdate.setImage(banner.getImage());
            bannerService.saveOrUpdate(bannerUpdate);
            return ResponseEntity.ok("Update banner successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Update failed");
        }
    }

    @GetMapping("changeBannerStatus/{bannerId}")
    public ResponseEntity<?> changeBannerStatus(@PathVariable("bannerId") int bannerId, @RequestParam("action") String action) {
        Banner banner = bannerService.findById(bannerId);
        try {
            if (action.equals("lock")) {
                banner.setBannerStatus(false);
                bannerService.saveOrUpdate(banner);
                return ResponseEntity.ok("Lock banner successfully");
            } else {
                banner.setBannerStatus(true);
                bannerService.saveOrUpdate(banner);
                return ResponseEntity.ok("Unlock banner successfully");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Action failed");
        }
    }

    @DeleteMapping("{bannerId}")
    public ResponseEntity<?> deleteBanner(@PathVariable("bannerId") int bannerId) {
        try {
            bannerService.delete(bannerId);
            return ResponseEntity.ok("Delete successfully");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Delete failed");
        }
    }

    @GetMapping("searchByContent")
    public ResponseEntity<?> getPaging(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam("content") String content){
        Pageable pageable = PageRequest.of(page,size);
        List<Banner> listBanner = bannerService.findByContent(content,pageable);
        Page<Banner> banners = bannerService.getPaging(pageable);
        Map<String,Object> data = new HashMap<>();
        data.put("banner",listBanner);
        data.put("totalPages",banners.getTotalPages());
        return  new ResponseEntity<>(data, HttpStatus.OK);
    }
}
