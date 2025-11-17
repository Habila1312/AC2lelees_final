package com.facens.gamificacao;

import com.facens.gamificacao.entity.StudentEmail;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StudentEmailTest {

    @Test
    public void testEmailFields() {
        StudentEmail email = new StudentEmail("a@b.com");

        assertEquals("a@b.com", email.getAddress());
        assertNotNull(email.toString());
    }

    @Test
    public void testEqualsAndHash() {
        StudentEmail e1 = new StudentEmail("a@b.com");
        StudentEmail e2 = new StudentEmail("a@b.com");

        assertEquals(e1, e2);
        assertEquals(e1.hashCode(), e2.hashCode());
    }
}
