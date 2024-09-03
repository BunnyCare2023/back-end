package com.project.bunnyCare.user.repository;

import com.project.bunnyCare.user.application.interfaces.UserRepository;
import com.project.bunnyCare.user.domain.SocialType;
import com.project.bunnyCare.user.domain.User;
import com.project.bunnyCare.user.repository.entity.UserEntity;
import com.project.bunnyCare.user.repository.jpa.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;
    @Override
    public User findByEmailAndSocialType(String email, SocialType socialType) {
        UserEntity userEntity = jpaUserRepository.findByEmailAndSocialType(email, socialType)
                .orElse(null);
        if (userEntity == null) return null;

        return userEntity.toUser();
    }

    @Override
    public User save(User user) {
        UserEntity newUser = user.toEntity();
        return jpaUserRepository.save(newUser).toUser();
    }
}
