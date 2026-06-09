package org.samtar.warehouse.orders.service;

import java.util.HashSet;
import java.util.Set;

import org.samtar.warehouse.cart.entity.CartEntity;
import org.samtar.warehouse.cart.entity.CartItemsEntity;
import org.samtar.warehouse.cart.repository.CartRepository;
import org.samtar.warehouse.common.enums.OrderStatus;
import org.samtar.warehouse.common.exceptions.OrderException;
import org.samtar.warehouse.orders.dto.res.OrderResDto;
import org.samtar.warehouse.orders.entity.OrderEntity;
import org.samtar.warehouse.orders.entity.OrderItemsEntity;
import org.samtar.warehouse.orders.mapper.OrderMapper;
import org.samtar.warehouse.orders.repository.OrderItemsRepository;
import org.samtar.warehouse.orders.repository.OrderRepository;
import org.samtar.warehouse.products.repository.ProductRepository;
import org.samtar.warehouse.shared.services.UserSharedService;
import org.samtar.warehouse.user.entity.UserEntity;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Service
public class OrderService {
    OrderRepository orderRepository;
    OrderItemsRepository orderItemsRepository;
    UserSharedService userSharedService;
    ProductRepository productRepository;
    CartRepository cartRepository;
    EntityManager entityManager;
    OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, OrderItemsRepository orderItemsRepository,
            UserSharedService userSharedService, ProductRepository productRepository, CartRepository cartRepository,
            EntityManager entityManager) {
        this.orderRepository = orderRepository;
        this.orderItemsRepository = orderItemsRepository;
        this.userSharedService = userSharedService;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.entityManager = entityManager;
    }

    @Transactional
    public OrderResDto placeAnOrder() {
        UserEntity currentUser = userSharedService.getCurrentUser();
        CartEntity cart = cartRepository.findByUser_id(currentUser.getId()).orElseThrow(OrderException::cartIsEmpty);
        if (cart.getCartItems().size() == 0) {
            throw OrderException.cartIsEmpty();
        }

        OrderEntity newOrder = new OrderEntity();
        Double totalAmaunt = 0.0;
        newOrder.setOwner(currentUser);
        newOrder.setStatus(OrderStatus.PENDING);
        Set<OrderItemsEntity> orderItems = new HashSet<>();

        // loop
        for (CartItemsEntity a : cart.getCartItems()) {
            totalAmaunt += a.getPrice() * a.getQuantity();
            OrderItemsEntity orderItem = new OrderItemsEntity(
                    a.getProduct(),
                    a.getQuantity(),
                    a.getPrice(),
                    newOrder);
                    orderItems.add(orderItem);
                }
                newOrder.setTotalAmount(totalAmaunt);
                newOrder.setOrderItems(orderItems);

        cartRepository.deleteById(cart.getId());
        return orderMapper.toDto(orderRepository.save(newOrder));

    }

    @Transactional
    public OrderResDto cancelOrder(Long orderID){
        OrderEntity order = orderRepository.findByIdAndStatusNot(orderID,OrderStatus.CANCELLED).orElseThrow(OrderException::orderNotfound);
        order.setStatus(OrderStatus.CANCELLED);
        return orderMapper.toDto(order);
    }

    @Transactional
     public OrderResDto updateOrderStatus(Long orderID,OrderStatus status){
        OrderEntity order = orderRepository.findByIdAndStatusNot(orderID,OrderStatus.CANCELLED).orElseThrow(OrderException::orderNotfound);
        order.setStatus(status);
        return orderMapper.toDto(order);
    }

    public Set<OrderResDto> getAllOrders(){
        UserEntity currentUser = userSharedService.getCurrentUser();
        Set<OrderEntity> orders = orderRepository.findByOwner_id(currentUser.getId());
        return orderMapper.toDto(orders);
    } 

    public OrderResDto getProductDtl(Long orderID){
        OrderEntity order = orderRepository.findById(orderID).orElseThrow(OrderException::orderNotfound);
        return orderMapper.toDto(order);
    }
}
