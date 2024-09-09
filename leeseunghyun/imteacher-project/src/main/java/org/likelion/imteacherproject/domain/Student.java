package org.likelion.imteacherproject.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Student {

    private Long id;
    private Long studentId;
    private String name;
    private String major;

    @Builder
    public Student(Long id, Long studentId,String name, String major){
        this.studentId = studentId;
        this.id = id;
        this.name = name;
        this.major = major;
    }
    public void update(String name , String major){
        this.name = name;
        this.major = major;
    }
}
