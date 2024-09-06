package com.project.bunnyCare.news.infrastructure;

import com.project.bunnyCare.news.domain.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsRepository extends JpaRepository<NewsEntity, Long> {

    List<NewsEntity> findAllByDeleteYn(String n);
}
