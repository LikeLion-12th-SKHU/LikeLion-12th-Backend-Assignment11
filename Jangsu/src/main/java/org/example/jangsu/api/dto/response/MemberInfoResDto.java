package org.example.jangsu.api.dto.response;

import lombok.Builder;
import org.example.jangsu.domain.Member;
import org.example.jangsu.domain.Status;

@Builder
public record MemberInfoResDto(
        String email,
        String password,
        String nickname,
        Status status
) {
    public static MemberInfoResDto from (Member member) {
        return MemberInfoResDto.builder()
                .email(member.getEmail())
                .password(member.getPassword())
                .nickname(member.getNickname())
                .status(member.getStatus()).build();
    }
}
