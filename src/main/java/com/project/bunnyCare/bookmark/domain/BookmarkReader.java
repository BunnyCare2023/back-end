package com.project.bunnyCare.bookmark.domain;

public interface BookmarkReader {

    BookmarkEntity findByUserIdAndHospitalId(Long userId, Long hospitalId);
}
