package com.facens.gamificacao;

import com.facens.gamificacao.controller.StudentController;
import com.facens.gamificacao.dto.StudentDTO;
import com.facens.gamificacao.entity.Student;
import com.facens.gamificacao.service.StudentService;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAll() throws Exception {
        Mockito.when(studentService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/students"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetById() throws Exception {
        Student s = new Student(1L, "Lara", 15, 2);
        StudentDTO dto = new StudentDTO(s);

        Mockito.when(studentService.findById(1L)).thenReturn(dto);

        mockMvc.perform(get("/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Lara"))
                .andExpect(jsonPath("$.commentsCount").value(15));
    }

    @Test
    void testCreate() throws Exception {
        Student s = new Student(1L, "Lara", 15, 2);
        Mockito.when(studentService.save(Mockito.any())).thenReturn(s);

        String json = objectMapper.writeValueAsString(s);

        mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/students/1"));
    }

    @Test
    void testRewards() throws Exception {
        mockMvc.perform(post("/students/rewards"))
                .andExpect(status().isOk());

        Mockito.verify(studentService, Mockito.times(1)).processRewards();
    }
}
