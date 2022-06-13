package com.order.model;

import com.order.dto.OrderDTO;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name="order_db")
public class Order
{
    @Id
    @GeneratedValue
    private int orderID;
    private LocalDate date = LocalDate.now();
    private int price;
    private int quantity;
    private String address;
    private int  userID;
    private int bookId;
    private boolean cancel;


    public Order() {
        super();
    }

    public Order(OrderDTO dto) {
        super();
        this.quantity = dto.getQuantity();
        this.address = dto.getAddress();
        this.userID = dto.getUserID();
        this.bookId = dto.getBookId();
        this.cancel = dto.isCancel();
    }
}
