package org.likelion.imteacherproject.fixture;

import org.likelion.imteacherproject.domain.Student;

public class StudentFixture {
    public static final Student STUDENT_1 = new Student(
            1L,
            201914089L,
            "오동재",
            "소프트웨어공학전공"
    );

    public static final Student STUDENT_2 = new Student(
            2L,
            202012345L,
            "안준영",
            "정보통신공학전공"
    );


    public static final Student STUDENT_3 = new Student(
            3L,
            202100000L,
            "주영빈",
            "컴퓨터공학전공"
    );
}
