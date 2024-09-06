package com.project.bunnyCare.news.domain;

import java.util.List;

public interface NewsReader {
    NewsEntity findById(Long id);
    List<NewsEntity> findAllByDeleteYn(String n);
}
