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
        return args -> {
            if (roleRepository.count() == 0) {
                log.info("Inicializando datos del barrio...");

                // OBISPADO
                createRole("Obispo", "Obispo de Barrio", "OBISPADO",
                        "[\"admin\", \"crear_reuniones\", \"ver_todas_tareas\", \"asignar_discursos\"]");
                createRole("Primer Consejero del Obispado", "Primer Consejero en el Obispado", "OBISPADO",
                        "[\"crear_reuniones\", \"ver_tareas\", \"asignar_discursos\"]");
                createRole("Segundo Consejero del Obispado", "Segundo Consejero en el Obispado", "OBISPADO",
                        "[\"crear_reuniones\", \"ver_tareas\", \"asignar_discursos\"]");
                createRole("Secretario de Barrio", "Secretario del Barrio", "OBISPADO",
                        "[\"ver_tareas\", \"reportar_asistencia\"]");
                createRole("Secretario Ejecutivo de Barrio", "Secretario Ejecutivo del Barrio", "OBISPADO",
                        "[\"ver_tareas\", \"coordinar_reuniones\"]");

                // QUÓRUM DE ÉLDERES
                createRole("Presidente del Quórum de Élderes", "Presidente del Quórum de Élderes", "QUORUM_ELDERES",
                        "[\"crear_reuniones_propias\", \"ver_mis_tareas\", \"reportar_ministerio\"]");
                createRole("Primer Consejero del Quórum de Élderes", "Primer Consejero del Quórum de Élderes", "QUORUM_ELDERES",
                        "[\"ver_mis_tareas\", \"reportar_ministerio\"]");
                createRole("Segundo Consejero del Quórum de Élderes", "Segundo Consejero del Quórum de Élderes", "QUORUM_ELDERES",
                        "[\"ver_mis_tareas\", \"reportar_ministerio\"]");
                createRole("Secretario del Quórum de Élderes", "Secretario del Quórum de Élderes", "QUORUM_ELDERES",
                        "[\"ver_mis_tareas\", \"reportar_asistencia\"]");
                createRole("Secretario Auxiliar del Quórum de Élderes", "Secretario Auxiliar del Quórum de Élderes", "QUORUM_ELDERES",
                        "[\"ver_mis_tareas\"]");
                createRole("Secretario de Ministración del Quórum de Élderes", "Secretario de Ministración del Quórum de Élderes", "QUORUM_ELDERES",
                        "[\"ver_mis_tareas\", \"reportar_ministerio\"]");

                // SOCIEDAD DE SOCORRO
                createRole("Presidenta de la Sociedad de Socorro", "Presidenta de la Sociedad de Socorro", "SOCIEDAD_SOCORRO",
                        "[\"crear_reuniones_propias\", \"ver_mis_tareas\", \"reportar_ministerio\"]");
                createRole("Primera Consejera de la Sociedad de Socorro", "Primera Consejera de la Sociedad de Socorro", "SOCIEDAD_SOCORRO",
                        "[\"ver_mis_tareas\", \"reportar_ministerio\"]");
                createRole("Segunda Consejera de la Sociedad de Socorro", "Segunda Consejera de la Sociedad de Socorro", "SOCIEDAD_SOCORRO",
                        "[\"ver_mis_tareas\", \"reportar_ministerio\"]");
                createRole("Secretaria Auxiliar de la Sociedad de Socorro", "Secretaria Auxiliar de la Sociedad de Socorro", "SOCIEDAD_SOCORRO",
                        "[\"ver_mis_tareas\"]");
                createRole("Secretaria de Ministración de la Sociedad de Socorro", "Secretaria de Ministración de la Sociedad de Socorro", "SOCIEDAD_SOCORRO",
                        "[\"ver_mis_tareas\", \"reportar_ministerio\"]");

                // SACERDOCIO AARÓNICO - DIÁCONOS
                createRole("Presidente del Quórum de Diáconos", "Presidente del Quórum de Diáconos", "SACERDOCIO_AARONICO",
                        "[\"ver_mis_tareas\"]");
                createRole("Primer Consejero del Quórum de Diáconos", "Primer Consejero del Quórum de Diáconos", "SACERDOCIO_AARONICO",
                        "[\"ver_mis_tareas\"]");
                createRole("Segundo Consejero del Quórum de Diáconos", "Segundo Consejero del Quórum de Diáconos", "SACERDOCIO_AARONICO",
                        "[\"ver_mis_tareas\"]");
                createRole("Secretario del Quórum de Diáconos", "Secretario del Quórum de Diáconos", "SACERDOCIO_AARONICO",
                        "[\"ver_mis_tareas\"]");
                createRole("Asesor de Quórum de Diáconos", "Asesor de Quórum de Diáconos", "SACERDOCIO_AARONICO",
                        "[\"ver_mis_tareas\", \"coordinar_jovenes\"]");

                // SACERDOCIO AARÓNICO - MAESTROS
                createRole("Presidente del Quórum de Maestros", "Presidente del Quórum de Maestros", "SACERDOCIO_AARONICO",
                        "[\"ver_mis_tareas\"]");
                createRole("Primer Consejero del Quórum de Maestros", "Primer Consejero del Quórum de Maestros", "SACERDOCIO_AARONICO",
                        "[\"ver_mis_tareas\"]");
                createRole("Segundo Consejero del Quórum de Maestros", "Segundo Consejero del Quórum de Maestros", "SACERDOCIO_AARONICO",
                        "[\"ver_mis_tareas\"]");
                createRole("Secretario del Quórum de Maestros", "Secretario del Quórum de Maestros", "SACERDOCIO_AARONICO",
                        "[\"ver_mis_tareas\"]");
                createRole("Asesor del Quórum de Maestros", "Asesor del Quórum de Maestros", "SACERDOCIO_AARONICO",
                        "[\"ver_mis_tareas\", \"coordinar_jovenes\"]");

                // SACERDOCIO AARÓNICO - PRESBÍTEROS
                createRole("Primer Ayudante del Quórum de Presbíteros", "Primer Ayudante del Quórum de Presbíteros", "SACERDOCIO_AARONICO",
                        "[\"ver_mis_tareas\"]");
                createRole("Segundo Consejero del Quórum de Presbíteros", "Segundo Consejero del Quórum de Presbíteros", "SACERDOCIO_AARONICO",
                        "[\"ver_mis_tareas\"]");
                createRole("Secretario del Quórum de Presbíteros", "Secretario del Quórum de Presbíteros", "SACERDOCIO_AARONICO",
                        "[\"ver_mis_tareas\"]");
                createRole("Asesor del Quórum de Presbíteros", "Asesor del Quórum de Presbíteros", "SACERDOCIO_AARONICO",
                        "[\"ver_mis_tareas\", \"coordinar_jovenes\"]");

                // MUJERES JÓVENES
                createRole("Presidenta de las Mujeres Jóvenes", "Presidenta de las Mujeres Jóvenes", "MUJERES_JOVENES",
                        "[\"crear_reuniones_propias\", \"ver_mis_tareas\"]");
                createRole("Primera Consejera de las Mujeres Jóvenes", "Primera Consejera de las Mujeres Jóvenes", "MUJERES_JOVENES",
                        "[\"ver_mis_tareas\"]");
                createRole("Segunda Consejera de las Mujeres Jóvenes", "Segunda Consejera de las Mujeres Jóvenes", "MUJERES_JOVENES",
                        "[\"ver_mis_tareas\"]");
                createRole("Secretaria de las Mujeres Jóvenes", "Secretaria de las Mujeres Jóvenes", "MUJERES_JOVENES",
                        "[\"ver_mis_tareas\"]");
                createRole("Líder de Actividades de las Mujeres Jóvenes", "Líder de Actividades de las Mujeres Jóvenes", "MUJERES_JOVENES",
                        "[\"ver_mis_tareas\"]");

                // PRIMARIA
                createRole("Presidenta de la Primaria", "Presidenta de la Primaria", "PRIMARIA",
                        "[\"crear_reuniones_propias\", \"ver_mis_tareas\"]");
                createRole("Primera Consejera de la Primaria", "Primera Consejera de la Primaria", "PRIMARIA",
                        "[\"ver_mis_tareas\"]");
                createRole("Segunda Consejera de la Primaria", "Segunda Consejera de la Primaria", "PRIMARIA",
                        "[\"ver_mis_tareas\"]");
                createRole("Secretaria de la Primaria", "Secretaria de la Primaria", "PRIMARIA",
                        "[\"ver_mis_tareas\"]");
                createRole("Líder de Música de la Primaria", "Líder de Música de la Primaria", "PRIMARIA",
                        "[\"ver_mis_tareas\"]");
                createRole("Líder de Actividades de la Primaria", "Líder de Actividades de la Primaria", "PRIMARIA",
                        "[\"ver_mis_tareas\"]");

                // ESCUELA DOMINICAL
                createRole("Presidente de la Escuela Dominical", "Presidente de la Escuela Dominical", "ESCUELA_DOMINICAL",
                        "[\"crear_reuniones_propias\", \"ver_mis_tareas\"]");
                createRole("Primer Consejero de la Escuela Dominical", "Primer Consejero de la Escuela Dominical", "ESCUELA_DOMINICAL",
                        "[\"ver_mis_tareas\"]");
                createRole("Segundo Consejero de la Escuela Dominical", "Segundo Consejero de la Escuela Dominical", "ESCUELA_DOMINICAL",
                        "[\"ver_mis_tareas\"]");
                createRole("Secretario de la Escuela Dominical", "Secretario de la Escuela Dominical", "ESCUELA_DOMINICAL",
                        "[\"ver_mis_tareas\"]");
                createRole("Maestro de la Escuela Dominical", "Maestro de la Escuela Dominical", "ESCUELA_DOMINICAL",
                        "[\"ver_mis_tareas\"]");

                // TEMPLO Y HISTORIA FAMILIAR
                createRole("Líder de Templo y Historia Familiar", "Líder de Templo y Historia Familiar", "TEMPLO_HF",
                        "[\"ver_mis_tareas\", \"coordinar_templo\"]");
                createRole("Consultor de Templo y Historia Familiar", "Consultor de Templo y Historia Familiar", "TEMPLO_HF",
                        "[\"ver_mis_tareas\"]");

                // OBRA MISIONAL
                createRole("Líder Misional de Barrio", "Líder Misional de Barrio", "OBRA_MISIONAL",
                        "[\"ver_mis_tareas\", \"coordinar_misioneros\"]");
                createRole("Misionero de Barrio", "Misionero de Barrio", "OBRA_MISIONAL",
                        "[\"ver_mis_tareas\"]");

                log.info("✓ {} roles creados correctamente", roleRepository.count());

                // Crear usuario Obispo
                Role obispoRole = roleRepository.findByNombre("Obispo")
                        .orElseThrow(() -> new RuntimeException("Rol Obispo no encontrado"));

                User admin = User.builder()
                        .email("lgarcia@gestorlds.com")
                        .nombre("Leopoldo Garcia")
                        .rol(obispoRole)
                        .passwordHash(passwordEncoder.encode("admin123"))
                        .activo(true)
                        .build();
                userRepository.save(admin);

                log.info("✓ Usuario Obispo creado: lgarcia@gestorlds.com");

                // Crear usuario rmendoza
                Role rmendozaRole = roleRepository.findByNombre("Primer Consejero del Obispado")
                        .orElseThrow(() -> new RuntimeException("Rol Primer Consejero del Obispado no encontrado"));

                User rmendoza = User.builder()
                        .email("rmendoza@gestorlds.com")
                        .nombre("Robert Mendoza")
                        .rol(rmendozaRole)
                        .passwordHash(passwordEncoder.encode("admin123"))
                        .activo(true)
                        .build();
                userRepository.save(rmendoza);

                log.info("✓ Usuario Primer Consejero del Obispado creado: rmendoza@gestorlds.com");

                // Crear tipos de reuniones
                createMeetingType("Sacramental", "Reunión Sacramental dominical", "SEMANAL",
                        "[\"Obispo\", \"Primer Consejero del Obispado\", \"Segundo Consejero del Obispado\"]");
                createMeetingType("Obispado", "Reunión de Obispado", "SEMANAL",
                        "[\"Obispo\", \"Primer Consejero del Obispado\", \"Segundo Consejero del Obispado\"]");
                createMeetingType("Consejo de Barrio", "Reunión del Consejo de Barrio", "MENSUAL",
                        "[\"Obispo\", \"Primer Consejero del Obispado\", \"Segundo Consejero del Obispado\"]");
                createMeetingType("Consejo Obispado para la Juventud", "Consejo del Obispado para la Juventud", "MENSUAL",
                        "[\"Obispo\", \"Primer Consejero del Obispado\"]");

                log.info("✓ {} tipos de reuniones creados", meetingTypeRepository.count());
                log.info("===========================================");
                log.info("SISTEMA INICIALIZADO CORRECTAMENTE");
                log.info("Login: lgarcia@gestorlds.com / admin123");
                log.info("===========================================");
                log.info("SISTEMA INICIALIZADO CORRECTAMENTE");
                log.info("Login: rmendoza@gestorlds.com / admin123");
                log.info("===========================================");
            }
        };
    }

    private void createRole(String nombre, String descripcion, String nivel, String permisos) {
        Role role = Role.builder()
                .nombre(nombre)
                .descripcion(descripcion)
                .nivel(nivel)
                .permisos(permisos)
                .build();
        roleRepository.save(role);
    }

    private void createMeetingType(String nombre, String descripcion, String frecuencia, String accesoRoles) {
        MeetingType meetingType = MeetingType.builder()
                .nombre(nombre)
                .descripcion(descripcion)
                .frecuencia(frecuencia)
                .accesoRoles(accesoRoles)
                .build();
        meetingTypeRepository.save(meetingType);
    }
}