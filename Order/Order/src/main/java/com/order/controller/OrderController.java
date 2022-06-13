package com.order.controller;

import com.order.dto.OrderDTO;
import com.order.dto.ResponseDTO;
import com.order.model.Order;
import com.order.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@RestController
@RequestMapping("order")
public class OrderController
{
    @Autowired
    private IOrderService orderService;

    @PostMapping("/insert")
    public ResponseEntity<ResponseDTO> addOrder(@RequestBody OrderDTO dto){
        Order order = orderService.insertOrder(dto);
        ResponseDTO response = new ResponseDTO("Order Placed : ", order);
        return new ResponseEntity<ResponseDTO>(response,HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAll(){
        List<Order> list = orderService.getAll();
        ResponseDTO response = new ResponseDTO("Orders Placed : ", list);
        return new ResponseEntity<ResponseDTO>(response,HttpStatus.OK);

    }

    @GetMapping("/getById/{orderID}")
    public ResponseEntity<ResponseDTO> getById( @Valid @PathVariable int orderID){
        Order order = orderService.getByID(orderID);
        ResponseDTO response = new ResponseDTO("Order Retrieved : ", order);
        return new ResponseEntity<ResponseDTO>(response,HttpStatus.OK);

    }

    @PutMapping("/update/{orderID}")
    public ResponseEntity<ResponseDTO> updateById( @Valid@PathVariable int orderID,@RequestBody OrderDTO dto){
        Order order = orderService.updateById(orderID,dto);
        ResponseDTO response = new ResponseDTO("Order Updated : ", order);
        return new ResponseEntity<ResponseDTO>(response,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{orderID}")
    public ResponseEntity<ResponseDTO> deleteById(@Valid@PathVariable int orderID){
        Order order = orderService.deleteById(orderID);
        ResponseDTO response = new ResponseDTO("Order deleted : ", order);
        return new ResponseEntity<ResponseDTO>(response,HttpStatus.OK);
    }
//    //Ability to call api to cancel order record by token and userid
//    @PutMapping("/cancelOrder/{token}/{userID}")
//    public ResponseEntity<ResponseDTO> getCancelOrder(@PathVariable String token , @PathVariable int userID){
//        Order deletedOrder = orderService.cancelOrder(token,userID);
//        ResponseDTO dto = new ResponseDTO("Cancel order successfully !",deletedOrder);
//        return new ResponseEntity(dto,HttpStatus.OK);
//    }
}
