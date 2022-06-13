package com.cart.controller;

import com.cart.dto.CartDTO;
import com.cart.dto.ResponseDTO;
import com.cart.model.Cart;
import com.cart.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController
{
    @Autowired
    private ICartService iCartService;


    //to insert book in the cart
    @PostMapping("/insert")
    public ResponseEntity<ResponseDTO> insertBook(@Valid @RequestBody CartDTO dto){
        Cart cart = iCartService.insertToCart(dto);
        ResponseDTO response = new ResponseDTO("Cart Added to bookstore", cart);
        return new ResponseEntity<ResponseDTO>(response,HttpStatus.OK);
    }

    //to get all carts in bookstore app
    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAllCarts(){
        List<Cart> list = iCartService.getAllCarts();
        ResponseDTO response = new ResponseDTO("All carts in bookstore",list );
        return new ResponseEntity<ResponseDTO>(response,HttpStatus.OK);
    }

    //find cart by cart id
    @GetMapping("/findbyid/{cartId}")
    public ResponseEntity<ResponseDTO> getbookByID(@PathVariable int cartId){
        Cart cart = iCartService.getCartByID(cartId);
        ResponseDTO response = new ResponseDTO("Requested Cart : ",cart);
        return new ResponseEntity<ResponseDTO>(response,HttpStatus.OK);
    }

    //update cart by cart id
    @PutMapping("/update/{cartId}")
    public ResponseEntity<ResponseDTO> updateById(@PathVariable  int cartId,@Valid @RequestBody CartDTO dto){
        Cart cart = iCartService.updateById(cartId,dto);
        ResponseDTO response = new ResponseDTO("cart updated : ", cart);
        return new ResponseEntity<ResponseDTO>(response,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{cartId}")
    public ResponseEntity<ResponseDTO> deleteById(@PathVariable  int cartId){
        Cart cart = iCartService.deleteById(cartId);
        ResponseDTO response = new ResponseDTO("cart deleted successfully",cart);
        return new ResponseEntity<ResponseDTO>(response,HttpStatus.OK);
    }

}
