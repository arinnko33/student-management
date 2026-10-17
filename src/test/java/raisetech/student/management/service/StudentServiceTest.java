package raisetech.student.management.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.student.management.converter.StudentConverter;
import raisetech.student.management.data.Student;
import raisetech.student.management.repository.StudentCourseRepository;
import raisetech.student.management.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock
  private StudentRepository studentRepository;

  @Mock
  private StudentCourseRepository studentCourseRepository;

  @Mock
  private StudentConverter studentConverter;

  @Test
  void 受講生一覧検索_全件検索が動作すること() {

    // 検索結果として返すデータ
    List<Student> expected = new ArrayList<>();

    // 「findAll()が呼ばれたらexpectedを返す」と設定
    when(studentRepository.findAll()).thenReturn(expected);

    // StudentServiceを作成
    StudentService sut =
        new StudentService(studentRepository, studentCourseRepository, studentConverter);

    // 実際に検索
    List<Student> actual = sut.findAllStudents();

    // 結果を比較
    Assertions.assertEquals(expected, actual);
  }

  @Test
  void 受講生登録_受講生とコースが登録されること() {

// テスト用の受講生を作成
    Student student = new Student();
    student.setId(1);

    String courseName = "Javaコース";
    LocalDate startDate = LocalDate.of(2026, 10, 1);
    LocalDate endDate = LocalDate.of(2027, 3, 31);

// StudentServiceを作成
    StudentService sut =
        new StudentService(studentRepository, studentCourseRepository, studentConverter);

// 登録処理を実行
    sut.registerStudent(student, courseName, startDate, endDate);

// 受講生の登録が呼ばれたことを確認
    verify(studentRepository).registerStudent(student);

// コース登録が呼ばれたことを確認
    verify(studentCourseRepository).registerStudentCourse(
        org.mockito.ArgumentMatchers.any());
  }

  @Test
  void 受講生更新_受講生情報が更新されること() {

// テスト用の受講生を作成
    Student student = new Student();
    student.setId(1);
    student.setName("テスト太郎");

// StudentServiceを作成
    StudentService sut =
        new StudentService(studentRepository, studentCourseRepository, studentConverter);

// 更新処理を実行
    sut.updateStudent(student);

// StudentRepositoryのupdateStudentが呼ばれたことを確認
    verify(studentRepository).updateStudent(student);
  }
}