package raisetech.student.management.converter;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.StudentDetail;

class StudentConverterTest {

  private final StudentConverter studentConverter = new StudentConverter();

  @Test
  void 受講生一覧を受講生詳細一覧に変換できる() {
    // Given：テストデータを準備する
    Student student1 = new Student();
    student1.setId(1);
    student1.setName("山田太郎");

    Student student2 = new Student();
    student2.setId(2);
    student2.setName("佐藤花子");

    StudentCourse course1 = new StudentCourse();
    course1.setId(1);
    course1.setStudentId(1);
    course1.setCourseName("Javaコース");

    StudentCourse course2 = new StudentCourse();
    course2.setId(2);
    course2.setStudentId(2);
    course2.setCourseName("Spring Bootコース");

    List<Student> students = List.of(student1, student2);
    List<StudentCourse> studentCourses = List.of(course1, course2);

    // When：変換処理を実行する
    List<StudentDetail> result =
        studentConverter.convertStudentDetails(students, studentCourses);

    // Then：変換結果を確認する
    assertThat(result).hasSize(2);

    assertThat(result.get(0).getStudent().getName()).isEqualTo("山田太郎");
    assertThat(result.get(0).getStudentCourses())
        .hasSize(1)
        .extracting(StudentCourse::getCourseName)
        .containsExactly("Javaコース");

    assertThat(result.get(1).getStudent().getName()).isEqualTo("佐藤花子");
    assertThat(result.get(1).getStudentCourses())
        .hasSize(1)
        .extracting(StudentCourse::getCourseName)
        .containsExactly("Spring Bootコース");
  }

  @Test
  void 受講コースがない受講生も変換できる() {
    // Given
    Student student = new Student();
    student.setId(1);
    student.setName("山田太郎");

    // When
    List<StudentDetail> result =
        studentConverter.convertStudentDetails(List.of(student), List.of());

    // Then
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getStudent().getName()).isEqualTo("山田太郎");
    assertThat(result.get(0).getStudentCourses()).isEmpty();
  }

  @Test
  void 受講生詳細を1件変換できる() {
    // Given
    Student student = new Student();
    student.setId(1);
    student.setName("山田太郎");

    StudentCourse course = new StudentCourse();
    course.setId(1);
    course.setStudentId(1);
    course.setCourseName("Javaコース");

    List<StudentCourse> studentCourses = List.of(course);

    // When
    StudentDetail result =
        studentConverter.convertStudentDetail(student, studentCourses);

    // Then
    assertThat(result.getStudent()).isSameAs(student);
    assertThat(result.getStudentCourses()).containsExactly(course);
  }
}
