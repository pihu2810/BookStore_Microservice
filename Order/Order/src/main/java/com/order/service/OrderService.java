package com.order.service;

import com.order.Util.EmailSenderService;
import com.order.Util.TokenUtility;
import com.order.dto.OrderDTO;
import com.order.exception.BookStoreException;
import com.order.model.BookContact;
import com.order.model.Order;
import com.order.model.UserContact;
import com.order.respository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.awt.print.Book;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrderService implements IOrderService
{
    @Autowired
    private OrderRepository orderRepo;
    @Autowired
    RestTemplate restTemplate;

    @Override
    public Order insertOrder(OrderDTO orderdto) {
       UserContact user = restTemplate.getForObject("http://localhost:9005/userregistration/findById/" + orderdto.getUserID(), UserContact.class);
        if(user.equals(null)) {
            throw new BookStoreException("Invalid user id...please provide valid user id");
        }
        else
        {
            BookContact book = restTemplate.getForObject("http://localhost:9006/book/getBook/" + orderdto.getBookId(), BookContact.class);
            if(book.equals(null))
            {
                throw new BookStoreException("Invalid book id...please provide valid book id");
            }
            else
            {
                if(orderdto.getQuantity() >(int)book.getQuantity())
                {
                    throw new BookStoreException("Currently we dont have that much books available");
                }
                else
                {
                    Order order = new Order(orderdto);
                    order.setPrice(orderdto.getQuantity()*book.getPrice());
                    order.setBookId(book.getBookId());
                    order.setDate(LocalDate.now());
                    orderRepo.save(order);
                    return order;
                }
            }
        }
    }


    @Override
    public Order getByID(int orderID) {
        Optional<Order> order = orderRepo.findById(orderID);
        if(order.isPresent()) {
            throw new BookStoreException("Invalid Order Id...please provide valid Order id");
        }
        return order.get();
    }


    @Override
    public List<Order> getAll() {
        List<Order> orders =  orderRepo.findAll();
        return orders;
    }

    @Override
    public Order updateById(int orderID, OrderDTO dto) {
        Optional<Order> order = orderRepo.findById(orderID);
        if(order.isPresent()) {
            throw new BookStoreException("Invalid Order Id...please provide valid Order id");
        }
       UserContact user = restTemplate.getForObject("http://localhost:9005/user/findById/" + dto.getUserID(), UserContact.class);
        if(user.equals(null)) {
            throw new BookStoreException("Invalid user id...please provide valid user id");
        }
        BookContact book = restTemplate.getForObject("http://localhost:9006/book/getBook/" + dto.getBookId(), BookContact.class);
        if(book.equals(null)) {
            throw new BookStoreException("Invalid book id...please provide valid book id");
        }
        Order newOrder = new Order(dto);
        newOrder.setPrice(dto.getQuantity()*book.getPrice());
        orderRepo.save(newOrder);
        return newOrder;
    }


    @Override
    public Order deleteById(int orderID) {
        Optional<Order> order = orderRepo.findById(orderID);
        if(order.isPresent()) {
            throw new BookStoreException("Invalid Order Id...please provide valid Order id");
        }
        orderRepo.delete(order.get());
        return order.get();
    }

}

////    @Autowired
////    EmailSenderService mailService;
////    @Autowired
////    TokenUtility util;
//
//    /**
//     * create a method name as insertOrder
//     * Ability to save order details to repository
//     * */
//    @Override
//    public Order insertOrder(OrderDTO orderdto) {
//        Object user = restTemplate.getForObject("http://localhost:9005/user/findById/" + orderdto.getUserID(), Object.class);
//        if(user.equals(null)) {
//            throw new BookStoreException("Invalid user id...please provide valid user id");
//        }
//        else
//        {
//          Book book = restTemplate.getForObject("http://localhost:9006/book/getBook/" + orderdto.getBookID(), Book.class);
//            if(book.equals(null))
//            {
//                throw new BookStoreException("Invalid book id...please provide valid book id");
//            }
//            else
//            {
//                if(orderdto.getQuantity() >(int)book.getQuantity())
//                {
//                    throw new BookStoreException("Currently we dont have that much books available");
//                }
//                else
//                {
//                    Order order = new Order(orderdto);
//                    order.setPrice(orderdto.getQuantity()*book.getprice());
//                    order.setDate(LocalDate.now());
//                    orderRepo.save(order);
//                    return order;
//                }
//            }
//        }
//
//        @Override
//        public List<Order> getAll() {
//            List<Order> orders =  orderRepo.findAll();
//            return orders;
//        }
//
//
//        //to get order by id
//        @Override
//        public Order getByID(int orderID)  {
//            Optional<Order> order = orderRepo.findById(orderID);
//            if(order.isPresent()) {
//                throw new BookStoreException("Invalid Order Id...please provide valid Order id");
//            }
//            return order.get();
//        }
//
//        //to update existing Order
//        @Override
//        public Order updateById(int orderID,OrderDTO dto) {
//            Optional<Order> order = orderRepo.findById(orderID);
//            if(order.isPresent()) {
//                throw new BookStoreException("Invalid Order Id...please provide valid Order id");
//            }
//            Object user = restTemplate.getForObject("http://localhost:9000/user/findById/" + dto.getUserId(), Object.class);
//            if(user.equals(null)) {
//                throw new BookStoreException("Invalid user id...please provide valid user id");
//            }
//            Book book = restTemplate.getForObject("http://localhost:9001/Book/getBook/" + dto.getBookId(), Book.class);
//            if(book.equals(null)) {
//                throw new BookStoreException("Invalid book id...please provide valid book id");
//            }
//            Order newOrder = new Order(dto);
//            newOrder.setPrice(dto.getQuantity()*book.getPrice());
//            orderRepo.save(newOrder);
//            return newOrder;
//        }

        //to delete order using order Id
