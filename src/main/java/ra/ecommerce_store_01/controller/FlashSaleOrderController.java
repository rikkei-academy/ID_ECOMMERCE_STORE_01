package ra.ecommerce_store_01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.ecommerce_store_01.model.entity.FlashSaleOrder;
import ra.ecommerce_store_01.model.entity.ItemFlashSale;
import ra.ecommerce_store_01.model.service.FlashSaleOrderService;
import ra.ecommerce_store_01.model.service.FlashSaleService;
import ra.ecommerce_store_01.model.service.ItemFlashSaleService;
import ra.ecommerce_store_01.payload.request.FlashSaleOrderRequest;
import ra.ecommerce_store_01.payload.request.FlashSaleRequest;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/v1/flashSaleOrder")
public class FlashSaleOrderController {
    @Autowired
    private ItemFlashSaleService itemFlashSaleService;
    @Autowired
    private FlashSaleOrderService flashSaleOrderService;
    @PostMapping("/create")
    public ResponseEntity<?> createFlashSaleOrder(@RequestBody FlashSaleOrderRequest flashSaleOrderRequest){
        ItemFlashSale itemFlashSale = itemFlashSaleService.findById(flashSaleOrderRequest.getItemFlashSaleId());
        FlashSaleOrder flashSaleOrder = new FlashSaleOrder();
        flashSaleOrder.setItemFlashSale(itemFlashSale);
        flashSaleOrder.setQuantity(1);
        flashSaleOrder.setPrice(itemFlashSale.getProduct().getPrice()*(100- itemFlashSale.getDiscount())/100);
        flashSaleOrderService.save(flashSaleOrder);
        return ResponseEntity.ok("Them thanh cong");
    }

}
