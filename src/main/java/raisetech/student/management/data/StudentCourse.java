package raisetech.student.management.data;

import java.time.LocalDate;
import lombok.Data;

@Data
public class StudentCourse {

  private int id;
  private int studentId;
  private String courseName;
  private LocalDate startDate;
  private LocalDate endDate;
}
