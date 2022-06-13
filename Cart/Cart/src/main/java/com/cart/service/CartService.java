package com.cart.service;

import com.cart.Util.EmailSenderService;
import com.cart.Util.TokenUtility;
import com.cart.dto.CartDTO;
import com.cart.exception.BookStoreException;
import com.cart.model.Cart;
import com.cart.repository.CartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CartService  implements ICartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    RestTemplate restTemplate;

    @Override
    public Cart insertToCart(CartDTO dto) {
        Cart cart = new Cart(dto);
        cartRepository.save(cart);
        return cart;
    }

    @Override
    public List<Cart> getAllCarts() {
        List<Cart> list = cartRepository.findAll();
        if(list.isEmpty()) {
            throw new NullPointerException("there are no Carts inserted yet");
        }
        return list;
    }

    @Override
    public Cart getCartByID(int cartId) {
        Optional<Cart> cart = cartRepository.findById(cartId);
        if(cart.isEmpty()) {
            throw new BookStoreException("There are no carts with given id");
        }
        return cart.get();
    }

    @Override
    public Cart updateById(int cartId, CartDTO dto) {
        Optional<Cart> cart = cartRepository.findById(cartId);
        if(cart.isPresent()) {
            Cart newCart = new Cart(cartId,dto);
            cartRepository.save(newCart);
            return newCart;
        }
        else {
            throw new BookStoreException("cart not found");
        }
    }

    @Override
    public Cart deleteById(int cartId) {
        Optional<Cart> cart = cartRepository.findById(cartId);
        if(cart.isEmpty()) {
            throw new BookStoreException("Invalid CartID..please input valid Id");
        }
        cartRepository.deleteById(cartId);
        return cart.get();
    }
    }

//    @Autowired
//    TokenUtility util;
//    @Autowired
//    EmailSenderService mailService;
//
//    /**
//     * create a method name as insertItems
//     * Ability to save cart details to repository
//     * */
//    @Override
//    public String insertItems(CartDTO cartdto) {
//        Cart cart =new Cart(cartdto);
//        cartRepository.save(cart);
//        return cart;
//    }
////        Optional<BookContact> book = bookRepository.findById(cartdto.getBookID());
////        Optional<UserContact> userRegistration = userRepository.findById(cartdto.getUserID());
////        if (book.isPresent() && userRegistration.isPresent()) {
////            Cart newCart = new Cart(cartdto.getQuantity(), book.get(), userRegistration.get());
////            cartRepository.save(newCart);
////            String token = util.createToken(newCart.getCartId());
////            return token;
////        } else {
////            throw new BookStoreException("Book or User does not exists");
////        }
////    }
//
//    /**
//     * create a method name as getCartDetails by token
//     * Ability to save cart details to repository
//     * */
//    @Override
//    public List<Cart> getCartDetails(String token) {
//        int id = util.decodeToken(token);
//        Optional<Cart> cartData = cartRepository.findById(id);
//        if (cartData.isPresent()) {
//            List<Cart> listOfCartdata = cartRepository.findAll();
//            log.info("ALL cart records retrieved successfully");
//            return listOfCartdata;
//        } else {
//            System.out.println("Exception ...Token not found!");
//            return null;
//        }
//    }
//
//    /**
//     * create a method name as getCartDetailsById by token
//     * Ability to save cart details to repository
//     * */
//    @Override
//    public Cart getCartDetailsById(String token) {
//        int id = util.decodeToken(token);
//        Optional<Cart> CartData = cartRepository.findById(id);
//        if (CartData.isPresent()) {
//            return CartData.get();
//        } else {
//            throw new BookStoreException(" Didn't find any record for this particular cartId");
//        }
//    }
//
//    /**
//     * create a method name as deleteCartItemById by token
//     * Ability to save cart details to repository
//     * */
//    @Override
//    public void deleteCartItemById(String token) {
//        int id = util.decodeToken(token);
//        Optional<Cart> delete = cartRepository.findById(id);
//        if (delete.isPresent()) {
//            userRepository.deleteById(id);
//        } else {
//            throw new BookStoreException(" Did not get any cart for specific cart id ");
//        }
//
//    }
//
//    /**
//     * create a method name as updateRecordById by token
//     * Ability to save cart details to repository
//     * */
//    @Override
//    public Cart updateRecordById(String token, CartDTO cartDTO) {
//        int id = util.decodeToken(token);
//        Optional<Cart> cart = cartRepository.findById(id);
//        Optional<BookContact>  book = bookRepository.findById(cartDTO.getBookID());
//        Optional<UserContact> user = userRepository.findById(cartDTO.getUserID());
//        if(cart.isPresent()) {
//            if(book.isPresent() && user.isPresent()) {
//                Cart cartData = new Cart(id, cartDTO.getQuantity(),book.get(), user.get());
//                cartRepository.save(cartData);
//                log.info("Cart record updated successfully for id "+id);
//                return cartData;
//            }
//            else {
//                throw new BookStoreException("Book or User doesn't exists");
//            }
//        }
//        else {
//            throw new BookStoreException("Cart Record doesn't exists");
//        }
//    }
//}
