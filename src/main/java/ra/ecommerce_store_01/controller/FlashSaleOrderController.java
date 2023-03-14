package ra.ecommerce_store_01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ra.ecommerce_store_01.model.entity.FlashSaleOrder;
import ra.ecommerce_store_01.model.entity.ItemFlashSale;
import ra.ecommerce_store_01.model.entity.Orders;
import ra.ecommerce_store_01.model.service.*;
import ra.ecommerce_store_01.payload.request.FlashSaleOrderRequest;
import ra.ecommerce_store_01.payload.request.FlashSaleRequest;
import ra.ecommerce_store_01.payload.request.OrderDetailRequest;
import ra.ecommerce_store_01.security.CustomUserDetails;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/v1/flashSaleOrder")
public class FlashSaleOrderController {
    @Autowired
    private ItemFlashSaleService itemFlashSaleService;
    @Autowired
    private FlashSaleOrderService flashSaleOrderService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderDetailService orderDetailService;
    @PostMapping("/create")
    public ResponseEntity<?> createFlashSaleOrder(@RequestBody FlashSaleOrderRequest flashSaleOrderRequest){

        CustomUserDetails customUserDetail = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Orders orderNew = new Orders();
        Orders orders = orderService.findByUserIdAndStatus(customUserDetail.getUserId(),0);
        ItemFlashSale itemFlashSale = itemFlashSaleService.findById(flashSaleOrderRequest.getItemFlashSaleId());

        if (orders==null){

            orderNew.setUser(userService.findByUserId(customUserDetail.getUserId()));
            orders = orderService.save(orderNew);
        }
        boolean check =true;
        for (FlashSaleOrder flashOrder :orders.getListFlashSaleOrder()) {
            if (flashOrder.getFlashSaleOrderId()==flashSaleOrderRequest.getItemFlashSaleId()){
                check =false;
                break;
            }
        }
        if (check){
            FlashSaleOrder flashSaleOrder = new FlashSaleOrder();
            flashSaleOrder.setOrders(orders);
            flashSaleOrder.setItemFlashSale(itemFlashSale);
            flashSaleOrder.setQuantity(1);
            flashSaleOrder.setPrice(itemFlashSale.getProduct().getPrice()*(100- itemFlashSale.getDiscount())/100);

            try {
                flashSaleOrderService.save(flashSaleOrder);
                return ResponseEntity.ok("Them thanh cong");
            }catch (Exception e){
                return ResponseEntity.ok("Them that bai");
            }
        }else {
//            OrderDetailRequest orderDetailRequest = new OrderDetailRequest();
//            orderDetailRequest.setQuantity(1);
//            orderDetailRequest.setUserId(customUserDetail.getUserId());
//            orderDetailRequest.setProductId(itemFlashSale.getProduct().getProductId());
//            check = orderDetailService.save(orderDetailRequest);
//            if (check){
//                return ResponseEntity.ok("Sản phẩm đang sales đã có trong giỏ hàng của bạn! Đã thêm sản phẩm vào giỏ hàng ");
//            }else {
//                return ResponseEntity.ok("Có lỗi trong quá trình xử lý vui lòng thử lại!");
//            }
            return ResponseEntity.ok("Sản phẩm đang sales đã có trong giỏ hàng của bạn! ");
        }


    }

}
