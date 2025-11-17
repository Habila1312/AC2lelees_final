package com.facens.gamificacao;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GamificacaoApplicationTest {

    @Test
    void contextLoads() {
        // Apenas testa se o contexto sobe
        GamificacaoApplication.main(new String[]{});
    }

    @Test
    void testMainMethod() {
        // Testa o método main diretamente (para cobertura do Jacoco)
        GamificacaoApplication.main(new String[]{});
    }
}
