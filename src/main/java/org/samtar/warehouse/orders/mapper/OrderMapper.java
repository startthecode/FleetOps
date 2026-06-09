package org.samtar.warehouse.orders.mapper;

import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.samtar.warehouse.orders.dto.res.OrderResDto;
import org.samtar.warehouse.orders.entity.OrderEntity;

@Mapper(componentModel = "spring",uses = {OrderItemsMapper.class})
public interface OrderMapper {

    @Mapping(source = "orderItems", target = "items")
    @Mapping(source = "id", target = "orderid")
    @Mapping(source = "status", target = "orderStatus")
    OrderResDto toDto(OrderEntity cartEntity);

        @Mapping(source = "orderItems", target = "items")
    @Mapping(source = "id", target = "orderid")
    @Mapping(source = "status", target = "orderStatus")
    Set<OrderResDto> toDto(Set<OrderEntity> orderEntiteEntities);

}
