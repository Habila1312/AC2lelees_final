package com.facens.gamificacao.controller;

import com.facens.gamificacao.dto.StudentDTO;
import com.facens.gamificacao.entity.Student;
import com.facens.gamificacao.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public List<StudentDTO> getAll() {
        return studentService.findAll();
    }

    @PostMapping
    public ResponseEntity<StudentDTO> create(@RequestBody Student student) {
        Student saved = studentService.save(student);
        return ResponseEntity.created(URI.create("/students/" + saved.getId())).body(new StudentDTO(saved));
    }

    @PostMapping("/rewards")
    public ResponseEntity<Void> processRewards() {
        studentService.processRewards();
        return ResponseEntity.ok().build();
    }
}
