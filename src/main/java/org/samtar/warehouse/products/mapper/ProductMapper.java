package org.samtar.warehouse.products.mapper;

import org.mapstruct.*;
import org.samtar.warehouse.products.dto.req.CreateProductReqDto;
import org.samtar.warehouse.products.dto.req.UpdateProductReqDto;
import org.samtar.warehouse.products.dto.res.ProductPaginatedResDto;
import org.samtar.warehouse.products.dto.res.ProductResDto;
import org.samtar.warehouse.products.entity.ProductEntity;
import org.samtar.warehouse.user.dto.request.CreateUserReqDto;

import java.lang.annotation.Target;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(ignore = true,target = "createdAt")
    @Mapping(ignore = true,target = "updatedAt")
    @Mapping(ignore = true,target = "createdBy")
    ProductEntity toEntity(CreateProductReqDto req);

    @Mapping(target = "userId",source = "createdBy.id")
    ProductResDto toResponse(ProductEntity productEntity);

    List<ProductResDto> toResponse(List<ProductEntity> productEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(UpdateProductReqDto updateProductReqDto, @MappingTarget ProductEntity productEntity);


}
