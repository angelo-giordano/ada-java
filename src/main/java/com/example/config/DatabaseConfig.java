package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configuração do Banco de Dados
 * Spring Boot auto-configura H2 baseado no application.properties
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.example.infrastructure.persistence.repository")
@EnableTransactionManagement
public class DatabaseConfig {
    // Spring Boot configura automaticamente o H2
    // Não precisa criar DataSource manualmente
}