package com.project.bunnyCare.bookmark.infrastructure;

import com.project.bunnyCare.bookmark.domain.BookmarkEntity;
import com.project.bunnyCare.bookmark.domain.BookmarkId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<BookmarkEntity, BookmarkId> {

}
