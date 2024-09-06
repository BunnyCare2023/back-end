package com.project.bunnyCare.news.interfaces;

import com.project.bunnyCare.common.api.ApiResponse;
import com.project.bunnyCare.news.application.NewsService;
import com.project.bunnyCare.news.domain.NewsResponseCode;
import com.project.bunnyCare.news.interfaces.dto.CreateNewsRequestDto;
import com.project.bunnyCare.news.interfaces.dto.NewsDetailResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/${api.version}/news")
@RequiredArgsConstructor
public class NewsApiController {

    private final NewsService newsService;

    @PostMapping
    public ResponseEntity<ApiResponse<NewsDetailResponseDto>> createNews(
            @RequestBody CreateNewsRequestDto requestDto
            ) {
        NewsDetailResponseDto response = newsService.createNews(requestDto);
        return ResponseEntity.ok(ApiResponse.ok(NewsResponseCode.CREATE,response));
    }

    @PatchMapping("/{newsId}")
    public ResponseEntity<ApiResponse<NewsDetailResponseDto>> updateNews(
            @RequestBody CreateNewsRequestDto requestDto,
            @PathVariable Long newsId
    ) {
        NewsDetailResponseDto response = newsService.updateNews(requestDto, newsId);
        return ResponseEntity.ok(ApiResponse.ok(NewsResponseCode.UPDATE, response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<NewsDetailResponseDto>>> getNewsList(){
        List<NewsDetailResponseDto> response = newsService.getNewsList();
        return ResponseEntity.ok(ApiResponse.ok(NewsResponseCode.SUCCESS, response));
    }

    @GetMapping("/{newsId}")
    public ResponseEntity<ApiResponse<NewsDetailResponseDto>> getNews(@PathVariable Long newsId){
        NewsDetailResponseDto response = newsService.getNews(newsId);
        return ResponseEntity.ok(ApiResponse.ok(NewsResponseCode.SUCCESS, response));
    }

    @DeleteMapping("/{newsId}")
    public ResponseEntity<ApiResponse<Void>> deleteNews(@PathVariable Long newsId){
        newsService.deleteNews(newsId);
        return ResponseEntity.ok(ApiResponse.ok(NewsResponseCode.DELETE));
    }
}
