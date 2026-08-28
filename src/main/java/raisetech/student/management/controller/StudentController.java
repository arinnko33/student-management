package raisetech.student.management.controller;

import java.time.LocalDate;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.StudentDetail;
import raisetech.student.management.service.StudentService;

@Controller
@RequiredArgsConstructor
public class StudentController {

  private final StudentService studentService;

  // 受講生一覧画面（Thymeleaf）
  @GetMapping("/studentList")
  public String studentList(Model model) {
    model.addAttribute("students", studentService.findAllStudents());
    return "studentList";
  }

  // 新規受講生登録画面
  @GetMapping("/students/new")
  public String showCreateStudentForm(Model model) {
    model.addAttribute("student", new Student());
    return "registerStudent";
  }

  // 全受講生を検索（JSON）
  @ResponseBody
  @GetMapping("/students")
  public List<Student> findAll() {
    return studentService.findAllStudents();
  }

  // 30代の受講生を検索（JSON）
  @ResponseBody
  @GetMapping("/students/30s")
  public List<Student> searchStudentList() {
    return studentService.searchStudentList();
  }

  // 受講生詳細を検索（JSON）
  @ResponseBody
  @GetMapping("/students/{id}")
  public StudentDetail getStudentDetail(@PathVariable int id) {
    return studentService.getStudentDetail(id);
  }

  // 新規受講生登録（Thymeleaf）
  @PostMapping("/students")
  public String registerStudent(
      @ModelAttribute Student student,
      @RequestParam String courseName,
      @RequestParam LocalDate startDate,
      @RequestParam LocalDate endDate) {

    studentService.registerStudent(
        student,
        courseName,
        startDate,
        endDate
    );

    return "redirect:/studentList";
  }

  // 更新
  @ResponseBody
  @PutMapping("/students")
  public void updateStudent(@RequestBody Student student) {
    studentService.updateStudent(student);
  }

  // 削除
  @ResponseBody
  @DeleteMapping("/students/{id}")
  public void deleteStudent(@PathVariable int id) {
    studentService.deleteStudent(id);
  }

  // コース検索
  @ResponseBody
  @GetMapping("/courses")
  public List<StudentCourse> getCourses() {
    return studentService.getCourses();
  }

  // Javaコースを検索
  @ResponseBody
  @GetMapping("/courses/java")
  public List<StudentCourse> searchJavaCourse() {
    return studentService.searchJavaCourse();
  }
}