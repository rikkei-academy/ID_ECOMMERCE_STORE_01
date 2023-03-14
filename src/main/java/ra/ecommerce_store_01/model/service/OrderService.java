package ra.ecommerce_store_01.model.service;

import org.springframework.data.domain.Pageable;
import ra.ecommerce_store_01.model.entity.Orders;
import ra.ecommerce_store_01.payload.request.ConfirmOrder;
import ra.ecommerce_store_01.payload.request.OrderRequest;
import ra.ecommerce_store_01.payload.respone.OrderResponse;

import java.util.List;
import java.util.Map;

public interface OrderService {
    Map<String,Object> findAll(Pageable pageable);
    Map<String,Object> findAllByStatus(Pageable pageable, int status);
    Map<String,Object> findAllForUser(Pageable pageable,int id);
    Map<String,Object> findByStatusByUser(Pageable pageable,int status,int id);
    OrderResponse findById(int id);
    boolean saveOrUpdate(OrderRequest orderRequest,String action);
    boolean checkout(OrderRequest orderRequest);
    boolean confirmOrder(ConfirmOrder confirmOrder,boolean active);
    Orders findByOrderId(int id);
}
