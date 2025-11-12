package com.gestorlds.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile("prod")
public class DatabaseConfig {

    @Bean
    public DataSource dataSource() {
        String databaseUrl = System.getenv("DATABASE_URL");

        // Railway proporciona postgresql:// pero Spring Boot necesita jdbc:postgresql://
        if (databaseUrl != null && databaseUrl.startsWith("postgresql://")) {
            databaseUrl = databaseUrl.replace("postgresql://", "jdbc:postgresql://");
        }

        return DataSourceBuilder
                .create()
                .url(databaseUrl)
                .driverClassName("org.postgresql.Driver")
                .build();
    }
}