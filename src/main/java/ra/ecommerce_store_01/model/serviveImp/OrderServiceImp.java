package ra.ecommerce_store_01.model.serviveImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ra.ecommerce_store_01.model.entity.*;
import ra.ecommerce_store_01.model.repository.*;
import ra.ecommerce_store_01.model.service.OrderDetailService;
import ra.ecommerce_store_01.model.service.OrderService;
import ra.ecommerce_store_01.payload.request.ConfirmOrder;
import ra.ecommerce_store_01.payload.request.OrderRequest;
import ra.ecommerce_store_01.payload.respone.OrderDetailResponse;
import ra.ecommerce_store_01.payload.respone.OrderResponse;

import java.util.*;

@Service
public class OrderServiceImp implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemFlashSaleRepository itemFlashSaleRepository;
    @Autowired
    private ProductRepository productRepository;



    @Override
    public Map<String, Object> findAll(Pageable pageable) {
        Page<Orders> listOrder = orderRepository.findAll(pageable);
        List<OrderResponse> list = new ArrayList<>();
        for (Orders order :listOrder) {
            list.add(changeData(order));
        }
        Map<String,Object> listResponse = new HashMap<>();
        listResponse.put("listOrder",list);
        listResponse.put("total",listOrder.getSize());
        listResponse.put("totalItems",listOrder.getTotalElements());
        listResponse.put("totalPage",listOrder.getTotalPages());
        return listResponse;
    }

    @Override
    public Map<String, Object> findAllByStatus(Pageable pageable, int status) {
        Page<Orders> listOrder = orderRepository.findAllByOrderStatus(status,pageable);
        List<OrderResponse> list = new ArrayList<>();
        for (Orders order :listOrder) {
            list.add(changeData(order));
        }
        Map<String,Object> listResponse = new HashMap<>();
        listResponse.put("listOrder",list);
        listResponse.put("total",listOrder.getSize());
        listResponse.put("totalItems",listOrder.getTotalElements());
        listResponse.put("totalPage",listOrder.getTotalPages());
        return listResponse;
    }

    @Override
    public Map<String, Object> findAllForUser(Pageable pageable, int id) {
        Page<Orders> listOrder = orderRepository.findAllByUser_UserId(id,pageable);
        List<OrderResponse> list = new ArrayList<>();
        for (Orders order :listOrder) {
            list.add(changeData(order));
        }
        Map<String,Object> listResponse = new HashMap<>();
        listResponse.put("listOrder",list);
        listResponse.put("total",listOrder.getSize());
        listResponse.put("totalItems",listOrder.getTotalElements());
        listResponse.put("totalPage",listOrder.getTotalPages());
        return listResponse;
    }


    @Override
    public Map<String, Object> findByStatusByUser(Pageable pageable, int status, int id) {
        Page<Orders> listOrder = orderRepository.findAllByUser_UserIdAndOrderStatus(id,status,pageable);
        List<OrderResponse> list = new ArrayList<>();
        for (Orders order :listOrder) {
            list.add(changeData(order));
        }
        Map<String,Object> listResponse = new HashMap<>();
        listResponse.put("listOrder",list);
        listResponse.put("total",listOrder.getSize());
        listResponse.put("totalItems",listOrder.getTotalElements());
        listResponse.put("totalPage",listOrder.getTotalPages());
        return listResponse;
    }

    @Override
    public OrderResponse findById(int id) {
        Orders orders = orderRepository.findById(id).get();
        return changeData(orders);
    }


    @Override
    public boolean saveOrUpdate(OrderRequest orderRequest,String action) {
        Orders orders = new Orders();
        if (orderRequest.getOrderId()!=0){
            orders = orderRepository.findById(orderRequest.getOrderId()).get();
        }
        if (action.equals("user")){
            User user = userRepository.findById(orderRequest.getUserId()).get();
            orders.setUser(user);
            float subtotal = 0;
            for (Integer id :orderRequest.getListCart()) {
                OrderDetail orderDetail = orderDetailRepository.findById(id).get();
                orders.getListOrderDetail().add(orderDetail);
                subtotal+=orderDetail.getTotalPrice();
            }
            orders.setSubTotal(subtotal);
            orders.setTax((float) (subtotal*0.1));
            orders.setShipping(0);
            orders.setTotalAmount(orders.getSubTotal()+orders.getTax()+orders.getShipping());
        }
        orders.setOrderStatus(orderRequest.getOrderStatus());
        try {
            orderRepository.save(orders);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean checkout(OrderRequest orderRequest) {
        try {
            Orders orders = orderRepository.findById(orderRequest.getOrderId()).get();
            orders.setOrderStatus(orderRequest.getOrderStatus());
            orders.setCountry(orderRequest.getCountry());
            orders.setState(orderRequest.getState());
            orders.setZipCode(orderRequest.getZipCode());
            orders.setNode(orderRequest.getZipCode());
            orders.setNode(orderRequest.getNote());
            orders.setCity(orderRequest.getCity());
            orders.setCreateDate(orderRequest.getCreateDate());
            orders.setAddress(orderRequest.getAddress());
            orders.setContact(orderRequest.getContext());
            orders.setFirstName(orderRequest.getFirstName());
            orders.setLastName(orderRequest.getLastName());
            orders.setCreateDate(new Date());
            float subtotal = 0;
            for (OrderDetail orderDetail :orders.getListOrderDetail()) {
                subtotal+=orderDetail.getTotalPrice();
            }
            orders.setSubTotal(subtotal);
            orders.setTax((float) (subtotal*0.1));
            orders.setShipping(0);
            orders.setTotalAmount(orders.getSubTotal()+orders.getTax()+orders.getShipping());
            Date localDate = new Date();
            orderRepository.save(orders);
            for (FlashSaleOrder flashOrder :orders.getListFlashSaleOrder()) {
                flashOrder.getItemFlashSale().setQuantity(flashOrder.getItemFlashSale().getQuantity()-1);
                flashOrder.getItemFlashSale().setSoldQuantity(flashOrder.getItemFlashSale().getSoldQuantity()+1);
                itemFlashSaleRepository.save(flashOrder.getItemFlashSale());
            }
            return true;
        }catch (Exception e){
            return false;
        }

    }

    @Override
    public boolean confirmOrder(ConfirmOrder confirmOrder,boolean active) {
        boolean check = true;
        try {
            for (Integer id :confirmOrder.getListOrder()) {
                Orders orders = orderRepository.findById(id).get();
                if (orders.getOrderStatus()==1){
                    if (active){
                        orders.setOrderStatus(2);
                    }else {
                        orders.setOrderStatus(4);
                    }
                } else {
                  check = false;
                }
            }
            return check;
        }catch (Exception e){
            return check;
        }
    }

    @Override
    public Orders findByUserIdAndStatus(int id, int status) {
        Orders orders = orderRepository.findByUser_UserIdAndOrderStatus(id,status);
        if (status==0){
            Date localDate = new Date();
            for (FlashSaleOrder flashOrder :orders.getListFlashSaleOrder()) {
                if (!(flashOrder.getItemFlashSale().getFlashSale().getStarTime().getTime()<=localDate.getTime()&&
                        localDate.getTime()<=flashOrder.getItemFlashSale().getFlashSale().getEndTime().getTime()
                        || flashOrder.getItemFlashSale().getQuantity()==0)){
                    orders.getListFlashSaleOrder().remove(flashOrder);
                }
            }
            orders = orderRepository.save(orders);
        }
        return orders;
    }

    @Override
    public Orders save(Orders orders) {
        return orderRepository.save(orders);
    }

    private static OrderResponse changeData(Orders order){
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderId(order.getOrderId());
        orderResponse.setSubtotal(order.getSubTotal());
        orderResponse.setShipping(order.getShipping());
        orderResponse.setTax(order.getTax());
        orderResponse.setTotalAmount(order.getTotalAmount());
        orderResponse.setCreateDate(order.getCreateDate());
        orderResponse.setOrderStatus(order.getOrderStatus());
        orderResponse.setAddress(order.getAddress());
        orderResponse.setContact(order.getContact());
        orderResponse.setFirstName(order.getFirstName());
        orderResponse.setLastName(order.getLastName());
        orderResponse.setCountry(order.getCountry());
        orderResponse.setState(order.getState());
        orderResponse.setZipCode(order.getZipCode());
        orderResponse.setNote(order.getNode());
        orderResponse.setCity(order.getCity());
        orderResponse.setUserName(order.getUser().getUserName());
        orderResponse.setUserId(order.getUser().getUserId());
        for (OrderDetail orderDetail :order.getListOrderDetail()) {
            OrderDetailResponse detailResponse = new OrderDetailResponse();
            detailResponse.setOrderDetailId(orderDetail.getOrderDetailId());
            detailResponse.setPrice(orderDetail.getPrice());
            detailResponse.setQuantity(orderDetail.getQuantity());
            detailResponse.setProductId(orderDetail.getProduct().getProductId());
            detailResponse.setTotalPrice(orderDetail.getTotalPrice());
            detailResponse.setProductName(orderDetail.getProduct().getProductName());
            orderResponse.getListOrderDetail().add(detailResponse);
        }
        return orderResponse;
    }


}
