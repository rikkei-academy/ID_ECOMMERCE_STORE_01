package ra.ecommerce_store_01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ra.ecommerce_store_01.model.service.OrderService;
import ra.ecommerce_store_01.payload.request.ConfirmOrder;
import ra.ecommerce_store_01.payload.request.OrderRequest;
import ra.ecommerce_store_01.payload.respone.OrderResponse;
import ra.ecommerce_store_01.security.CustomUserDetails;

import java.util.Date;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/auth/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    // ----------------------------   FIND ALL --------------------------------------

    @GetMapping("findAll")
    public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "6") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Map<String, Object> list = orderService.findAll(pageable);
        return ResponseEntity.ok(list);
    }

    // ----------------------------   FIND ALL BY STATUS --------------------------------------

    @GetMapping("findAllByStatus")
    public ResponseEntity<?> findAllByStatus(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "6") int size,
                                             @RequestParam int status) {
        Pageable pageable = PageRequest.of(page, size);
        Map<String, Object> list = orderService.findAllByStatus(pageable, status);
        return ResponseEntity.ok(list);
    }

    // ----------------------------   SOFT BY --------------------------------------

    @GetMapping("softBy")
    public ResponseEntity<?> softBy(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "6") int size,
                                    @RequestParam("direction") String direction,
                                    @RequestParam("softBy") String name) {
        Pageable pageable;
        if (name.equalsIgnoreCase("amount")) {
            if (direction.equalsIgnoreCase("asc")) {
                pageable = PageRequest.of(page, size, Sort.by("totalAmount").ascending());
            } else {
                pageable = PageRequest.of(page, size, Sort.by("totalAmount").descending());
            }
        } else {
            if (direction.equalsIgnoreCase("asc")) {
                pageable = PageRequest.of(page, size, Sort.by("createDate").ascending());
            } else {
                pageable = PageRequest.of(page, size, Sort.by("createDate").descending());
            }
        }
        Map<String, Object> list = orderService.findAll(pageable);
        return ResponseEntity.ok(list);
    }

    // ----------------------------   FIND BY ID --------------------------------------

    @GetMapping("findById/{orderId}")
    public ResponseEntity<?> findById(@PathVariable("orderId") int orderId) {
        OrderResponse orderResponse = orderService.findById(orderId);
        return ResponseEntity.ok(orderResponse);
    }

    // ---------------------------- USER  FIND ALL  --------------------------------------

    @GetMapping("user/findAll")
    public ResponseEntity<?> findAllForUser(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "6") int size) {

        CustomUserDetails customUserDetail = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Pageable pageable = PageRequest.of(page, size);

        Map<String, Object> list = orderService.findAllForUser(pageable, customUserDetail.getUserId());
        return ResponseEntity.ok(list);
    }

    // ---------------------------- USER  FIND ALL  --------------------------------------

    @GetMapping("user/findAllByStatus")
    public ResponseEntity<?> findAllByStatusForUser(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "6") int size,
                                                    @RequestParam("status")int status) {

        CustomUserDetails customUserDetail = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Pageable pageable = PageRequest.of(page, size);

        Map<String, Object> list = orderService.findByStatusByUser(pageable,status,customUserDetail.getUserId());
        return ResponseEntity.ok(list);
    }

    //                       ---------------  CREATE ORDER   -------------------

    @PostMapping("createOrder")
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest orderRequest){
        String user = "user";
        CustomUserDetails customUserDetail = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        orderRequest.setCreateDate(new Date());
        orderRequest.setUserId(customUserDetail.getUserId());
        boolean check = orderService.saveOrUpdate(orderRequest,user);
        if (check){
            return ResponseEntity.ok("Tạo order thành công!");
        }else {
            return ResponseEntity.ok("Tạo order thất bại!");
        }
    }

    //                 ----------------- CHECKOUT ORDER   -------------------

    @PutMapping("checkout/{orderId}")
    public ResponseEntity<?> checkout(@RequestBody OrderRequest orderRequest,@PathVariable("orderId")int orderId){
       orderRequest.setOrderStatus(1);
       orderRequest.setOrderId(orderId);
        boolean check = orderService.checkout(orderRequest);
        if (check){
            return ResponseEntity.ok("checkout thành công!");
        }else {
            return ResponseEntity.ok("checkout thất bại!");
        }
    }

    //                 ----------------- USER CONFIRM ORDER   -------------------

    @PatchMapping("user/comfirmOrder/{orderId}")
    public ResponseEntity<?> comfirmOrderByUser(@RequestBody OrderRequest orderRequest,@PathVariable("orderId")int orderId){
        orderRequest.setOrderStatus(3);
        orderRequest.setOrderId(orderId);
        String user = "";
        boolean check = orderService.saveOrUpdate(orderRequest,user);
        if (check){
            return ResponseEntity.ok("Xác nhận đơn hàng thành công!");
        }else {
            return ResponseEntity.ok("Xác nhận đơn hàng thất bại!");
        }
    }

    //                 ----------------- USER CANCLE ORDER   -------------------

    @PatchMapping("user/cancle/{orderId}")
    public ResponseEntity<?> cancleOrderByUser(@RequestBody OrderRequest orderRequest,@PathVariable("orderId")int orderId){
        orderRequest.setOrderStatus(4);
        orderRequest.setOrderId(orderId);
        String user = "";
        boolean check = orderService.saveOrUpdate(orderRequest,user);
        if (check){
            return ResponseEntity.ok("Xác nhận đơn hàng thành công!");
        }else {
            return ResponseEntity.ok("Xác nhận đơn hàng thất bại!");
        }
    }


    //                 ----------------- ADMIN CONFIRM ORDER   -------------------

    @PatchMapping("comfirmOrder")
    public ResponseEntity<?> comfirmOrder(@RequestBody ConfirmOrder confirmOrder){
        boolean action = true;
        boolean check = orderService.confirmOrder(confirmOrder,action);
        if (check){
            return ResponseEntity.ok("Thành công!");
        }else {
            return ResponseEntity.ok("Thất bại!");
        }
    }

    //                 ----------------- ADMIN CANCLE ORDER   -------------------

    @PatchMapping("cancleOrder")
    public ResponseEntity<?> cancleOrder(@RequestBody ConfirmOrder confirmOrder){
        boolean action = false;
        boolean check = orderService.confirmOrder(confirmOrder,action);
        if (check){
            return ResponseEntity.ok("Thành công!");
        }else {
            return ResponseEntity.ok("Thất bại!");
        }
    }
}
