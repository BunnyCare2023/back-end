package com.project.bunnyCare.bookmark.infrastructure;

import com.project.bunnyCare.bookmark.domain.BookmarkEntity;
import com.project.bunnyCare.bookmark.domain.BookmarkReader;
import com.project.bunnyCare.hospital.domain.HospitalReader;
import com.project.bunnyCare.user.domain.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookmarkReaderImpl implements BookmarkReader {

    private final BookmarkRepository bookmarkRepository;
    private final UserReader userReader;
    private final HospitalReader hospitalReader;
    @Override
    public BookmarkEntity findByUserIdAndHospitalId(Long userId, Long hospitalId) {
        return bookmarkRepository.findByHospitalIdAndUserId(hospitalId, userId)
                .orElse(null);
    }
}
