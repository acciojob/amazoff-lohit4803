package com.driver;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;
    public void addorder(Order order){
        orderRepository.addOrderInDb(order);
    }

    public void addPartner(String deliveryPartner){
        orderRepository.addDeliveryInDb(deliveryPartner);
    }

    public void addOrderPartner(String orderId, String partnerId){
        orderRepository.addOrderDeliveryInDb(orderId,partnerId);
    }

    public Order GetOrder(String OrderId){
        return orderRepository.getOrderFromDb(OrderId);
    }

    public DeliveryPartner GetPartner(String partnerId){
        return orderRepository.getDeliveryFromDb(partnerId);
    }

    public int NoOfOrderByPartner(String partnerId){
        return orderRepository.getNoOfOrder(partnerId);
    }

    public List<String> OrderByPartner(String partnerId){
        return orderRepository.getAllOrder(partnerId);
    }

    public List<String> Order(){
        return orderRepository.getAllOrder();
    }

    public int orderNotAssigned(){
        return orderRepository.noOfOrderNotAssigned();
    }

    public int orderLeftAfterTime(String time, String partnerId){
        return orderRepository.orderNotAssigned(time, partnerId);
    }

    public String lastDeliveryTime(String partnerId){
        return orderRepository.orderTime(partnerId);
    }

    public void DeletePartner(String partnerId){
        orderRepository.deletePartner(partnerId);
    }

    public void DeleteOrder(String orderId){
        orderRepository.deleteOrder(orderId);
    }
}