package org.samtar.warehouse.cart.service;

import jakarta.transaction.Transactional;
import org.samtar.warehouse.cart.dto.req.CartItemReqDto;
import org.samtar.warehouse.cart.dto.req.CartReqDto;
import org.samtar.warehouse.cart.dto.res.CartItemResDto;
import org.samtar.warehouse.cart.dto.res.CartResDto;
import org.samtar.warehouse.cart.entity.CartEntity;
import org.samtar.warehouse.cart.entity.CartItemsEntity;
import org.samtar.warehouse.cart.mapper.CartMapper;
import org.samtar.warehouse.cart.repository.CartItemsRepository;
import org.samtar.warehouse.cart.repository.CartRepository;
import org.samtar.warehouse.common.exceptions.ProductException;
import org.samtar.warehouse.products.repository.ProductRepository;
import org.samtar.warehouse.shared.services.UserSharedService;
import org.samtar.warehouse.user.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CartService {
    ProductRepository productRepository;
    CartRepository cartRepository;
    CartItemsRepository cartItemsRepository;
    CartMapper cartMapper;
    UserSharedService userSharedService;

    public CartService(CartItemsRepository cartItemsRepository,
            CartMapper cartMapper,
            CartRepository cartRepository,
            UserSharedService userSharedService,
            ProductRepository productRepository) {
        this.cartItemsRepository = cartItemsRepository;
        this.cartMapper = cartMapper;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userSharedService = userSharedService;
    }

    @Transactional
    public CartResDto addToCart(CartReqDto payload) {
        UserEntity currentUser = userSharedService.getCurrentUser();
        CartEntity userCart = cartRepository.findByUser_id(currentUser.getId()).orElse(new CartEntity());
        double totalAmount = 0;
        userCart.setUser(currentUser);
        userCart.setTotalItems(payload.items().size());
        List<CartItemsEntity> cartItems = new ArrayList<>();
        for (CartItemReqDto item : payload.items()) {
            totalAmount += item.price() * item.quantity();
            CartItemsEntity newItem = new CartItemsEntity();
            newItem.setProduct(productRepository.findByProductId(item.productId())
                    .orElseThrow(() -> ProductException.notExists(item.productId())));
            newItem.setCart(userCart);
            newItem.setPrice(item.price());
            newItem.setQuantity(item.quantity());
            cartItems.add(newItem);
        }
        userCart.setTotalAmount(totalAmount);
        userCart.getCartItems().clear();
        userCart.getCartItems().addAll(cartItems);
        return cartMapper.toDto(cartRepository.saveAndFlush(userCart));
    }

    public CartResDto emptyCart() {
        cartRepository.deleteByUser_id(userSharedService.getCurrentUser().getId());
        return new CartResDto(new HashSet<>());
    }

    public CartResDto getCartItems() {
        UserEntity currentUser = userSharedService.getCurrentUser();
        CartEntity userCart = cartRepository.findByUser_id(currentUser.getId()).orElse(null);
        if (userCart == null) {
            return new CartResDto(new HashSet<>());
        } else {
            return cartMapper.toDto(userCart);
        }
    }

}
