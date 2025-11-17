package com.facens.gamificacao;

import com.facens.gamificacao.dto.StudentDTO;
import com.facens.gamificacao.entity.Student;
import com.facens.gamificacao.entity.StudentEmail;
import com.facens.gamificacao.repository.StudentRepository;
import com.facens.gamificacao.service.StudentService;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    public StudentServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    // ------------------------------------------------------
    // TESTES ORIGINAIS
    // ------------------------------------------------------

    @Test
    public void testFindAll() {

        Student s1 = new Student(1L, "Lara", 10, 1, new StudentEmail("a@b.com"));
        Student s2 = new Student(2L, "Joao", 5, 3, new StudentEmail("c@d.com"));

        when(studentRepository.findAll()).thenReturn(Arrays.asList(s1, s2));

        List<StudentDTO> result = studentService.findAll();

        assertEquals(2, result.size());
        assertEquals("Lara", result.get(0).getName());
    }

    @Test
    public void testFindById() {

        Student s1 = new Student(1L, "Lara", 10, 1, new StudentEmail("a@b.com"));

        when(studentRepository.findById(1L)).thenReturn(Optional.of(s1));

        Optional<Student> result = studentService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("Lara", result.get().getName());
    }

    @Test
    public void testSave() {

        Student s = new Student(null, "Lara", 10, 1, new StudentEmail("a@b.com"));

        when(studentRepository.save(s)).thenReturn(s);

        Student saved = studentService.save(s);

        assertEquals("Lara", saved.getName());
    }

    @Test
    public void testProcessRewards() {

        Student s1 = new Student(1L, "Lara", 20, 1, new StudentEmail("a@b.com"));
        Student s2 = new Student(2L, "Joao", 5, 1, new StudentEmail("c@d.com"));

        when(studentRepository.findAll()).thenReturn(Arrays.asList(s1, s2));

        studentService.processRewards();

        assertEquals(2, s1.getAvailableCourses());
        verify(studentRepository, times(1)).save(s1);
    }

    // ------------------------------------------------------
    // TESTES ADICIONADOS PARA BATER 99%
    // ------------------------------------------------------

    @Test
    public void testFindDtoById() {

        Student s = new Student(1L, "Lara", 10, 1, new StudentEmail("a@b.com"));
        when(studentRepository.findById(1L)).thenReturn(Optional.of(s));

        StudentDTO dto = studentService.findDtoById(1L);

        assertEquals("Lara", dto.getName());
        assertEquals(10, dto.getCommentsCount());
        assertEquals("a@b.com", dto.getEmail());
    }

    @Test
    public void testFindDtoByIdNotFound() {

        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> studentService.findDtoById(1L));
    }

    @Test
    public void testProcessRewardsEmptyList() {

        when(studentRepository.findAll()).thenReturn(Arrays.asList());

        studentService.processRewards();

        verify(studentRepository, never()).save(any());
    }

    @Test
    public void testProcessRewardsNullList() {

        when(studentRepository.findAll()).thenReturn(null);

        studentService.processRewards();

        verify(studentRepository, never()).save(any());
    }

    @Test
    public void testProcessRewardsTie() {

        Student s1 = new Student(1L, "A", 10, 1, new StudentEmail("a@a.com"));
        Student s2 = new Student(2L, "B", 10, 1, new StudentEmail("b@b.com"));

        when(studentRepository.findAll()).thenReturn(Arrays.asList(s1, s2));

        studentService.processRewards();

        // Em caso de EMPATE, pega o primeiro da lista
        assertEquals(2, s1.getAvailableCourses());
        assertEquals(1, s2.getAvailableCourses());

        verify(studentRepository).save(s1);
        verify(studentRepository, never()).save(s2);
    }
}
