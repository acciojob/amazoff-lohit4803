package com.driver;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {

    HashMap<String,Order> orderMap  = new HashMap<>();
    HashMap<String,DeliveryPartner> deliveryMap  = new HashMap<>();
    HashMap<String,List<String>> orderDeliveryMap  = new HashMap<>();
    HashMap<String,Integer> OrderTimeMap  = new HashMap<>();

    public void addOrderTimeInDb(String orderId, String Time){
        String hr = Time.substring(0,2);
        String min = Time.substring(3,5);
        int h = Integer.parseInt(hr);
        int m = Integer.parseInt(min);
        int totalTime = h*60 + m;
        OrderTimeMap.put(orderId,totalTime);
    }

    public void addOrderInDb(Order order){
        String key = order.getId();
        orderMap.put(key,order);
    }

    @Autowired
    DeliveryPartner deliveryPartner;
    public void addDeliveryInDb(String Partner){
        deliveryMap.put(Partner,deliveryPartner);
    }

    public void addOrderDeliveryInDb(String orderId,String partnerId){
        if(orderMap.containsKey(orderId) && deliveryMap.containsKey(partnerId)){
            List<String> orderByDelivery = new ArrayList<>();
            if(orderDeliveryMap.containsKey(partnerId))
                orderByDelivery = orderDeliveryMap.get(partnerId);
            orderByDelivery.add(orderId);

            orderDeliveryMap.put(partnerId,orderByDelivery);
        }
    }

    public Order getOrderFromDb(String order){
        return orderMap.get(order);
    }

    public DeliveryPartner getDeliveryFromDb(String partner){
        return deliveryMap.get(partner);
    }

    public int getNoOfOrder(String partnerId){
        List<String> orderList = new ArrayList<>();
        orderList = orderDeliveryMap.get(partnerId);
        int count = 0;
        for(String o : orderList){
            count++;
        }
        return count;
    }

    public List<String> getAllOrder(String partnerId){
        // List<Order> orderList = new ArrayList<>();
        List<String> orderList = new ArrayList<>();
        orderList = orderDeliveryMap.get(partnerId);
        //for(String o : ordeList){
        //    orderList.add(orderMap.get(o));
        //}
        return orderList;
    }

    public List<String> getAllOrder(){
        List<String> o = new ArrayList<>();
        //for(Order or : orderMap.values()){
        //  o.add(or);
        //}
        for(String order : orderMap.keySet())
            o.add(order);
        return o;
    }

    public int noOfOrderNotAssigned(){
        int count = 0;
        HashSet<String> orderSet = new HashSet<>();
        for(String partner : orderDeliveryMap.keySet()){

            for(String order : orderDeliveryMap.get(partner)){
                orderSet.add(order);
            }
        }

        for(String order : orderSet){
            if(orderMap.containsKey(order))
                continue;
            else
                count++;
        }
        return count;
    }

    public int orderNotAssigned(String deliverTime, String partnerId){
        String hr = deliverTime.substring(0,2);
        String min = deliverTime.substring(3,5);
        int h = Integer.parseInt(hr);
        int m = Integer.parseInt(min);
        int totalTime = h*60 + m;

        HashSet<String> orderSet = new HashSet<>();
        int count = 0;

        for(String order : orderDeliveryMap.get(partnerId)){
            orderSet.add(order);
        }

        for(String order : orderSet){
            if(totalTime == OrderTimeMap.get(order))
                continue;
            else
                count++;
        }
        return count;
    }

    public String orderTime(String partnerId){
        HashSet<String> orderSet = new HashSet<>();
        int t = 0;
        for(String order : orderDeliveryMap.get(partnerId)){
            orderSet.add(order);
        }
        for(String o : orderSet){
            t = Math.max(t,OrderTimeMap.get(o));
        }

        int t1 = t/60;
        int t2 = t%60;
        String hr = Integer.toString(t1);
        String min = Integer.toString(t2);

        String s = hr+":"+min;
        return s;
    }

    public void deletePartner(String partnerId){
        deliveryMap.remove(partnerId);
        orderDeliveryMap.remove(partnerId);
    }

    public void deleteOrder(String orderId){
        orderMap.remove(orderId);
        for(List<String> l : orderDeliveryMap.values()){
            if(l.contains(orderId))
                l.remove(orderId);
        }
    }

}