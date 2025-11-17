package com.facens.gamificacao;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class GamificacaoApplicationTest {

    @Test
    void contextLoads() {
        // Apenas testa se o contexto inicia corretamente
    }

    @Test
    void testMainMethod() {
        // Garante cobertura do método main para o Jacoco
        GamificacaoApplication.main(new String[]{});
    }
}
