package raisetech.student.management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import raisetech.student.management.data.StudentCourse;

@Mapper
public interface StudentCourseRepository {

  @Select("""
      SELECT
        id,
        student_id AS studentId,
        course_name AS courseName,
        start_date AS startDate,
        end_date AS endDate
      FROM students_courses
      """)
  List<StudentCourse> findAll();
}
