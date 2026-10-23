package raisetech.student.management.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import raisetech.student.management.service.StudentService;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private StudentService studentService;

  @Test
  void 受講生一覧を取得できる() throws Exception {
    mockMvc.perform(get("/students"))
        .andExpect(status().isOk());
  }

  @Test
  void 受講生詳細を取得できる() throws Exception {
    mockMvc.perform(get("/students/3"))
        .andExpect(status().isOk());
  }

  @Test
  void 受講生詳細で数字以外のIDを指定するとエラーになる() throws Exception {
    mockMvc.perform(get("/students/abc"))
        .andExpect(status().isBadRequest());
  }
}