package com.facens.gamificacao;

import com.facens.gamificacao.entity.Student;
import com.facens.gamificacao.entity.StudentEmail;
import com.facens.gamificacao.repository.StudentRepository;
import com.facens.gamificacao.service.StudentService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessRewards() {

        Student s1 = new Student(1L, "Lara", 15, 2, new StudentEmail("l@e.com"));
        Student s2 = new Student(2L, "Joao", 5, 1, new StudentEmail("j@e.com"));
        List<Student> list = Arrays.asList(s1, s2);

        Mockito.when(studentRepository.findAll()).thenReturn(list);

        studentService.processRewards();

        // Apenas aluno com pontos > 10 ganha 1 curso
        assertEquals(3, s1.getAvailableCourses());
        assertEquals(1, s2.getAvailableCourses());

        // Verifica save somente para o aluno que ganhou bônus
        Mockito.verify(studentRepository, Mockito.times(1)).save(s1);
    }

    @Test
    void testProcessRewardsEmptyList() {
        Mockito.when(studentRepository.findAll()).thenReturn(Arrays.asList());

        assertDoesNotThrow(() -> studentService.processRewards());
    }
}
