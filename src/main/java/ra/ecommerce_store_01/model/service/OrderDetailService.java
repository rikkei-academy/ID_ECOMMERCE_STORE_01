package ra.ecommerce_store_01.model.service;

import ra.ecommerce_store_01.payload.request.OrderDetailRequest;
import ra.ecommerce_store_01.payload.respone.OrderDetailResponse;

import java.util.List;

public interface OrderDetailService {
    boolean save(OrderDetailRequest orderDetailRequest);
    boolean update(OrderDetailRequest orderDetailRequest);
    OrderDetailResponse findById(int id);
    boolean deleteCart(int id);
}
