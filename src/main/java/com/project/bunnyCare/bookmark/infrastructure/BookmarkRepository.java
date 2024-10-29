package com.project.bunnyCare.bookmark.infrastructure;

import com.project.bunnyCare.bookmark.domain.BookmarkEntity;
import com.project.bunnyCare.bookmark.domain.BookmarkId;
import com.project.bunnyCare.hospital.domain.HospitalEntity;
import com.project.bunnyCare.user.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<BookmarkEntity, BookmarkId> {

    Optional<BookmarkEntity> findByHospitalIdAndUserId(Long hospitalId, Long userId);

}
