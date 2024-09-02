package com.project.bunnyCare.user.application.interfaces;

import com.project.bunnyCare.user.domain.SocialType;
import com.project.bunnyCare.user.domain.User;

import java.util.Optional;

public interface UserRepository {

    User findByEmailAndSocialType(String email, SocialType socialType);
    User save(User user);
}
