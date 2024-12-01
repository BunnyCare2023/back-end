package com.project.bunnyCare.bookmark.infrastructure;

import com.project.bunnyCare.bookmark.domain.BookmarkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<BookmarkEntity, Long> {

    Optional<BookmarkEntity> findByHospitalIdAndUserId(Long hospitalId, Long userId);

}
