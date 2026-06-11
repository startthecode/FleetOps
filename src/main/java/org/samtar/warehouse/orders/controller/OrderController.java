package org.samtar.warehouse.orders.controller;

import java.util.Set;

import org.samtar.warehouse.common.dto.response.GenericResponseDto;
import org.samtar.warehouse.common.enums.OrderStatus;
import org.samtar.warehouse.orders.dto.res.OrderResDto;
import org.samtar.warehouse.orders.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
    OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/all/{orderid}")
    public ResponseEntity<GenericResponseDto<OrderResDto>> getOrderDtl(
            @Valid @PathVariable(required = true) Long orderid) {
        GenericResponseDto<OrderResDto> response = new GenericResponseDto<>("Order details", true,
                orderService.getProductDtl(orderid));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<GenericResponseDto<Set<OrderResDto>>> getAllOrder() {
        GenericResponseDto<Set<OrderResDto>> response = new GenericResponseDto<>("All orders fetched", true,
                orderService.getAllOrders());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/place-order")
    public ResponseEntity<GenericResponseDto<OrderResDto>> placeOrder() {
        GenericResponseDto<OrderResDto> response = new GenericResponseDto<>("Order Successfully placed", true,
                orderService.placeAnOrder());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update-status/{orderid}")
    public ResponseEntity<GenericResponseDto<OrderResDto>> updateOrder(@Valid @RequestBody Map<String, String> request,
            @Valid @PathVariable(required = true) Long orderid) {
        OrderStatus status = OrderStatus.valueOf(request.get("status").toUpperCase());
        GenericResponseDto<OrderResDto> response = new GenericResponseDto<>("Order status Successfully placed", true,
                orderService.updateOrderStatus(orderid, status));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/cancel-order/{orderid}")
    public ResponseEntity<GenericResponseDto<OrderResDto>> cancelOrder(@Valid @PathVariable(required = true) Long orderid) {
        GenericResponseDto<OrderResDto> response = new GenericResponseDto<>("Order canceled ", true,
                orderService.cancelOrder(orderid));
        return ResponseEntity.ok(response);
    }

}
