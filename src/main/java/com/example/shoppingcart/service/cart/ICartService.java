package com.example.shoppingcart.service.cart;

import com.example.shoppingcart.model.Cart;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

    Cart getCartByUserId(Long userId);
}
