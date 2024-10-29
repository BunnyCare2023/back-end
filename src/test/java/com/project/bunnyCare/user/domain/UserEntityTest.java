package com.project.bunnyCare.user.domain;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
class UserEntityTest {

    @Test
    @DisplayName("회원가입 테스트")
    void givenUpdateUser_whenRegisterNewUser_thenReturnNewUser () {
        //Given
        UserEntity userEntity = new UserEntity();

        //When
        userEntity.registerNewUser();

        //Then
        assertEquals("N", userEntity.getDeletedYn());
        assertEquals(Role.ROLE_USER, userEntity.getRole());
    }

}