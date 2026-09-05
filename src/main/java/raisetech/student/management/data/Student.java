package raisetech.student.management.data;

import lombok.Data;

@Data
public class Student {
  private int id;
  private String nickname;
  private String name;
  private String furigana;
  private int age;
  private String gender;
  private String email;
  private String region;
  private String note;
  private boolean deleteFlag;
}