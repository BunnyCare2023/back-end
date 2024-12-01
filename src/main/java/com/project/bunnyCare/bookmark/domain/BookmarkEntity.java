package com.project.bunnyCare.bookmark.domain;

import com.project.bunnyCare.hospital.domain.HospitalEntity;
import com.project.bunnyCare.user.domain.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "bookmark")
@Entity
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookmarkEntity {

//    @EmbeddedId
//    private BookmarkId id;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long hospitalId;

    private Long userId;

    private String state; // 북마크 상태 (Y: 북마크, N: 북마크 해제)

    public BookmarkEntity(Long hospitalId, Long userId) {
        this.hospitalId = hospitalId;
        this.userId = userId;
        this.state = "Y";
    }

    public boolean isLike() {
        return this.state.equals("Y");
    }
    public boolean isUnlike() {
        return this.state.equals("N");
    }

    public void like() {
        this.state = "Y";
    }

    public void unlike() {
        this.state = "N";
    }

}
