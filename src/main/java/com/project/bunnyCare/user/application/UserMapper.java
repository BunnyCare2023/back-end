package com.project.bunnyCare.user.application;

import com.project.bunnyCare.user.domain.Role;
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

    default UserEntity createUser(AuthUserRequestDto Dto) {
        if ( Dto == null ) {
            return null;
        }

        UserEntity.UserEntityBuilder userEntity = UserEntity.builder();

        userEntity.email( Dto.email() );
        userEntity.name( Dto.name() );
        userEntity.socialType( Dto.socialType() );
        userEntity.deletedYn("N");
        userEntity.role(Role.ROLE_USER);

        return userEntity.build();
    }

}
