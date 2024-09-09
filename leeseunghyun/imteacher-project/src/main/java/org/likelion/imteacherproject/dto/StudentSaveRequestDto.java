package org.likelion.imteacherproject.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StudentSaveRequestDto {

    private Long studentId;
    private String name;
    private String major;

    @Builder
    public StudentSaveRequestDto(Long studentId, String name, String major) {
        this.studentId = studentId;
        this.name = name;
        this.major = major;
    }
}
