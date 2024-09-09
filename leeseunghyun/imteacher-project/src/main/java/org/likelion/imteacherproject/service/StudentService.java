package org.likelion.imteacherproject.service;

import lombok.RequiredArgsConstructor;
import org.likelion.imteacherproject.domain.Student;
import org.likelion.imteacherproject.domain.StudentRepository;
import org.likelion.imteacherproject.dto.StudentResponseDto;
import org.likelion.imteacherproject.dto.StudentSaveRequestDto;
import org.likelion.imteacherproject.dto.StudentUpdateRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    private  static Long sequence = 0L;

    public Student saveStudent(StudentSaveRequestDto requestDto) {
        Student student = Student.builder()
                .id(++sequence)
                .studentId(requestDto.getStudentId())
                .name(requestDto.getName())
                .major(requestDto.getMajor())
                .build();

        return studentRepository.save(student);
    }

    public StudentResponseDto findStudentById(Long id){
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 학생이 없습니다. id " + id));

        return StudentResponseDto.from(student);
    }

    public List<StudentResponseDto> findAllStudent() {
        return studentRepository.findAll().stream()
                .map(StudentResponseDto::from)
                .collect(Collectors.toList());
    }

    public Student updateStudentById(Long id, StudentUpdateRequestDto requestDto){
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 학생이 없습니다. id = " + id));

        student.update(requestDto.getName(), requestDto.getMajor());

        return  studentRepository.save(student);
    }


    public void deleteStudentById(Long id){
        studentRepository.deleteById(id);
    }
}
