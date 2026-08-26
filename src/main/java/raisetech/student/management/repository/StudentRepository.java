package raisetech.student.management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.student.management.data.Student;

@Mapper
public interface StudentRepository {

  // 全受講生を検索
  @Select("""
      SELECT
          id,
          nickname,
          name,
          furigana,
          age,
          gender,
          email,
          note,
          delete_flag
      FROM students
      WHERE delete_flag = 0
      """)
  List<Student> findAll();

  // IDで受講生を検索
  @Select("""
      SELECT
          id,
          nickname,
          name,
          furigana,
          age,
          gender,
          email,
          note,
          delete_flag
      FROM students
      WHERE id = #{id}
        AND delete_flag = 0
      """)
  Student findById(int id);

  // 登録
  @Insert("""
    INSERT INTO students
    (
        nickname,
        name,
        furigana,
        age,
        gender,
        email,
        note,
        delete_flag
    )
    VALUES
    (
        #{nickname},
        #{name},
        #{furigana},
        #{age},
        #{gender},
        #{email},
        #{note},
        #{deleteFlag}
    )
    """)
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void registerStudent(Student student);

  // 更新
  @Update("""
      UPDATE students
      SET
          nickname = #{nickname},
          name = #{name},
          furigana = #{furigana},
          age = #{age},
          gender = #{gender},
          email = #{email},
          note = #{note},
          delete_flag = #{deleteFlag}
      WHERE id = #{id}
      """)
  void updateStudent(Student student);

  // 論理削除
  @Update("""
      UPDATE students
      SET
          delete_flag = 1
      WHERE id = #{id}
      """)
  void deleteStudent(int id);

  // 30代の受講生を検索
  @Select("""
      SELECT *
      FROM students
      WHERE age >= 30
        AND age < 40
        AND delete_flag = 0
      """)
  List<Student> search();
}


