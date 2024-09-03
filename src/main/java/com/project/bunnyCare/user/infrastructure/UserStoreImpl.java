package com.project.bunnyCare.user.infrastructure;

import com.project.bunnyCare.user.domain.UserEntity;
import com.project.bunnyCare.user.domain.UserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserStoreImpl implements UserStore {
    private final UserRepository userRepository;

    @Override
    public UserEntity save(UserEntity user) {
        return userRepository.save(user);
    }
}
