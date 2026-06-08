package org.samtar.warehouse.cart.controller;

import jakarta.validation.Valid;
import org.samtar.warehouse.cart.dto.req.CartReqDto;
import org.samtar.warehouse.cart.dto.res.CartResDto;
import org.samtar.warehouse.cart.service.CartService;
import org.samtar.warehouse.common.dto.response.GenericResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {
    CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }


    @PostMapping({"/add-to-cart", "/updare-cart"})
    public ResponseEntity<GenericResponseDto<CartResDto>> addToCart(@Valid @RequestBody CartReqDto payload) {
        GenericResponseDto<CartResDto> response = new GenericResponseDto<>("Cart updated",
                true,
                cartService.addToCart(payload));
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/empty-cart")
    public ResponseEntity<GenericResponseDto<CartResDto>> emptyCart() {
        ;
        GenericResponseDto<CartResDto> response = new GenericResponseDto<>("Cart is empty",
                true,
                cartService.emptyCart()
        );
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/cart-items")
    public ResponseEntity<GenericResponseDto<CartResDto>> getCartItems() {
        GenericResponseDto<CartResDto> response = new GenericResponseDto<>("Cart Items",
                true,
                cartService.getCartItems());
        return ResponseEntity.ok().body(response);
    }

}
