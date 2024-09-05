package com.project.bunnyCare.profileCard.interfaces;

import com.project.bunnyCare.common.api.ApiResponse;
import com.project.bunnyCare.common.util.AuthUtil;
import com.project.bunnyCare.profileCard.application.ProfileCardService;
import com.project.bunnyCare.profileCard.domain.ProfileCardEntity;
import com.project.bunnyCare.profileCard.domain.ProfileCardResponseCode;
import com.project.bunnyCare.profileCard.interfaces.dto.CreateProfileCardRequestDto;
import com.project.bunnyCare.profileCard.interfaces.dto.ProfileCardDetailResponseDto;
import com.project.bunnyCare.profileCard.interfaces.dto.UpdateProfileCardRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/${api.version}/profiles")
@RequiredArgsConstructor
public class ProfileCardApiController {

    private final ProfileCardService profileCardService;

    @PostMapping
    public ResponseEntity<ApiResponse<ProfileCardDetailResponseDto>> createProfileCard(
            @ModelAttribute CreateProfileCardRequestDto dto,
            @RequestPart("file") MultipartFile file
    ) {
        ProfileCardDetailResponseDto card = profileCardService.createProfileCard(dto, file);
        return ResponseEntity.ok(ApiResponse.ok(ProfileCardResponseCode.CRATED,card));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProfileCard(@PathVariable Long id){
        profileCardService.deleteProfileCard(id);
        return ResponseEntity.ok(ApiResponse.ok(ProfileCardResponseCode.DELETE));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<ProfileCardDetailResponseDto>> updateProfileCard(
            @PathVariable Long id,
            UpdateProfileCardRequestDto dto,
            Authentication auth){
        ProfileCardDetailResponseDto response = profileCardService.updateProfileCard(id, dto);
        return ResponseEntity.ok(ApiResponse.ok(ProfileCardResponseCode.UPDATE, response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProfileCardDetailResponseDto>>> getProfileCards(){
        List<ProfileCardDetailResponseDto> cards = profileCardService.getProfileCardsByUserId();
        return ResponseEntity.ok(ApiResponse.ok(ProfileCardResponseCode.READ,cards));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProfileCardDetailResponseDto>> getProfileCard(@PathVariable Long id){
        ProfileCardDetailResponseDto card = profileCardService.getProfileCardById(id);
        return ResponseEntity.ok(ApiResponse.ok(ProfileCardResponseCode.READ,card));
    }


}
