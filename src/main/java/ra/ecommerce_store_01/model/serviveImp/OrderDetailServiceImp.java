package ra.ecommerce_store_01.model.serviveImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.ecommerce_store_01.model.entity.OrderDetail;
import ra.ecommerce_store_01.model.entity.Orders;
import ra.ecommerce_store_01.model.entity.User;
import ra.ecommerce_store_01.model.repository.OrderDetailRepository;
import ra.ecommerce_store_01.model.repository.OrderRepository;
import ra.ecommerce_store_01.model.repository.ProductRepository;
import ra.ecommerce_store_01.model.repository.UserRepository;
import ra.ecommerce_store_01.model.service.OrderDetailService;
import ra.ecommerce_store_01.payload.request.OrderDetailRequest;
import ra.ecommerce_store_01.payload.respone.OrderDetailResponse;

import java.util.Date;
import java.util.List;

@Service
public class OrderDetailServiceImp implements OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public boolean save(OrderDetailRequest orderDetailRequest) {
        Orders ordersNew = new Orders();
        try {
            // check xem có tồn tại order nào đang ở trạng thái chờ không?
             Orders orders = orderRepository.findByUser_UserIdAndOrderStatus(orderDetailRequest.getUserId(),0);
            if (orders==null){
                // Tạo order mới ở trạng thái chờ để lưu sản phẩm
                User user = userRepository.findById(orderDetailRequest.getUserId()).get();
                ordersNew.setCreateDate(new Date());

                ordersNew.setUser(user);
                orders = orderRepository.save(ordersNew);
            }
            boolean check = false;
            for (OrderDetail order:orders.getListOrderDetail()) {
                // check xem trong order đã có sản phẩm chưa
                if (order.getProduct().getProductId()==orderDetailRequest.getProductId()){
                    check = true;
                    orderDetailRequest.setOrderDetailId(order.getOrderDetailId());
                    break;
                }
            }
            OrderDetail orderDetail = new OrderDetail();
            if (check){
                // Tăng số lượng cho sản phẩm đã có trong order
                orderDetail = orderDetailRepository.findById(orderDetailRequest.getOrderDetailId()).get();
                orderDetail.setQuantity(orderDetail.getQuantity()+orderDetailRequest.getQuantity());
                orderDetail.setTotalPrice(orderDetail.getPrice()*orderDetail.getQuantity());
            }else {
                // tạo orderDetail mới
                orderDetail.setOrders(orders);
                orderDetail.setProduct(productRepository.findById(orderDetailRequest.getProductId()).get());
                orderDetail.setQuantity(orderDetailRequest.getQuantity());
                orderDetail.setPrice(orderDetail.getProduct().getPrice());
                orderDetail.setTotalPrice(orderDetail.getProduct().getPrice()*orderDetail.getQuantity());
            }
            orderDetailRepository.save(orderDetail);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(OrderDetailRequest orderDetailRequest) {
        try {
            OrderDetail orderDetail = orderDetailRepository.findById(orderDetailRequest.getOrderDetailId()).get();
            orderDetail.setQuantity(orderDetail.getQuantity());
            orderDetail.setPrice(orderDetail.getProduct().getPrice());
            orderDetail.setTotalPrice(orderDetail.getProduct().getPrice()*orderDetail.getQuantity());
            orderDetailRepository.save(orderDetail);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    @Override
    public OrderDetailResponse findById(int id) {
        OrderDetail orderDetail = orderDetailRepository.findById(id).get();
        OrderDetailResponse orderDetailResponse = new OrderDetailResponse();
        orderDetailResponse.setOrderDetailId(orderDetail.getOrderDetailId());
        orderDetailResponse.setPrice(orderDetail.getPrice());
        orderDetailResponse.setQuantity(orderDetail.getQuantity());
        orderDetailResponse.setTotalPrice(orderDetail.getTotalPrice());
       orderDetailResponse.setProductId(orderDetail.getProduct().getProductId());
       orderDetailResponse.setProductName(orderDetail.getProduct().getProductName());
        return orderDetailResponse;
    }

    @Override
    public boolean deleteCart(int id) {
        try {
            orderDetailRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }

    }
}
