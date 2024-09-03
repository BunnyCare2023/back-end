package com.project.bunnyCare.user.application;

import com.project.bunnyCare.user.domain.UserEntity;
import com.project.bunnyCare.user.interfaces.dto.AuthUserRequestDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {

    UserEntity toEntity(AuthUserRequestDto Dto);

}