//        @Override
//        public Order deleteById(int orderID) {
//
//
//
//    }





//    @Override
//    public Order updateById(int orderID, OrderDTO dto) {
//        return null;
//    }
//
//    @Override
//    public Order deleteById(int orderID) {
//            Optional<Order> order = orderRepo.findById(orderID);
//            if(order.isPresent()) {
//                throw new BookStoreException("Invalid Order Id...please provide valid Order id");
//            }
//            orderRepo.delete(order.get());
//            return order.get();
//        }
//    }


//        Optional<BookContact> book = bookRepo.findById(orderdto.getBookID());
//        Optional<UserContact> user = userRepo.findById(orderdto.getUserID());
//        if (book.isPresent() && user.isPresent()) {
//            if (orderdto.getQuantity() <= book.get().getQuantity()) {
//                int quantity = book.get().getQuantity() - orderdto.getQuantity();
//                book.get().setQuantity(quantity);
//                bookRepo.save(book.get());
//                int totalPrice = book.get().getPrice() * orderdto.getQuantity();
//                Order newOrder = new Order(totalPrice, orderdto.getQuantity(), orderdto.getAddress(), book.get(), user.get(), orderdto.isCancel());
//                orderRepo.save(newOrder);
//                log.info("Order record inserted successfully");
//                String token = util.createToken(newOrder.getOrderID());
//                mailService.sendEmail(newOrder.getUserID().getEmail(), "Test Email", "Registered SuccessFully, hii: "
//                        + newOrder.getOrderID() + "Please Click here to get data-> "
//                        + "http://localhost:9008/order/insert/" + token);
//                log.info("Order record inserted successfully");
//                return token;
//            } else {
//                throw new BookStoreException("Requested quantity is out of stock");
//            }
//        } else {
//            throw new BookStoreException("Book or User doesn't exists");
//        }
//    }

//    /**
//     * create a method name as getOrderRecord by token
//     * Ability to save order details to repository
//     * */
//    @Override
//    public List<Order> getOrderRecord(String token) {
//        Integer id = util.decodeToken(token);
//        Optional<Order> order = orderRepo.findById(id);
//        if (order.isPresent()) {
//            List<Order> listOrder = orderRepo.findAll();
//            log.info("Order record retrieved successfully for id " + id);
//            mailService.sendEmail("vishakakadam19@gmail.com", "Test Email", "Get your data with this token, hii: "
//                    + order.get().getUserID().getEmail() + "Please Click here to get all data-> "
//                    + "http://localhost:9008/order/getById/" + token);
//            return listOrder;
//
//        } else {
//            throw new BookStoreException("Order Record doesn't exists");
//        }
//    }
//
//    /**
//     * create a method name as getAllOrderRecords by token
//     * Ability to save order details to repository
//     * */
//    @Override
//    public List<Order> getAllOrderRecords(String token) {
//        Integer id = util.decodeToken(token);
//        Optional<Order> orderData = orderRepo.findById(id);
//        if (orderData.isPresent()) {
//            List<Order> listOrderData = orderRepo.findAll();
//            log.info("ALL order records retrieved successfully");
//            mailService.sendEmail("vishakakadam19@gmail.com", "Test Email", "Get your data with this token, hii: "
//                    + orderData.get().getUserID().getEmail() + "Please Click here to get all data-> "
//                    + "http://localhost:9008/order/getAllOrders/" + token);
//            return listOrderData;
//        } else {
//            System.out.println("Exception ...Token not found!");
//            return null;
//        }
//    }
//
//    /**
//     * create a method name as cancelOrder by token and user id
//     * Ability to save order details to repository
//     * */
//    @Override
//    public Order cancelOrder(String token, int userID) {
//        Integer id=util.decodeToken(token);
//        Optional<Order> order = orderRepo.findById(id);
//        Optional<UserContact> user = userRepo.findById(userID);
//        if (order.isPresent() && user.isPresent()) {
//            order.get().setCancel(true);
//            orderRepo.save(order.get());
//            mailService.sendEmail(order.get().getUserID().getEmail(), "Test Email", "canceled order SuccessFully, hii: "
//                    +order.get().getOrderID()+"Please Click here to get data of updated id-> "
//                    +"http://localhost:9008/order/cancelOrder/"+token);
//            return order.get();
//        } else {
//            throw new BookStoreException("Order Record doesn't exists");
//        }
//    }

