package com.facens.gamificacao;

import com.facens.gamificacao.controller.StudentController;
import com.facens.gamificacao.entity.Student;
import com.facens.gamificacao.entity.StudentEmail;
import com.facens.gamificacao.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = StudentController.class)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateAndGet() throws Exception {
        Student s = new Student(1L, "Lara", 15, 2, new StudentEmail("a@b.com"));
        Mockito.when(studentService.findAll()).thenReturn(Arrays.asList());
        mockMvc.perform(get("/students")).andExpect(status().isOk());
        Mockito.when(studentService.save(Mockito.any(Student.class))).thenReturn(s);

        String json = objectMapper.writeValueAsString(s);
        mockMvc.perform(post("/students").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/students/1"));
    }
}
