package org.samtar.warehouse.cart.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.samtar.warehouse.cart.dto.res.CartItemResDto;
import org.samtar.warehouse.cart.entity.CartItemsEntity;

@Mapper(componentModel = "spring")
public interface CartItemsMapper {

    @Mapping(target = "productID",source = "product.productId")
    @Mapping(target = "productName",source = "product.productName")
    CartItemResDto toDto(CartItemsEntity cartItemsEntity);
}
