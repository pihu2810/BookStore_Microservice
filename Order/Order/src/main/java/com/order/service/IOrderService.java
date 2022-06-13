package com.order.service;

import com.order.dto.OrderDTO;
import com.order.model.Order;

import java.util.List;

public interface IOrderService
{
    Order insertOrder(OrderDTO orderdto);

    Order getByID(int orderID);

    List<Order> getAll();

    Order updateById(int orderID,OrderDTO dto);

    Order deleteById(int orderID);
//    List<Order> getOrderRecord (String token);
//    List<Order> getAllOrderRecords(String token);
//
//    Order cancelOrder(String token,int userID);

}
