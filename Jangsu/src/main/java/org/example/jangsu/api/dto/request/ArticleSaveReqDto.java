package org.example.jangsu.api.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.example.jangsu.domain.Member;

@Valid
public record ArticleSaveReqDto(
        @NotBlank(message = "게시글 이름을 입력하여 주세요.")
        String articleName,

        String articleWriteTime,

        @NotBlank(message = "게시글을 입력하여 주세요.")
        String content,

        Member member
) {
}
