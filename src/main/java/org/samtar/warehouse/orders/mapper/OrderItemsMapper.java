package org.samtar.warehouse.orders.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.samtar.warehouse.orders.dto.res.OrderItemResDto;
import org.samtar.warehouse.orders.entity.OrderItemsEntity;

@Mapper(componentModel = "spring")
public interface OrderItemsMapper {

    @Mapping(target = "productID",source = "product.productId")
    @Mapping(target = "productName",source = "product.productName")
    OrderItemResDto toDto(OrderItemsEntity orderItemsEntity);
}
