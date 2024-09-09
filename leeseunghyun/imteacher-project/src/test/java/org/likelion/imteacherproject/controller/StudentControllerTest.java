package org.likelion.imteacherproject.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.likelion.imteacherproject.domain.Student;
import org.likelion.imteacherproject.domain.StudentRepository;
import org.likelion.imteacherproject.dto.StudentResponseDto;
import org.likelion.imteacherproject.dto.StudentSaveRequestDto;
import org.likelion.imteacherproject.dto.StudentUpdateRequestDto;
import org.likelion.imteacherproject.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.likelion.imteacherproject.fixture.StudentFixture.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class StudentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    StudentService studentService;

    @Autowired
    StudentRepository studentRepository;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @AfterEach
    public void afterEach() {
        studentRepository.clear();
    }

    @Test
    @DisplayName("학생을 정상 저장한다")
    public void saveStudent() throws Exception {
        final StudentSaveRequestDto requestDto = StudentSaveRequestDto.builder()
                .studentId(STUDENT_1.getStudentId())
                .name(STUDENT_1.getName())
                .major(STUDENT_1.getMajor())
                .build();

        String url = "http://localhost:" + port + "/students";

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .content(asJsonString(requestDto))
                        .contentType("application/json"))
                .andReturn();

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());

        List<Student> all = studentRepository.findAll();
        assertThat(all.get(0).getStudentId()).isEqualTo(STUDENT_1.getStudentId());
        assertThat(all.get(0).getName()).isEqualTo(STUDENT_1.getName());
        assertThat(all.get(0).getMajor()).isEqualTo(STUDENT_1.getMajor());
    }

    @Test
    @DisplayName("학생을 정상 조회한다")
    public void getStudent() throws Exception {
        studentRepository.save(STUDENT_2);

        String url = "http://localhost:" + port + "/students/" + STUDENT_2.getId();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(url)).andReturn();

        final StudentResponseDto studentResponse = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                new TypeReference<>() {}
        );

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());

        assertThat(studentResponse.getStudentId()).isEqualTo(STUDENT_2.getStudentId());
        assertThat(studentResponse.getName()).isEqualTo(STUDENT_2.getName());
        assertThat(studentResponse.getMajor()).isEqualTo(STUDENT_2.getMajor());
    }

    @Test
    @DisplayName("학생 전체를 정상 조회한다")
    public void getAllStudent() throws Exception {
        studentRepository.save(STUDENT_1);
        studentRepository.save(STUDENT_2);
        studentRepository.save(STUDENT_3);

        String url = "http://localhost:" + port + "/students";

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(url)).andReturn();

        final List<StudentResponseDto> actualResponses = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                new TypeReference<>() {}
        );

        List<StudentResponseDto> expectedResponses = studentService.findAllStudent();
        assertThat(actualResponses).usingRecursiveComparison().isEqualTo(expectedResponses);
    }

    @Test
    @DisplayName("학생을 정상 수정한다")
    public void updateStudent() throws Exception {
        final Student savedStudent = studentRepository.save(STUDENT_1);

        Long updateId = savedStudent.getId();
        String newName = "주영빈";
        String newMajor = "컴퓨터공학전공";

        StudentUpdateRequestDto requestDto = StudentUpdateRequestDto.builder()
                .name(newName)
                .major(newMajor)
                .build();

        String url = "http://localhost:" + port + "/students/" + updateId;

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.patch(url)
                        .content(asJsonString(requestDto))
                        .contentType("application/json"))
                .andReturn();

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());

        List<Student> all = studentRepository.findAll();
        assertThat(all.get(0).getName()).isEqualTo(newName);
        assertThat(all.get(0).getMajor()).isEqualTo(newMajor);
    }

    @Test
    @DisplayName("학생을 정상 삭제한다")
    public void deleteStudent() throws Exception {
        final Student savedStudent = studentRepository.save(STUDENT_1);

        String url = "http://localhost:" + port + "/students/" + savedStudent.getId();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(url)).andReturn();

        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());

        List<Student> all = studentRepository.findAll();
        assertThat(all.size()).isEqualTo(0);
    }

    private String asJsonString(final Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}