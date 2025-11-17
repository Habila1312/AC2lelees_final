package com.facens.gamificacao;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GamificacaoApplicationTest {

    @Test
    void contextLoads() {
        // Testa se o contexto inicializa corretamente
        GamificacaoApplication.main(new String[]{}); 
    }

    @Test
    void testMainMethod() {
        // Testa o método main diretamente (necessário para Jacoco)
        GamificacaoApplication.main(new String[]{});
    }
    @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GamificacaoApplicationTest {
    @Test
    void contextLoads() {}
}

}
