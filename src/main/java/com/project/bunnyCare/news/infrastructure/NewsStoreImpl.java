package com.project.bunnyCare.news.infrastructure;

import com.project.bunnyCare.news.domain.NewsEntity;
import com.project.bunnyCare.news.domain.NewsStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class NewsStoreImpl implements NewsStore {

    private final NewsRepository newsRepository;

    @Override
    public NewsEntity save(NewsEntity newsEntity) {
        return newsRepository.save(newsEntity);
    }


}
