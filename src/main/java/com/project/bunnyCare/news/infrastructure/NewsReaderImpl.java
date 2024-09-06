package com.project.bunnyCare.news.infrastructure;

import com.project.bunnyCare.common.exception.ApiException;
import com.project.bunnyCare.news.domain.NewsEntity;
import com.project.bunnyCare.news.domain.NewsReader;
import com.project.bunnyCare.news.domain.NewsResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class NewsReaderImpl implements NewsReader {

    private final NewsRepository newsRepository;

    @Override
    public NewsEntity findById(Long id) {
        return newsRepository.findById(id).orElseThrow(() -> new ApiException(NewsResponseCode.NOT_FOUND));
    }

    @Override
    public List<NewsEntity> findAllByDeleteYn(String n) {
        return newsRepository.findAllByDeleteYn(n);
    }
}
