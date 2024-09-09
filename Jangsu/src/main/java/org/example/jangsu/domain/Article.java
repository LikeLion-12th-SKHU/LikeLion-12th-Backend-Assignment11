package org.example.jangsu.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.jangsu.api.dto.request.ArticleUpdateReqDto;

@Entity
@Getter
@Table(name = "articles")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {
    @Id
    @Column(name = "article_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleId;

    private String articleName;
    private String articleWriteTime;
    private String content;

    @ManyToOne
    private Member member;

    @Builder
    private Article(String articleName, String articleWriteTime, String content, Member member) {
        this.articleName = articleName;
        this.articleWriteTime = articleWriteTime;
        this.content = content;
        this.member = member;
    }

    public void update(ArticleUpdateReqDto articleUpdateReqDto) {
        this.articleName = articleUpdateReqDto.articleName();
        this.content = articleUpdateReqDto.content();
    }
}
