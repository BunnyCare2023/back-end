package com.project.bunnyCare.bookmark.infrastructure;

import com.project.bunnyCare.bookmark.domain.BookmarkEntity;
import com.project.bunnyCare.bookmark.domain.BookmarkId;
import com.project.bunnyCare.bookmark.domain.BookmarkReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookmarkReaderImpl implements BookmarkReader {

    private final BookmarkRepository bookmarkRepository;

    @Override
    public BookmarkEntity findByUserIdAndHospitalId(Long userId, Long hospitalId) {
        BookmarkId bookmarkId = new BookmarkId(userId, hospitalId);
        return bookmarkRepository.findById(bookmarkId).orElse(null);
    }
}
