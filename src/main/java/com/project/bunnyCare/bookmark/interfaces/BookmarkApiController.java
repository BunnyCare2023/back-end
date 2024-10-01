package com.project.bunnyCare.bookmark.interfaces;

import com.project.bunnyCare.bookmark.application.BookmarkService;
import com.project.bunnyCare.bookmark.domain.BookmarkResponseCode;
import com.project.bunnyCare.common.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/${api.version}/bookmarks")
public class BookmarkApiController {

    private final BookmarkService bookmarkService;

    @PostMapping("/like/{hospitalId}")
    public ResponseEntity<ApiResponse<?>> likeHospital(
            @PathVariable Long hospitalId
    ) {
        bookmarkService.likeHospital(hospitalId);
        return ResponseEntity.ok(ApiResponse.ok(BookmarkResponseCode.LIKE_SUCCESS,null));
    }

    @PostMapping("/unlike/{hospitalId}")
    public ResponseEntity<ApiResponse<?>> unlikeHospital(
            @PathVariable Long hospitalId
    ) {
        bookmarkService.unlikeHospital(hospitalId);
        return ResponseEntity.ok(ApiResponse.ok(BookmarkResponseCode.LIKE_SUCCESS,null));
    }
}
