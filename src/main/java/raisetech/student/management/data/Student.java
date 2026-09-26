package raisetech.student.management.data;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Student {
  private int id;
  private String nickname;

  @NotBlank
  private String name;
  private String furigana;
  private int age;
  private String gender;
  private String email;
  private String note;
  private boolean deleteFlag;
}