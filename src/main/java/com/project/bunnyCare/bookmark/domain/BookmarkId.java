package com.project.bunnyCare.bookmark.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkId implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "hospital_id")
    private Long hospitalId;
}
