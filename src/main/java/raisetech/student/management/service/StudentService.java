package raisetech.student.management.service;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import raisetech.student.management.converter.StudentConverter;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.repository.StudentCourseRepository;
import raisetech.student.management.repository.StudentRepository;

@Service
@RequiredArgsConstructor
public class StudentService {

  private final StudentRepository repository;
  private final StudentCourseRepository studentCourseRepository;
  private final StudentConverter studentConverter;


  // 全受講生を検索
  public List<Student> findAllStudents() {
    return repository.findAll();
  }

  // IDから受講生を検索
  public Student findStudentById(int id) {
    return repository.findById(id);
  }
  // 30代の受講生を検索
  public List<Student> searchStudentList() {
    return repository.search();
  }

  // 全受講生の詳細情報を検索
  public List<StudentDetail> getStudentDetails() {

    List<Student> students = repository.findAll();
    List<StudentCourse> studentCourses = studentCourseRepository.findAll();

    return studentConverter.convertStudentDetails(students, studentCourses);
  }

  // 受講生詳細を検索
  public StudentDetail getStudentDetail(int id) {
    Student student = repository.findById(id);
    List<StudentCourse> studentCourses =
        studentCourseRepository.findByStudentId(id);

    return studentConverter.convertStudentDetail(student, studentCourses);
  }

  // 登録
  public void registerStudent(
      Student student,
      String courseName,
      LocalDate startDate,
      LocalDate endDate) {

    repository.registerStudent(student);

    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setStudentId(student.getId());
    studentCourse.setCourseName(courseName);
    studentCourse.setStartDate(startDate);
    studentCourse.setEndDate(endDate);

    studentCourseRepository.registerStudentCourse(studentCourse);
  }
  // 更新
  public void updateStudent(
      Student student,
      String courseName,
      LocalDate startDate,
      LocalDate endDate) {

    // 受講生情報を更新
    repository.updateStudent(student);

    // 受講生のコース情報を検索
    List<StudentCourse> studentCourses =
        studentCourseRepository.findByStudentId(student.getId());

    // コース情報がある場合だけ更新
    if (!studentCourses.isEmpty()
        && courseName != null
        && startDate != null
        && endDate != null) {

      StudentCourse studentCourse = studentCourses.get(0);

      studentCourse.setCourseName(courseName);
      studentCourse.setStartDate(startDate);
      studentCourse.setEndDate(endDate);

      studentCourseRepository.updateStudentCourse(studentCourse);
    }
  }

  // 削除
  public void deleteStudent(int id) {
    repository.deleteStudent(id);
  }

  // コース名を重複なしで検索
  public List<String> getCourseNames() {
    return studentCourseRepository.findAllCourseNames();
  }

  // Javaコースを検索
  public List<StudentCourse> searchJavaCourse() {
    return studentCourseRepository.searchJavaCourse();
  }

  // 受講生のコース情報を検索
  public List<StudentCourse> getStudentCourses(int studentId) {
    return studentCourseRepository.findByStudentId(studentId);
  }

  public void addStudentCourse(
      int studentId,
      String courseName,
      LocalDate startDate,
      LocalDate endDate) {

    StudentCourse studentCourse = new StudentCourse();

    studentCourse.setStudentId(studentId);
    studentCourse.setCourseName(courseName);
    studentCourse.setStartDate(startDate);
    studentCourse.setEndDate(endDate);

    studentCourseRepository.registerStudentCourse(studentCourse);
  }
}
