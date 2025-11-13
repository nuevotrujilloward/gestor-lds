package com.gestorlds.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile("prod")
public class DatabaseConfig {

    @Bean
    @Primary
    public DataSource dataSource() {
        String databaseUrl = System.getenv("DATABASE_URL");

        if (databaseUrl == null) {
            throw new IllegalStateException("DATABASE_URL no está configurada");
        }

        // Railway proporciona postgresql:// pero JDBC necesita jdbc:postgresql://
        if (databaseUrl.startsWith("postgresql://")) {
            databaseUrl = databaseUrl.replace("postgresql://", "jdbc:postgresql://");
        }

        System.out.println("🔌 Conectando a PostgreSQL: " + databaseUrl.replaceAll(":[^:@]+@", ":****@"));

        return DataSourceBuilder
                .create()
                .url(databaseUrl)
                .driverClassName("org.postgresql.Driver")
                .build();
    }
}