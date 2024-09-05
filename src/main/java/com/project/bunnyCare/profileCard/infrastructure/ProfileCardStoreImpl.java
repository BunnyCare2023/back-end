package com.project.bunnyCare.profileCard.infrastructure;

import com.project.bunnyCare.profileCard.domain.ProfileCardEntity;
import com.project.bunnyCare.profileCard.domain.ProfileCardStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProfileCardStoreImpl implements ProfileCardStore {

    private final ProfileCardRepository profileCardRepository;

    @Override
    public ProfileCardEntity save(ProfileCardEntity profileCardEntity) {
        return profileCardRepository.save(profileCardEntity);
    }
}
