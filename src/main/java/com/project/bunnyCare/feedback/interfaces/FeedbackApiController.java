package com.project.bunnyCare.feedback.interfaces;

import com.project.bunnyCare.common.api.ApiResponse;
import com.project.bunnyCare.feedback.application.FeedbackService;
import com.project.bunnyCare.feedback.domain.FeedbackResponseCode;
import com.project.bunnyCare.feedback.interfaces.dto.CreateFeedbackRequestDto;
import com.project.bunnyCare.feedback.interfaces.dto.FeedbackDetailResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/${api.version}/feedbacks")
public class FeedbackApiController {

    private final FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createFeedback(
            @ModelAttribute CreateFeedbackRequestDto dto,
            @RequestPart(required = false)MultipartFile[] files
            ) {
        feedbackService.createFeedback(dto, files);
        return ResponseEntity.ok(ApiResponse.ok(FeedbackResponseCode.CREATE_SUCCESS));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<FeedbackDetailResponseDto>>> getFeedbacks() {
        List<FeedbackDetailResponseDto> feedbacks = feedbackService.getFeedbacks();
        return ResponseEntity.ok(ApiResponse.ok(FeedbackResponseCode.READ_SUCCESS, feedbacks));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<FeedbackDetailResponseDto>> getFeedback(@PathVariable Long id) {
        FeedbackDetailResponseDto feedbacks = feedbackService.getFeedback(id);
        return ResponseEntity.ok(ApiResponse.ok(FeedbackResponseCode.READ_SUCCESS, feedbacks));
    }

}
