package ra.ecommerce_store_01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.ecommerce_store_01.model.entity.FlashSale;
import ra.ecommerce_store_01.model.entity.ItemFlashSale;
import ra.ecommerce_store_01.model.service.FlashSaleService;
import ra.ecommerce_store_01.model.service.ItemFlashSaleService;
import ra.ecommerce_store_01.model.service.ProductService;
import ra.ecommerce_store_01.payload.request.ItemFlashSaleRequest;
import ra.ecommerce_store_01.payload.respone.ItemRespone;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/v1/itemFlashSale")
public class ItemFlashSaleController {
    @Autowired
    private ItemFlashSaleService itemFlashSaleService;
    @Autowired
    private ProductService productService;
    @Autowired
    private FlashSaleService flashSaleService;

    @PostMapping("/create")
    public ResponseEntity<?> createItemFlashSale(@RequestBody ItemFlashSaleRequest itemFlashSaleRequest){
        ItemFlashSale itemFlashSale = new ItemFlashSale();
        itemFlashSale.setDiscount(itemFlashSaleRequest.getDiscount());
        itemFlashSale.setQuantity(itemFlashSaleRequest.getQuantity());
        itemFlashSale.setSoldQuantity(0);
        itemFlashSale.setProduct(productService.findById(itemFlashSaleRequest.getFlashSaleId()));
        itemFlashSale.setFlashSale(flashSaleService.findById(itemFlashSaleRequest.getFlashSaleId()));
        itemFlashSaleService.save(itemFlashSale);
        return ResponseEntity.ok(chageData(itemFlashSale));
    }
    private static ItemRespone chageData(ItemFlashSale itemFlashSale){
        ItemRespone itemRespone = new ItemRespone();
        itemRespone.setItemFlashSaleId(itemFlashSale.getItemFlashSaleId());
        itemRespone.setPrice(itemFlashSale.getProduct().getPrice());
        itemRespone.setDiscount(itemFlashSale.getDiscount());
        itemRespone.setQuantity(itemFlashSale.getQuantity());
        itemRespone.setProductId(itemFlashSale.getProduct().getProductId());
        itemRespone.setProductName(itemFlashSale.getProduct().getProductName());
        return itemRespone;
    }
    @DeleteMapping("/{itemFlashSaleId}")
    public void deleteItem(@PathVariable int itemFlashSaleId){
        itemFlashSaleService.delete(itemFlashSaleId);
    }
    @PatchMapping("/update")
    public ResponseEntity<?> updateItem(@RequestBody ItemFlashSaleRequest itemFlashSaleRequest){
        ItemFlashSale itemFlashSale = new ItemFlashSale();
        itemFlashSale.setDiscount(itemFlashSaleRequest.getDiscount());
        itemFlashSale.setQuantity(itemFlashSaleRequest.getQuantity());
        itemFlashSale.setSoldQuantity(0);
        itemFlashSale.setProduct(productService.findById(itemFlashSaleRequest.getFlashSaleId()));
        itemFlashSale.setFlashSale(flashSaleService.findById(itemFlashSaleRequest.getFlashSaleId()));
        itemFlashSaleService.save(itemFlashSale);
        return ResponseEntity.ok(chageData(itemFlashSale));
    }

}
