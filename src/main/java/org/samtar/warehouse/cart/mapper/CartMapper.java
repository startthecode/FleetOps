package org.samtar.warehouse.cart.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.samtar.warehouse.cart.dto.res.CartResDto;
import org.samtar.warehouse.cart.entity.CartEntity;

@Mapper(componentModel = "spring",uses = {CartItemsMapper.class})
public interface CartMapper {

    @Mapping(source = "cartItems", target = "items")
    CartResDto toDto(CartEntity cartEntity);

}
