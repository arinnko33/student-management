package raisetech.student.management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.student.management.data.StudentCourse;

@Mapper
public interface StudentCourseRepository {

  // Javaコースを検索
  @Select("""
    SELECT
        id,
        student_id,
        course_name,
        start_date,
        end_date
    FROM students_courses
    WHERE course_name = 'Javaコース'
    """)
  List<StudentCourse> searchJavaCourse();

  // 全コースを検索
  @Select("""
    SELECT
        id,
        student_id,
        course_name,
        start_date,
        end_date
    FROM students_courses
    """)
  List<StudentCourse> findAll();

  // 受講生IDからコースを検索
  @Select("""
    SELECT
        id,
        student_id,
        course_name,
        start_date,
        end_date
    FROM students_courses
    WHERE student_id = #{studentId}
    """)
  List<StudentCourse> findByStudentId(int studentId);

  // 登録
  @Insert("""
      INSERT INTO students_courses
      (
          student_id,
          course_name,
          start_date,
          end_date
      )
      VALUES
      (
          #{studentId},
          #{courseName},
          #{startDate},
          #{endDate}
      )
      """)
  void registerStudentCourse(StudentCourse studentCourse);

  // 更新
  @Update("""
      UPDATE students_courses
      SET
          student_id = #{studentId},
          course_name = #{courseName},
          start_date = #{startDate},
          end_date = #{endDate}
      WHERE id = #{id}
      """)
  void updateStudentCourse(StudentCourse studentCourse);

  // 削除
  @Delete("""
      DELETE FROM students_courses
      WHERE id = #{id}
      """)
  void deleteStudentCourse(int id);
}
