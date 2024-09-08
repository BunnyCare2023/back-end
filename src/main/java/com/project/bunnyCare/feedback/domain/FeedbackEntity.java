package com.project.bunnyCare.feedback.domain;

import com.project.bunnyCare.common.BaseEntity;
import com.project.bunnyCare.image.domain.ImageEntity;
import com.project.bunnyCare.user.domain.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "feedback")
@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedbackEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    private FeedbackType type;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "hospital_name")
    private String hospitalName;

    @Column(name = "hospital_address")
    private String hospitalAddress;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

    @OneToMany(mappedBy = "feedback", fetch = FetchType.EAGER)
    private List<ImageEntity> images;

    @Column(name = "delete_yn")
    private String deleteYn;

    @ManyToOne
    private UserEntity createdUser;

    public void create(UserEntity user, List<ImageEntity> images) {
        this.createdUser = user;
        this.images = images;
        this.deleteYn = "N";
    }



}
