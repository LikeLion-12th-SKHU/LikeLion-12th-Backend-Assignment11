package org.example.jangsu.api.dto.response;

import lombok.Builder;
import org.example.jangsu.domain.Article;

@Builder
public record ArticleInfoResDto(
        String articleName,
        String articleWriteTime,
        String content
) {
    public static ArticleInfoResDto from (Article article) {
        return ArticleInfoResDto.builder()
                .articleName(article.getArticleName())
                .articleWriteTime(article.getArticleWriteTime())
                .content(article.getContent()).build();
    }
}
