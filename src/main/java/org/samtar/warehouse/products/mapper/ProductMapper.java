package org.samtar.warehouse.products.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.samtar.warehouse.products.dto.req.CreateProductReqDto;
import org.samtar.warehouse.products.dto.res.ProductResDto;
import org.samtar.warehouse.products.entity.ProductEntity;
import org.samtar.warehouse.user.dto.request.CreateUserReqDto;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(ignore = true,target = "createdAt")
    @Mapping(ignore = true,target = "updatedAt")
    @Mapping(ignore = true,target = "createdBy")
    ProductEntity toEntity(CreateProductReqDto req);

    @Mapping(target = "userId",source = "createdBy.id")
    ProductResDto toResponse(ProductEntity productEntity);

}
