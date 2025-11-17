package com.facens.gamificacao;

import com.facens.gamificacao.entity.Student;
import com.facens.gamificacao.entity.StudentEmail;
import com.facens.gamificacao.repository.StudentRepository;
import com.facens.gamificacao.service.StudentService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    public StudentServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    // -----------------------------------------------------------
    // TESTE DO MÉTODO processRewards()
    // -----------------------------------------------------------
    @Test
    public void testProcessRewards() {
        Student s1 = new Student(1L, "Lara", 15, 2, new StudentEmail("l@e.com"));
        Student s2 = new Student(2L, "Joao", 5, 1, new StudentEmail("j@e.com"));

        List<Student> list = Arrays.asList(s1, s2);
        Mockito.when(studentRepository.findAll()).thenReturn(list);

        studentService.processRewards();

        Mockito.verify(studentRepository, Mockito.times(1)).save(s1);
        assertEquals(3, s1.getAvailableCourses());
        assertEquals(1, s2.getAvailableCourses());
    }

    // -----------------------------------------------------------
    // TESTE DO MÉTODO findAll()
    // -----------------------------------------------------------
    @Test
    public void testFindAll() {
        List<Student> list = Arrays.asList(
                new Student(1L, "Ana", 10, 1, new StudentEmail("a@a.com")),
                new Student(2L, "Pedro", 5, 0, new StudentEmail("p@p.com"))
        );

        Mockito.when(studentRepository.findAll()).thenReturn(list);

        List<Student> result = studentService.findAll();
        assertEquals(2, result.size());
        Mockito.verify(studentRepository).findAll();
    }

    // -----------------------------------------------------------
    // TESTE DO MÉTODO findById()
    // -----------------------------------------------------------
    @Test
    public void testFindById() {
        Student s = new Student(1L, "Ana", 10, 1, new StudentEmail("a@a.com"));

        Mockito.when(studentRepository.findById(1L)).thenReturn(Optional.of(s));

        Student result = studentService.findById(1L);
        assertNotNull(result);
        assertEquals("Ana", result.getName());
    }

    // -----------------------------------------------------------
    // TESTE DO MÉTODO save()
    // -----------------------------------------------------------
    @Test
    public void testSave() {
        Student s = new Student(1L, "Ana", 10, 1, new StudentEmail("a@a.com"));

        Mockito.when(studentRepository.save(s)).thenReturn(s);

        Student result = studentService.save(s);

        assertEquals("Ana", result.getName());
        Mockito.verify(studentRepository).save(s);
    }

    // -----------------------------------------------------------
    // TESTE DO MÉTODO update()
    // -----------------------------------------------------------
    @Test
    public void testUpdate() {
        Student s = new Student(1L, "Ana", 10, 1, new StudentEmail("a@a.com"));

        Mockito.when(studentRepository.existsById(1L)).thenReturn(true);
        Mockito.when(studentRepository.save(s)).thenReturn(s);

        Student result = studentService.update(1L, s);

        assertEquals("Ana", result.getName());
        Mockito.verify(studentRepository).save(s);
    }

    // -----------------------------------------------------------
    // TESTE DO MÉTODO delete()
    // -----------------------------------------------------------
    @Test
    public void testDelete() {
        Mockito.when(studentRepository.existsById(1L)).thenReturn(true);

        studentService.delete(1L);

        Mockito.verify(studentRepository).deleteById(1L);
    }
}
