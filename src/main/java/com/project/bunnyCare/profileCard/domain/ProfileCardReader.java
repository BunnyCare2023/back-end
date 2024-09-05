package com.project.bunnyCare.profileCard.domain;

import java.util.Collection;
import java.util.List;

public interface ProfileCardReader {
    ProfileCardEntity findById(Long id);

    List<ProfileCardEntity> findAllByUserId(Long userId);
}
