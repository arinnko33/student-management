package raisetech.student.management.controller;

import java.time.LocalDate;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
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
import raisetech.student.management.controller.exceptionhandler.StudentNotFoundException;
import io.swagger.v3.oas.annotations.Operation;

@Controller
@RequiredArgsConstructor
public class StudentController {

  private final StudentService studentService;

  // 受講生一覧画面（Thymeleaf）
  @Operation(summary = "受講生一覧画面を表示する")
  @GetMapping("/studentList")
  public String studentList(Model model) {
    model.addAttribute("students", studentService.findAllStudents());
    return "studentList";
  }

  // 新規受講生登録画面
  @Operation(summary = "新規受講生登録画面を表示する")
  @GetMapping("/students/new")
  public String showCreateStudentForm(Model model) {
    model.addAttribute("student", new Student());
    return "registerStudent";
  }

  // 全受講生を検索（JSON）
  @Operation(summary = "全受講生を取得する")
  @ResponseBody
  @GetMapping("/students")
  public List<Student> findAll() {
    return studentService.findAllStudents();
  }

  // 30代の受講生を検索（JSON）
  @Operation(summary = "30代の受講生を取得する")
  @ResponseBody
  @GetMapping("/students/30s")
  public List<Student> findStudentsInTheir30s() {
    return studentService.findStudentsInTheir30s();
  }

  // 受講生詳細を検索（JSON）
  @Operation(summary = "指定したIDの受講生詳細を取得する")
  @ResponseBody
  @GetMapping("/students/{id}")
  public StudentDetail getStudentDetail(@PathVariable int id) {
    return studentService.getStudentDetail(id);
  }

  // 新規受講生登録（Thymeleaf）
  @Operation(summary = "受講生を登録する")
  @PostMapping("/students")
  public String registerStudent(
      @Valid @ModelAttribute Student student,
      BindingResult bindingResult,
      @RequestParam String courseName,
      @RequestParam LocalDate startDate,
      @RequestParam LocalDate endDate) {

    if (bindingResult.hasErrors()) {
      return "registerStudent";
    }

    studentService.registerStudent(
        student,
        courseName,
        startDate,
        endDate
    );

    return "redirect:/studentList";
  }

  // 更新
  @Operation(summary = "受講生を更新する")
  @ResponseBody
  @PutMapping("/students")
  public void updateStudent(@RequestBody Student student) {
    studentService.updateStudent(student);
  }

  // 削除
  @Operation(summary = "受講生を削除する")
  @ResponseBody
  @DeleteMapping("/students/{id}")
  public void deleteStudent(@PathVariable int id) {
    studentService.deleteStudent(id);
  }

  // コース検索
  @Operation(summary = "コース一覧を取得する")
  @ResponseBody
  @GetMapping("/courses")
  public List<StudentCourse> getCourses() {
    return studentService.getCourses();
  }

  // Javaコースを検索
  @Operation(summary = "Javaコースの受講生を取得する")
  @ResponseBody
  @GetMapping("/courses/java")
  public List<StudentCourse> searchJavaCourse() {
    return studentService.searchJavaCourse();
  }

  // JSONから新規受講生を登録
  @Operation(summary = "JSONから受講生を登録する")
  @ResponseBody
  @PostMapping("/students/json")
  public Student registerStudentByJson(@RequestBody Student student) {
    studentService.registerStudent(student);
    return student;
  }

  // 例外発生テスト
  @Operation(summary = "例外処理をテストする")
  @ResponseBody
  @GetMapping("/students/error")
  public String error() {
    throw new StudentNotFoundException("受講生が見つかりません");
  }
}