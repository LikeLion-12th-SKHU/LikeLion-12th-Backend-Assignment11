package org.likelion.imteacherproject.controller;

import lombok.RequiredArgsConstructor;
import org.likelion.imteacherproject.dto.StudentResponseDto;
import org.likelion.imteacherproject.dto.StudentSaveRequestDto;
import org.likelion.imteacherproject.dto.StudentUpdateRequestDto;
import org.likelion.imteacherproject.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping("students")
    public void save(@RequestBody StudentSaveRequestDto requestDto) {
        studentService.saveStudent(requestDto);
    }

    @GetMapping("students/{id}")
    public StudentResponseDto findStudentById(@PathVariable Long id) {
        return studentService.findStudentById(id);
    }

    @GetMapping("students")
    public List<StudentResponseDto> findAllStudent() {
        return studentService.findAllStudent();
    }

    @PatchMapping("students/{id}")
    public void updateStudentById(@PathVariable Long id, @RequestBody StudentUpdateRequestDto requestDto) {
        studentService.updateStudentById(id, requestDto);
    }

    @DeleteMapping("students/{id}")
    public void deleteStudentById(@PathVariable Long id) {
        studentService.deleteStudentById(id);
    }
}