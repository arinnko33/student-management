package raisetech.student.management.converter;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import raisetech.student.management.data.Student;
import raisetech.student.management.data.StudentCourse;
import raisetech.student.management.domain.StudentDetail;

@Component
public class StudentConverter {

  public List<StudentDetail> convertStudentDetails(
      List<Student> students,
      List<StudentCourse> studentCourses) {

    List<StudentDetail> studentDetails = new ArrayList<>();

    for (Student student : students) {
      StudentDetail studentDetail = new StudentDetail();
      studentDetail.setStudent(student);

      List<StudentCourse> convertStudentCourses = new ArrayList<>();

      for (StudentCourse studentCourse : studentCourses) {
        if (student.getId() == studentCourse.getStudentId()) {
          convertStudentCourses.add(studentCourse);
        }
      }

      studentDetail.setStudentCourses(convertStudentCourses);
      studentDetails.add(studentDetail);
    }

    return studentDetails;
  }

  public StudentDetail convertStudentDetail(
      Student student,
      List<StudentCourse> studentCourses) {

    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setStudentCourses(studentCourses);

    return studentDetail;
  }
}