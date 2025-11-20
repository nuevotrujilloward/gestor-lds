package com.gestorlds.config;

import com.gestorlds.entity.MeetingType;
import com.gestorlds.entity.Role;
import com.gestorlds.entity.User;
import com.gestorlds.repository.MeetingTypeRepository;
import com.gestorlds.repository.RoleRepository;
import com.gestorlds.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final MeetingTypeRepository meetingTypeRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initDatabase() {
        return args -> initialize();
    }

    @Transactional
    public void initialize() {
        log.info("DataInitializer: iniciando verificación/creación de datos base...");

        // Roles (idempotente)
        createOrGetRole("Obispo", "Obispo de Barrio", "OBISPADO",
                "[\"admin\", \"crear_reuniones\", \"ver_todas_tareas\", \"asignar_discursos\"]");
        createOrGetRole("Primer Consejero del Obispado", "Primer Consejero en el Obispado", "OBISPADO",
                "[\"crear_reuniones\", \"ver_tareas\", \"asignar_discursos\"]");
        createOrGetRole("Segundo Consejero del Obispado", "Segundo Consejero en el Obispado", "OBISPADO",
                "[\"crear_reuniones\", \"ver_tareas\", \"asignar_discursos\"]");
        createOrGetRole("Secretario de Barrio", "Secretario del Barrio", "OBISPADO",
                "[\"ver_tareas\", \"reportar_asistencia\"]");
        createOrGetRole("Secretario Ejecutivo de Barrio", "Secretario Ejecutivo del Barrio", "OBISPADO",
                "[\"ver_tareas\", \"coordinar_reuniones\"]");

        // ... agrega aquí las demás llamadas a createOrGetRole() exactamente como antes ...
        // (todo el conjunto de roles que ya tienes)

        log.info("Roles existentes: {}", roleRepository.count());

        // Usuarios iniciales (si no existen)
        createUserIfNotExists("lgarcia@gestorlds.com", "Leopoldo Garcia", "Obispo", "admin123");
        createUserIfNotExists("rmendoza@gestorlds.com", "Robert Mendoza", "Primer Consejero del Obispado", "admin123");

        log.info("Usuarios existentes: {}", userRepository.count());

        // Meeting types (idempotente)
        createOrGetMeetingType("Sacramental", "Reunión Sacramental dominical", "SEMANAL",
                "[\"Obispo\", \"Primer Consejero del Obispado\", \"Segundo Consejero del Obispado\"]");
        createOrGetMeetingType("Obispado", "Reunión de Obispado", "SEMANAL",
                "[\"Obispo\", \"Primer Consejero del Obispado\", \"Segundo Consejero del Obispado\"]");
        createOrGetMeetingType("Consejo de Barrio", "Reunión del Consejo de Barrio", "MENSUAL",
                "[\"Obispo\", \"Primer Consejero del Obispado\", \"Segundo Consejero del Obispado\"]");
        createOrGetMeetingType("Consejo Obispado para la Juventud", "Consejo del Obispado para la Juventud", "MENSUAL",
                "[\"Obispo\", \"Primer Consejero del Obispado\"]");

        log.info("Tipos de reuniones existentes: {}", meetingTypeRepository.count());

        log.info("DataInitializer: finalizado correctamente.");
    }

    private Role createOrGetRole(String nombre, String descripcion, String nivel, String permisos) {
        Optional<Role> existing = roleRepository.findByNombre(nombre);
        if (existing.isPresent()) {
            return existing.get();
        }
        Role role = Role.builder()
                .nombre(nombre)
                .descripcion(descripcion)
                .nivel(nivel)
                .permisos(permisos)
                .build();
        Role saved = roleRepository.save(role);
        log.debug("Role creado: {}", nombre);
        return saved;
    }

    private void createUserIfNotExists(String email, String nombre, String roleNombre, String plainPassword) {
        if (userRepository.findByEmail(email).isPresent()) {
            log.debug("Usuario ya existe: {}", email);
            return;
        }

        Role role = roleRepository.findByNombre(roleNombre)
                .orElseThrow(() -> new IllegalStateException("Rol no encontrado: " + roleNombre));

        User user = User.builder()
                .email(email)
                .nombre(nombre)
                .rol(role)
                .passwordHash(passwordEncoder.encode(plainPassword))
                .activo(true)
                .build();
        userRepository.save(user);
        log.info("Usuario creado: {}", email);
    }

    private MeetingType createOrGetMeetingType(String nombre, String descripcion, String frecuencia, String accesoRoles) {
        Optional<MeetingType> existing = meetingTypeRepository.findByNombre(nombre);
        if (existing.isPresent()) {
            return existing.get();
        }
        MeetingType meetingType = MeetingType.builder()
                .nombre(nombre)
                .descripcion(descripcion)
                .frecuencia(frecuencia)
                .accesoRoles(accesoRoles)
                .build();
        MeetingType saved = meetingTypeRepository.save(meetingType);
        log.debug("MeetingType creado: {}", nombre);
        return saved;
    }
}
