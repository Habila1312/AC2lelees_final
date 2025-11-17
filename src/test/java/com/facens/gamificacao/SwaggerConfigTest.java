package com.facens.gamificacao.config;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SwaggerConfigTest {

    @Test
    void testSwaggerConfigLoads() {
        SwaggerConfig config = new SwaggerConfig();
        assertNotNull(config);
    }
}
