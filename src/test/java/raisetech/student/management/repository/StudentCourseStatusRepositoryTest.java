package raisetech.student.management.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import raisetech.student.management.data.StudentCourseStatus;

@MybatisTest(properties = {
    "mybatis.configuration.map-underscore-to-camel-case=true"
})
class StudentCourseStatusRepositoryTest {

  @Autowired
  private StudentCourseStatusRepository studentCourseStatusRepository;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @BeforeEach
  void setUp() {
    jdbcTemplate.update("DELETE FROM students_courses_status");
    jdbcTemplate.update("DELETE FROM students_courses");
    jdbcTemplate.update("DELETE FROM students");

    jdbcTemplate.update("""
        INSERT INTO students
        (id, nickname, name, furigana, age, gender, email, region, note, delete_flag)
        VALUES (1, 'テスト', 'テスト受講生', 'テスト', 25, '女性',
                'test@example.com', '東京都', 'テスト用', 0)
        """);

    jdbcTemplate.update("""
        INSERT INTO students_courses
        (id, student_id, course_name, start_date, end_date)
        VALUES (1, 1, 'Javaコース', '2026-01-01', '2026-06-30')
        """);
  }

  @Test
  void registerStatus_申込状況を登録できる() {
    StudentCourseStatus status = new StudentCourseStatus();
    status.setStudentCourseId(1);
    status.setStatus("仮申込");

    studentCourseStatusRepository.registerStatus(status);

    assertThat(status.getId()).isPositive();

    StudentCourseStatus registered =
        studentCourseStatusRepository.findByStudentCourseId(1);

    assertThat(registered).isNotNull();
    assertThat(registered.getStudentCourseId()).isEqualTo(1);
    assertThat(registered.getStatus()).isEqualTo("仮申込");
  }

  @Test
  void updateStatus_申込状況を更新できる() {
    jdbcTemplate.update("""
        INSERT INTO students_courses_status (student_course_id, status)
        VALUES (1, '仮申込')
        """);

    StudentCourseStatus status =
        studentCourseStatusRepository.findByStudentCourseId(1);
    status.setStatus("本申込");

    studentCourseStatusRepository.updateStatus(status);

    StudentCourseStatus updated =
        studentCourseStatusRepository.findByStudentCourseId(1);

    assertThat(updated.getStatus()).isEqualTo("本申込");
  }

  @Test
  void findAll_すべての申込状況を取得できる() {
    jdbcTemplate.update("""
        INSERT INTO students_courses_status (student_course_id, status)
        VALUES (1, '受講中')
        """);

    var statuses = studentCourseStatusRepository.findAll();

    assertThat(statuses).hasSize(1);
    assertThat(statuses.get(0).getStudentCourseId()).isEqualTo(1);
    assertThat(statuses.get(0).getStatus()).isEqualTo("受講中");
  }
}