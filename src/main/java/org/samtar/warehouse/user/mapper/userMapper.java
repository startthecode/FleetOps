package org.samtar.warehouse.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.samtar.warehouse.user.dto.request.CreateUserReqDto;
import org.samtar.warehouse.user.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface userMapper {
    @Mapping(ignore = true,target = "role")
    @Mapping(ignore = true,target = "password")
    @Mapping(ignore = true,target = "accountBlocked")
    UserEntity toEntity(CreateUserReqDto req);
}
