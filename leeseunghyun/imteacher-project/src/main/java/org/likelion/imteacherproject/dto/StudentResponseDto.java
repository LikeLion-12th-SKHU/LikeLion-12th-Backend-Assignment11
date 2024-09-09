package org.likelion.imteacherproject.dto;

import org.likelion.imteacherproject.domain.Student;

public class StudentResponseDto {
    private Long id;
    private Long studentId;
    private String name;
    private String major;

    public StudentResponseDto(Long id, Long studentId, String name, String major) {
        this.id = id;
        this.studentId = studentId;
        this.name = name;
        this.major = major;
    }

    public static StudentResponseDto from(Student student) {
        return new StudentResponseDto(student.getId(), student.getStudentId(), student.getName(), student.getMajor());
    }

    public Long getId() {
        return id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public String getMajor() {
        return major;
    }
}