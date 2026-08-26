package raisetech.student.management.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.service.StudentService;

@RestController
@RequiredArgsConstructor
public class StudentController {

  private final StudentService studentService;

  @GetMapping("/students")
  public List<Student> findAll() {
    return studentService.findAllStudents();
  }

  @GetMapping("/students/30s")
  public List<Student> searchStudentList() {
    return studentService.searchStudentList();
  }

  @GetMapping("/studentCourses")
  public List<StudentCourse> findAllStudentCourses() {
    return studentService.findAllStudentCourses();
  }
}