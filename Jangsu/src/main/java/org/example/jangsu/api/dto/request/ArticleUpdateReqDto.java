package org.example.jangsu.api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ArticleUpdateReqDto(
        @NotBlank(message = "게시글 이름을 입력하여 주세요.")
        String articleName,

        @NotBlank(message = "게시글을 입력하여 주세요.")
        String content
) {
}
