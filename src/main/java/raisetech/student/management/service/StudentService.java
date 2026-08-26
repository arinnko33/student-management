package raisetech.student.management.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.repository.StudentCourseRepository;
import raisetech.student.management.repository.StudentRepository;

@Service
@RequiredArgsConstructor
public class StudentService {

  private final StudentRepository repository;
  private final StudentCourseRepository studentCourseRepository;

  public List<Student> findAllStudents() {
    return repository.findAll();
  }

  public List<Student> searchStudentList() {
    return repository.search();
  }

  public List<StudentCourse> findAllStudentCourses() {
    return studentCourseRepository.findAll();
  }
}
