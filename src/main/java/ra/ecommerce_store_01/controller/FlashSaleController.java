package ra.ecommerce_store_01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.ecommerce_store_01.model.entity.FlashSale;
import ra.ecommerce_store_01.model.service.FlashSaleService;
import ra.ecommerce_store_01.model.service.OrderService;
import ra.ecommerce_store_01.model.service.ProductService;
import ra.ecommerce_store_01.payload.request.FlashSaleRequest;
import ra.ecommerce_store_01.payload.respone.FlashSaleRespone;

import java.util.Date;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/v1/flashSale")
public class FlashSaleController {
    @Autowired
    private FlashSaleService flashSaleService;
    @GetMapping("/{flashSaleId}")
    public ResponseEntity<?> getById(@PathVariable int flashSaleId){
        return ResponseEntity.ok(chageData(flashSaleService.findById(flashSaleId)));
    }
    @PostMapping("/create")
    public ResponseEntity<?> createNewFlashSale(@RequestBody FlashSaleRequest flashSaleRequest){
        FlashSale flashSale = new FlashSale();
        flashSale.setStarTime(flashSaleRequest.getStarTime());
        flashSale.setEndTime(flashSaleRequest.getEndTime());
        flashSale.setStatus(true);
        flashSale.setTitle(flashSaleRequest.getTitle());
        flashSale.setDescriptions(flashSaleRequest.getDescriptions());
        flashSaleService.save(flashSale);
        return ResponseEntity.ok(chageData(flashSale));
    }
   @GetMapping("/getByStarTime")
    public ResponseEntity<?> getFlashSaleByStarTime(@RequestBody Date starTime){
        FlashSale flashSale = flashSaleService.findFlashSaleByStarTime(starTime);

        return ResponseEntity.ok(chageData(flashSale));
   }
   public static FlashSaleRespone chageData(FlashSale flashSale){
        FlashSaleRespone flashSaleRespone = new FlashSaleRespone();
        flashSaleRespone.setStarTime(flashSale.getStarTime());
        flashSaleRespone.setEndTime(flashSale.getEndTime());
        flashSaleRespone.setTitle(flashSale.getTitle());
        flashSaleRespone.setDescriptions(flashSale.getDescriptions());
        return flashSaleRespone;
   }

}
