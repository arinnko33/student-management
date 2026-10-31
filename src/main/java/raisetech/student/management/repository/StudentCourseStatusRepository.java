package raisetech.student.management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import raisetech.student.management.data.StudentCourseStatus;

@Mapper
public interface StudentCourseStatusRepository {

  List<StudentCourseStatus> findAll();

  StudentCourseStatus findByStudentCourseId(
      @Param("studentCourseId") int studentCourseId);

  void registerStatus(StudentCourseStatus studentCourseStatus);

  void updateStatus(StudentCourseStatus studentCourseStatus);
}
