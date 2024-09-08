package org.example.persistencecontext.Api.response;

import lombok.Builder;
import org.example.persistencecontext.domain.Member;

@Builder
public record MemberInfoResDto(Long memberId, String name) {
    public static MemberInfoResDto from(Member member) {
        return MemberInfoResDto.builder()
                .memberId(member.getMemberId())
                .name(member.getName()).build();
    }
}
