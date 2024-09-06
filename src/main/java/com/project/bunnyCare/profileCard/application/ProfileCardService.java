package com.project.bunnyCare.profileCard.application;

import com.project.bunnyCare.common.exception.ApiException;
import com.project.bunnyCare.common.util.AuthUtil;
import com.project.bunnyCare.image.application.ImageService;
import com.project.bunnyCare.image.domain.ImageEntity;
import com.project.bunnyCare.image.domain.ImageType;
import com.project.bunnyCare.profileCard.domain.*;
import com.project.bunnyCare.profileCard.interfaces.dto.CreateProfileCardRequestDto;
import com.project.bunnyCare.profileCard.interfaces.dto.ProfileCardDetailResponseDto;
import com.project.bunnyCare.profileCard.interfaces.dto.UpdateProfileCardRequestDto;
import com.project.bunnyCare.user.domain.UserEntity;
import com.project.bunnyCare.user.domain.UserReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProfileCardService {

    private final ImageService imageService;
    private final UserReader userReader;
    private final ProfileCardStore profileCardStore;
    private final ProfileCardReader profileCardReader;
    private final ProfileCardMapper profileCardMapper;
    private final ProfileCardFactory profileCardFactory;

    @Transactional
    public ProfileCardDetailResponseDto createProfileCard(CreateProfileCardRequestDto dto, MultipartFile file) {
        log.info("createProfileCard rabbitName : {}", dto.rabbitName());
        Long userId = AuthUtil.getUserId();

        UserEntity user = userReader.findById(userId);
        ImageEntity newImage = imageService.uploadImage(file, ImageType.PROFILE);
        ProfileCardEntity profileCard = profileCardMapper.toEntity(dto);

        profileCard = profileCardFactory.createProfileCard(profileCard, newImage, user, dto.appearanceTypes());
        return ProfileCardDetailResponseDto.from(profileCardStore.save(profileCard));

    }

    @Transactional
    public void deleteProfileCard(Long id) {
        log.info("deleteProfileCard id: {}", id);
        Long userId = AuthUtil.getUserId();
        ProfileCardEntity profileCard = profileCardReader.findById(id);
        // 권한 확인
        UserEntity user = userReader.findById(userId);
        if(!user.equals(profileCard.getUser())){
            throw new ApiException(ProfileCardResponseCode.FORBIDDEN);
        }
        // 이미지 삭제
        imageService.deleteImage(profileCard.getProfileImage());
        profileCardFactory.deleteProfileCard(profileCard);
    }

    @Transactional
    public ProfileCardDetailResponseDto updateProfileCard(Long id, UpdateProfileCardRequestDto dto, MultipartFile file) {
        log.info("updateProfileCard id: {}", id);
        Long userId = AuthUtil.getUserId();
        ProfileCardEntity profileCard = profileCardReader.findById(id);
        // 권한 확인
        UserEntity user = userReader.findById(userId);
        if(!user.equals(profileCard.getUser())){
            throw new ApiException(ProfileCardResponseCode.FORBIDDEN);
        }
        if(file != null){
            imageService.deleteImage(profileCard.getProfileImage());
            ImageEntity newImage = imageService.uploadImage(file, ImageType.PROFILE);
            profileCard.update(dto.rabbitName(), dto.birthDate(), dto.adoptionDate(),dto.sex(), newImage);
        }else {
            profileCard.update(dto.rabbitName(), dto.birthDate(), dto.adoptionDate(),dto.sex());

        }
        return ProfileCardDetailResponseDto.from(profileCard);
    }

    @Transactional(readOnly = true)
    public List<ProfileCardDetailResponseDto> getProfileCardsByUserId() {
        Long userId = AuthUtil.getUserId();
        log.info("getProfileCards by userId : {}", userId);
        return profileCardReader.findAllByUserId(userId).stream()
                .map(ProfileCardDetailResponseDto::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProfileCardDetailResponseDto getProfileCardById(Long id) {
        log.info("getProfileCard by id : {}", id);
        return ProfileCardDetailResponseDto.from(profileCardReader.findById(id));
    }
}
