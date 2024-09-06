package com.project.bunnyCare.profileCard.domain;


import com.project.bunnyCare.image.domain.ImageEntity;
import com.project.bunnyCare.profileCard.domain.appearance.AppearanceEntity;
import com.project.bunnyCare.profileCard.domain.appearance.AppearanceType;
import com.project.bunnyCare.user.domain.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProfileCardFactory {

    public ProfileCardEntity createProfileCard(ProfileCardEntity profileCard, ImageEntity imageEntity, UserEntity user, List<AppearanceType> appearances) {

        List<AppearanceEntity> appearanceEntities = appearances.stream().map(AppearanceEntity::create).toList();
        appearanceEntities.forEach(appearance -> appearance.updateProfileCard(profileCard));
        profileCard.create(user, imageEntity, appearanceEntities);
        return profileCard;
    }

    public void deleteProfileCard(ProfileCardEntity profileCard) {
        profileCard.getAppearances().forEach(AppearanceEntity::delete);
        profileCard.delete();
    }

}
