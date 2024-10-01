package com.project.bunnyCare.bookmark.application;

import com.project.bunnyCare.bookmark.domain.BookmarkEntity;
import com.project.bunnyCare.bookmark.domain.BookmarkId;
import com.project.bunnyCare.bookmark.domain.BookmarkReader;
import com.project.bunnyCare.bookmark.infrastructure.BookmarkRepository;
import com.project.bunnyCare.common.util.AuthUtil;
import com.project.bunnyCare.hospital.domain.HospitalEntity;
import com.project.bunnyCare.hospital.domain.HospitalReader;
import com.project.bunnyCare.user.domain.UserEntity;
import com.project.bunnyCare.user.domain.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkReader bookmarkReader;
    private final BookmarkRepository bookmarkRepository;
    private final UserReader userReader;
    private final HospitalReader hospitalReader;


    @Transactional
    public void likeHospital(Long hospitalId) {
        // 북마크 존재하는지 확인 로직
        Long userId = AuthUtil.getUserId();
        BookmarkEntity bookmarkEntity = bookmarkReader.findByUserIdAndHospitalId(userId, hospitalId);

        if(bookmarkEntity == null) {
            // 북마크 생성
            UserEntity user = userReader.findById(userId);
            HospitalEntity hospital = hospitalReader.findById(hospitalId);
            BookmarkId bookmarkId = new BookmarkId(user.getId(), hospital.getId());
            BookmarkEntity newBookmark = BookmarkEntity.builder()
                            .id(bookmarkId)
                                    .build();
            newBookmark.like();
            bookmarkRepository.save(newBookmark);
        } else {
            // 북마크 상태 변경
            bookmarkEntity.like();
        }
    }

    @Transactional
    public void unlikeHospital(Long hospitalId) {
        // 북마크 존재하는지 확인 로직
        Long userId = AuthUtil.getUserId();
        BookmarkEntity bookmarkEntity = bookmarkReader.findByUserIdAndHospitalId(userId, hospitalId);
        bookmarkEntity.unlike();
    }
}
