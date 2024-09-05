package com.project.bunnyCare.profileCard.infrastructure;

import com.project.bunnyCare.common.exception.ApiException;
import com.project.bunnyCare.profileCard.domain.ProfileCardEntity;
import com.project.bunnyCare.profileCard.domain.ProfileCardReader;
import com.project.bunnyCare.profileCard.domain.ProfileCardResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProfileCardReaderImpl implements ProfileCardReader {

    private final ProfileCardRepository profileCardRepository;
    @Override
    public ProfileCardEntity findById(Long id) {
        return profileCardRepository.findById(id).orElseThrow(()-> new ApiException(ProfileCardResponseCode.NOT_FOUND));
    }

    @Override
    public List<ProfileCardEntity> findAllByUserId(Long userId) {
        return profileCardRepository.findAllByUserId(userId);
    }
}
