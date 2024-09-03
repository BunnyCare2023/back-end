package com.project.bunnyCare.user.domain;

import com.project.bunnyCare.common.exception.ApiException;
import com.project.bunnyCare.user.repository.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class User {
    private Long id;
    private String email;
    private String name;
    private SocialType socialType;
    private Role role;

    public User(Long id, String email, String name, SocialType socialType) {
        if(email == null || email.isEmpty()) throw new ApiException(UserResponseCode.NULL_POINTER_EXCEPTION);
        if(name == null || name.isEmpty()) throw new ApiException(UserResponseCode.NULL_POINTER_EXCEPTION);
        if(socialType == null) throw new ApiException(UserResponseCode.NULL_POINTER_EXCEPTION);

        this.id = id;
        this.email = email;
        this.name = name;
        this.socialType = socialType;
    }

    public void setUserRole(){
        this.role = Role.ROLE_USER;
    }

    public void setAdminRole() {
        this.role = Role.ROLE_ADMIN;
    }

    public UserEntity toEntity(){
        return UserEntity.builder()
                .id(id)
                .email(email)
                .name(name)
                .socialType(socialType)
                .role(role)
                .build();
    }

}
