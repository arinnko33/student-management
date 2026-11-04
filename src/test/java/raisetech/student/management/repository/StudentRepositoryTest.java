package raisetech.student.management.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import raisetech.student.management.data.Student;

@MybatisTest(properties = {
    "mybatis.configuration.map-underscore-to-camel-case=true"
})
class StudentRepositoryTest {

  @Autowired
  private StudentRepository studentRepository;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @BeforeEach
  void setUp() {
    jdbcTemplate.update("DELETE FROM students_courses_status");
    jdbcTemplate.update("DELETE FROM students_courses");
    jdbcTemplate.update("DELETE FROM students");

    // テストデータを5件登録
    insertStudent(1, "20代の受講生", 29, 0);
    insertStudent(2, "30歳の受講生", 30, 0);
    insertStudent(3, "39歳の受講生", 39, 0);
    insertStudent(4, "40歳の受講生", 40, 0);
    insertStudent(5, "削除済みの受講生", 35, 1);

    jdbcTemplate.execute(
        "ALTER TABLE students ALTER COLUMN id RESTART WITH 6"
    );
  }

  private void insertStudent(int id, String name, int age, int deleteFlag) {
    jdbcTemplate.update(
        "INSERT INTO students "
            + "(id, nickname, name, furigana, age, gender, email, region, note, delete_flag) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
        id,
        "テスト",
        name,
        "テスト",
        age,
        "男性",
        "test" + id + "@example.com",
        "東京都",
        "テスト用データ",
        deleteFlag
    );
  }

  @Test
  void findAll_論理削除されていない受講生を取得できる() {
    var students = studentRepository.findAll();

    assertThat(students).hasSize(4);
    assertThat(students)
        .extracting(Student::getName)
        .contains("20代の受講生", "30歳の受講生", "39歳の受講生", "40歳の受講生")
        .doesNotContain("削除済みの受講生");
  }

  @Test
  void findById_IDを指定して受講生を取得できる() {
    Student student = studentRepository.findById(2);

    assertThat(student).isNotNull();
    assertThat(student.getId()).isEqualTo(2);
    assertThat(student.getName()).isEqualTo("30歳の受講生");
    assertThat(student.getAge()).isEqualTo(30);
  }

  @Test
  void findById_論理削除された受講生は取得できない() {
    Student student = studentRepository.findById(5);

    assertThat(student).isNull();
  }

  @Test
  void registerStudent_受講生を登録できる() {
    Student student = new Student();
    student.setNickname("新規登録");
    student.setName("登録テスト");
    student.setFurigana("トウロクテスト");
    student.setAge(25);
    student.setGender("女性");
    student.setEmail("register@example.com");
    student.setNote("登録テスト用");
    student.setDeleteFlag(false);

    studentRepository.registerStudent(student);

    assertThat(student.getId()).isPositive();

    Student registeredStudent = studentRepository.findById(student.getId());

    assertThat(registeredStudent).isNotNull();
    assertThat(registeredStudent.getName()).isEqualTo("登録テスト");
    assertThat(registeredStudent.getAge()).isEqualTo(25);
    assertThat(registeredStudent.getEmail()).isEqualTo("register@example.com");
  }

  @Test
  void updateStudent_受講生の情報を更新できる() {
    Student student = studentRepository.findById(2);

    student.setNickname("更新後");
    student.setName("更新テスト");
    student.setAge(31);
    student.setEmail("updated@example.com");
    student.setNote("更新しました");

    studentRepository.updateStudent(student);

    Student updatedStudent = studentRepository.findById(2);

    assertThat(updatedStudent).isNotNull();
    assertThat(updatedStudent.getNickname()).isEqualTo("更新後");
    assertThat(updatedStudent.getName()).isEqualTo("更新テスト");
    assertThat(updatedStudent.getAge()).isEqualTo(31);
    assertThat(updatedStudent.getEmail()).isEqualTo("updated@example.com");
    assertThat(updatedStudent.getNote()).isEqualTo("更新しました");
  }

  @Test
  void deleteStudent_受講生を論理削除できる() {
    studentRepository.deleteStudent(2);

    Integer deleteFlag = jdbcTemplate.queryForObject(
        "SELECT delete_flag FROM students WHERE id = 2",
        Integer.class
    );

    assertThat(deleteFlag).isEqualTo(1);
    assertThat(studentRepository.findById(2)).isNull();
  }

  @Test
  void findStudentsInTheir30s_30代の受講生だけを取得できる() {
    var students = studentRepository.findStudentsInTheir30s();

    assertThat(students).hasSize(2);
    assertThat(students)
        .extracting(Student::getName)
        .containsExactlyInAnyOrder("30歳の受講生", "39歳の受講生");
  }

  @Test
  void findStudentsByCourseStatus_仮申込の受講生を検索できる() {
    insertStudentCourseStatus("仮申込");

    var students = studentRepository.findStudentsByCourseStatus("仮申込");

    assertThat(students).hasSize(1);
    assertThat(students.get(0).getId()).isEqualTo(1);
  }

  @Test
  void findStudentsByCourseStatus_本申込の受講生を検索できる() {
    insertStudentCourseStatus("本申込");

    var students = studentRepository.findStudentsByCourseStatus("本申込");

    assertThat(students).hasSize(1);
    assertThat(students.get(0).getId()).isEqualTo(1);
  }

  @Test
  void findStudentsByCourseStatus_受講中の受講生を検索できる() {
    insertStudentCourseStatus("受講中");

    var students = studentRepository.findStudentsByCourseStatus("受講中");

    assertThat(students).hasSize(1);
    assertThat(students.get(0).getId()).isEqualTo(1);
  }

  @Test
  void findStudentsByCourseStatus_受講終了の受講生を検索できる() {
    insertStudentCourseStatus("受講終了");

    var students = studentRepository.findStudentsByCourseStatus("受講終了");

    assertThat(students).hasSize(1);
    assertThat(students.get(0).getId()).isEqualTo(1);
  }

  private void insertStudentCourseStatus(String status) {
    jdbcTemplate.update("""
      INSERT INTO students_courses
      (id, student_id, course_name, start_date, end_date)
      VALUES (1, 1, 'Javaコース', '2026-01-01', '2026-06-30')
      """);

    jdbcTemplate.update("""
      INSERT INTO students_courses_status
      (student_course_id, status)
      VALUES (1, ?)
      """, status);
  }
}
