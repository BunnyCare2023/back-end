package com.project.bunnyCare.news.application;

import com.project.bunnyCare.common.util.AuthUtil;
import com.project.bunnyCare.news.domain.NewsEntity;
import com.project.bunnyCare.news.domain.NewsReader;
import com.project.bunnyCare.news.domain.NewsStore;
import com.project.bunnyCare.news.interfaces.dto.CreateNewsRequestDto;
import com.project.bunnyCare.news.interfaces.dto.NewsDetailResponseDto;
import com.project.bunnyCare.user.domain.UserEntity;
import com.project.bunnyCare.user.domain.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsStore newsStore;
    private final NewsReader newsReader;
    private final UserReader userReader;

    @Transactional
    public NewsDetailResponseDto createNews(CreateNewsRequestDto dto) {
        Long userId = AuthUtil.getUserId();
        UserEntity createUser = userReader.findById(userId);
        NewsEntity newsEntity = NewsEntity.create(dto.title(), dto.content(), createUser);
        return NewsDetailResponseDto.from(newsStore.save(newsEntity));
    }

    @Transactional
    public NewsDetailResponseDto updateNews(CreateNewsRequestDto dto, Long newsId) {
        NewsEntity newsEntity = newsReader.findById(newsId);
        UserEntity updateUser = userReader.findById(AuthUtil.getUserId());
        newsEntity.update(dto.title(), dto.content(), updateUser);
        return NewsDetailResponseDto.from(newsEntity);
    }

    @Transactional(readOnly = true)
    public List<NewsDetailResponseDto> getNewsList() {
        return newsReader.findAllByDeleteYn("N")
                .stream().map(NewsDetailResponseDto::from).toList();
    }

    @Transactional(readOnly = true)
    public NewsDetailResponseDto getNews(Long newsId) {
        return NewsDetailResponseDto.from(newsReader.findById(newsId));
    }

    @Transactional
    public void deleteNews(Long newsId) {
        NewsEntity newsEntity = newsReader.findById(newsId);
        newsEntity.delete();
    }
}
