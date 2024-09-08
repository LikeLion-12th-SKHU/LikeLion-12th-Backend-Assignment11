package org.example.jangsu.api.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ArticleListResDto(
        List<ArticleInfoResDto> articles
) {
    public static ArticleListResDto from(List<ArticleInfoResDto> articles) {
        return ArticleListResDto.builder()
                .articles(articles).build();
    }
}
