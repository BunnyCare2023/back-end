package com.project.bunnyCare.news.domain;

import com.project.bunnyCare.common.BaseEntity;
import com.project.bunnyCare.user.domain.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "news")
@Entity
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "delete_yn")
    private String deleteYn;

    @ManyToOne
    private UserEntity createUser;

    @ManyToOne
    private UserEntity updateUser;


    public static NewsEntity create(String title, String content, UserEntity user) {
       return NewsEntity.builder()
               .title(title)
               .content(content)
               .createUser(user)
               .deleteYn("N")
               .build();
    }

    public void update(String title, String content, UserEntity user) {
        this.title = title;
        this.content = content;
        this.updateUser = user;
    }

    public void delete() {
        this.deleteYn = "Y";
    }

}
