package ra.ecommerce_store_01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ra.ecommerce_store_01.model.service.OrderDetailService;
import ra.ecommerce_store_01.payload.request.OrderDetailRequest;
import ra.ecommerce_store_01.payload.respone.OrderDetailResponse;
import ra.ecommerce_store_01.security.CustomUserDetails;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/auth/orderDetail")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;


     /*
        FindById
        inputValue: Integer id
        outPutValue: orderDetailRespone
        made By: tin
     */

    @GetMapping("findById")
    public ResponseEntity<?> findCartById(@PathVariable("id") int id) {
        OrderDetailResponse orderDetailResponse = orderDetailService.findById(id);
        return ResponseEntity.ok(orderDetailResponse);
    }

    /*
      insertCart
      inputValue: OrderDetailRequest
      outPutValue: true/false
      made By: tin
   */

    @PostMapping("insertCart")
    public ResponseEntity<?> insertOrderDetail(@RequestBody OrderDetailRequest orderDetailRequest) {
        CustomUserDetails customUserDetail = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        orderDetailRequest.setUserId(customUserDetail.getUserId());
        boolean check = orderDetailService.save(orderDetailRequest);
        if (check) {
            return ResponseEntity.ok("Đã thêm sản phẩm vào giỏ hàng !");
        } else {
            return ResponseEntity.ok("Thêm sản phẩm vào giỏ hàng thất bại !! ");
        }
    }

    /*
      updateCart
      inputValue: OrderDetailRequest
                  Integer cartId
      outPutValue: true/false
      made By: tin
   */

    @PatchMapping("updateCart/{cartId}")
    public ResponseEntity<?> updateCart(@RequestBody OrderDetailRequest orderDetailRequest, @PathVariable("cartId") int cartId) {
        orderDetailRequest.setOrderDetailId(cartId);
        boolean check = orderDetailService.update(orderDetailRequest);
        if (check) {
            return ResponseEntity.ok("Cập nhật giỏ hàng thành công!");
        } else {
            return ResponseEntity.ok("Cập nhật giỏ hàng thất bại !! ");
        }
    }

    /*
    deleteCart
    inputValue: Integer cartId
    outPutValue: true/false
    made By: tin
    */

    @DeleteMapping("delete/{cartId}")
    public ResponseEntity<?> delete(@PathVariable("cartId") int cartId) {

        boolean check = orderDetailService.deleteCart(cartId);
        if (check) {
            return ResponseEntity.ok("Xóa sản phẩm khỏi giỏ hàng thành công!");
        } else {
            return ResponseEntity.ok("Xóa sản phẩm khỏi giỏ hàng thất bại !! ");
        }
    }


}
