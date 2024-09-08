package com.project.bunnyCare.feedback.application;

import com.project.bunnyCare.common.util.AuthUtil;
import com.project.bunnyCare.feedback.domain.FeedbackEntity;
import com.project.bunnyCare.feedback.domain.FeedbackReader;
import com.project.bunnyCare.feedback.domain.FeedbackStore;
import com.project.bunnyCare.feedback.interfaces.dto.CreateFeedbackRequestDto;
import com.project.bunnyCare.feedback.interfaces.dto.FeedbackDetailResponseDto;
import com.project.bunnyCare.image.application.ImageService;
import com.project.bunnyCare.image.domain.ImageEntity;
import com.project.bunnyCare.image.domain.ImageType;
import com.project.bunnyCare.user.application.UserService;
import com.project.bunnyCare.user.domain.UserEntity;
import com.project.bunnyCare.user.domain.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackStore feedbackStore;
    private final FeedbackReader feedbackReader;
    private final UserReader userReader;
    private final ImageService imageService;
    private final FeedbackMapper feedbackMapper;

    @Transactional
    public void createFeedback(CreateFeedbackRequestDto dto, MultipartFile[] images) {
        Long userId = AuthUtil.getUserId();
        UserEntity user = userReader.findById(userId);

        // 파일 저장해야함.
        List<ImageEntity> imageEntities = null;
        if(images != null && images.length > 0) {
            imageEntities = Arrays.stream(images)
                    .map(image -> imageService.uploadImage(image, ImageType.FEEDBACK))
                    .toList();

        }
        FeedbackEntity feedback = feedbackMapper.toEntity(dto);
        feedback.create(user, imageEntities);
        if(imageEntities != null) {
            imageEntities.forEach(image -> image.updateFeedback(feedback));
        }
        feedbackStore.save(feedback);
    }

    @Transactional(readOnly = true)
    public List<FeedbackDetailResponseDto> getFeedbacks() {
        return feedbackReader.findAll().stream()
                .map(feedbackMapper::from).toList();
    }

    @Transactional(readOnly = true)
    public FeedbackDetailResponseDto getFeedback(Long id) {
        return feedbackMapper.from(feedbackReader.findById(id));
    }
}
