package com.project.bunnyCare.bookmark.interfaces;

import com.project.bunnyCare.bookmark.application.BookmarkService;
import com.project.bunnyCare.bookmark.domain.BookmarkResponseCode;
import com.project.bunnyCare.bookmark.interfaces.dto.BookmarkRequestDto;
import com.project.bunnyCare.common.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bookmarks")
public class BookmarkApiController {

    private final BookmarkService bookmarkService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> likeHospital(
            @RequestBody BookmarkRequestDto dto
            ) {
        bookmarkService.likeHospital(dto);
        return ResponseEntity.ok(ApiResponse.ok(BookmarkResponseCode.LIKE_SUCCESS,null));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> unlikeHospital(
            @RequestBody BookmarkRequestDto dto
    ) {
        bookmarkService.unlikeHospital(dto);
        return ResponseEntity.ok(ApiResponse.ok(BookmarkResponseCode.UNLIKE_SUCCESS,null));
    }
}
