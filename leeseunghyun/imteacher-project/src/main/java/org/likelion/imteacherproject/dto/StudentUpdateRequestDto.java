package org.likelion.imteacherproject.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StudentUpdateRequestDto {
    private String name;
    private String major;

    @Builder
    public StudentUpdateRequestDto(String name, String major) {
        this.name = name;
        this.major = major;
    }
}