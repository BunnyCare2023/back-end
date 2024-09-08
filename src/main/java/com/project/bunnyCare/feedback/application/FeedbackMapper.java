package com.project.bunnyCare.feedback.application;

import com.project.bunnyCare.feedback.domain.FeedbackEntity;
import com.project.bunnyCare.feedback.interfaces.dto.CreateFeedbackRequestDto;
import com.project.bunnyCare.feedback.interfaces.dto.FeedbackDetailResponseDto;
import com.project.bunnyCare.profileCard.interfaces.dto.ImageDetailResponseDto;
import com.project.bunnyCare.user.interfaces.dto.UserDetailResponseDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface FeedbackMapper {

    FeedbackEntity toEntity(CreateFeedbackRequestDto dto);



    default FeedbackDetailResponseDto from(FeedbackEntity entity) {
        List<ImageDetailResponseDto> images = entity.getImages().stream()
                .map(ImageDetailResponseDto::from).toList();

        UserDetailResponseDto createdUser = UserDetailResponseDto.from(entity.getCreatedUser());

        return new FeedbackDetailResponseDto(
                entity.getId(),
                entity.getType(),
                entity.getTitle(),
                entity.getContent(),
                entity.getHospitalName(),
                entity.getHospitalAddress(),
                entity.getLongitude(),
                entity.getLatitude(),
                images,
                createdUser
        );
    }
}
