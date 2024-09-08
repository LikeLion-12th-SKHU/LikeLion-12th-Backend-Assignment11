package org.example.persistencecontext.Api.response;

import lombok.Builder;

import java.util.List;

@Builder
public record MemberListResDto(List<MemberInfoResDto> members) {
    public static MemberListResDto from(List<MemberInfoResDto> members) {
        return MemberListResDto.builder()
                .members(members).build();
    }
}
