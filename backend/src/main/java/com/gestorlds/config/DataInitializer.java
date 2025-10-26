package com.gestorlds.config;

import com.gestorlds.entity.Role;
import com.gestorlds.entity.User;
import com.gestorlds.repository.RoleRepository;
import com.gestorlds.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            if (roleRepository.count() == 0) {
                log.info("Inicializando datos...");

                // Crear rol Obispo
                Role obispoRole = Role.builder()
                        .nombre("Obispo")
                        .descripcion("Presidente del Barrio")
                        .nivel("OBISPADO")
                        .permisos("[\"admin\", \"crear_reuniones\", \"ver_todas_tareas\"]")
                        .build();
                roleRepository.save(obispoRole);

                // Crear usuario admin
                User admin = User.builder()
                        .email("obispo@gestorlds.com")
                        .nombre("Obispo Admin")
                        .rol(obispoRole)
                        .passwordHash(passwordEncoder.encode("admin123"))
                        .activo(true)
                        .build();
                userRepository.save(admin);

                log.info("Datos inicializados correctamente");
            }
        };
    }
}