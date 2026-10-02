package raisetech.student.management.controller.exceptionhandler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(StudentNotFoundException.class)
  public String handleStudentNotFoundException(StudentNotFoundException e) {
    return e.getMessage();
  }
}
