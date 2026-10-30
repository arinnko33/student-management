package raisetech.student.management.data;

import lombok.Data;

@Data
public class StudentCourseStatus {

  private int id;
  private int studentCourseId;
  private String status;
}