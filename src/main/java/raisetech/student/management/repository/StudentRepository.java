package raisetech.student.management.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import raisetech.student.management.data.Student;

@Mapper
public interface StudentRepository {

  // 全受講生を検索
  List<Student> findAll();

  // IDで受講生を検索
  Student findById(int id);

  // 登録
  void registerStudent(Student student);

  // 更新
  void updateStudent(Student student);

  // 論理削除
  void deleteStudent(int id);

  // 30代の受講生を検索
  List<Student> findStudentsInTheir30s();
}


