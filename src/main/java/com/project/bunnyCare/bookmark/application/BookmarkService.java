package com.project.bunnyCare.bookmark.application;

import com.project.bunnyCare.bookmark.domain.BookmarkEntity;
import com.project.bunnyCare.bookmark.domain.BookmarkReader;
import com.project.bunnyCare.bookmark.domain.BookmarkResponseCode;
import com.project.bunnyCare.bookmark.infrastructure.BookmarkRepository;
import com.project.bunnyCare.bookmark.interfaces.dto.BookmarkRequestDto;
import com.project.bunnyCare.common.exception.ApiException;
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

    @Transactional
    public void likeHospital(BookmarkRequestDto dto) {
        // 북마크 존재하는지 확인 로직
        Long userId = AuthUtil.getUserId();
        BookmarkEntity bookmarkEntity = bookmarkReader.findByUserIdAndHospitalId(userId, dto.hospitalId());

        if(bookmarkEntity == null) {
            bookmarkRepository.save(new BookmarkEntity(dto.hospitalId(), userId));
        } else {
            if(bookmarkEntity.isUnlike()) bookmarkEntity.like();
        }
    }

    @Transactional
    public void unlikeHospital(BookmarkRequestDto dto) {
        Long userId = AuthUtil.getUserId();
        BookmarkEntity bookmarkEntity = bookmarkReader.findByUserIdAndHospitalId(userId, dto.hospitalId());
        if(bookmarkEntity == null) throw new ApiException(BookmarkResponseCode.GET_BOOKMARK_FAIL);
        if(bookmarkEntity.isUnlike()) bookmarkEntity.unlike();
    }
}
