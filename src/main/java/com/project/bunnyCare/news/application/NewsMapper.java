package com.project.bunnyCare.news.application;

import com.project.bunnyCare.news.domain.NewsEntity;
import com.project.bunnyCare.news.interfaces.dto.CreateNewsRequestDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface NewsMapper {

    NewsEntity toEntity(CreateNewsRequestDto requestDto);
}
