package com.gestorlds.service;

import com.gestorlds.dto.AgendaItemDto;
import com.gestorlds.dto.CreateAgendaItemRequest;
import com.gestorlds.entity.Meeting;
import com.gestorlds.entity.MeetingAgenda;
import com.gestorlds.entity.User;
import com.gestorlds.repository.MeetingAgendaRepository;
import com.gestorlds.repository.MeetingRepository;
import com.gestorlds.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AgendaService {

    private final MeetingAgendaRepository agendaRepository;
    private final MeetingRepository meetingRepository;
    private final UserRepository userRepository;

    @Transactional
    public AgendaItemDto createAgendaItem(UUID meetingId, CreateAgendaItemRequest request) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new RuntimeException("Reunión no encontrada"));

        MeetingAgenda.MeetingAgendaBuilder builder = MeetingAgenda.builder()
                .meeting(meeting)
                .numeroOrden(request.getNumeroOrden())
                .tipo(request.getTipo())
                .titulo(request.getTitulo())
                .descripcion(request.getDescripcion())
                .tiempoAsignado(request.getTiempoAsignado())
                .notas(request.getNotas())
                .confirmado(false)
                .presentado(false);

        // Asignar a usuario si se proporciona
        if (request.getAsignadoAId() != null) {
            User asignado = userRepository.findById(request.getAsignadoAId())
                    .orElseThrow(() -> new RuntimeException("Usuario asignado no encontrado"));
            builder.asignadoA(asignado);
        }

        // O asignar por email si no está en el sistema
        if (request.getEmailAsignado() != null && !request.getEmailAsignado().isEmpty()) {
            builder.emailAsignado(request.getEmailAsignado());
        }

        MeetingAgenda agenda = builder.build();
        MeetingAgenda saved = agendaRepository.save(agenda);

        log.info("Item de agenda creado: {} para reunión {}", request.getTitulo(), meetingId);
        return mapToDto(saved);
    }

    @Transactional(readOnly = true)
    public List<AgendaItemDto> getAgendaByMeeting(UUID meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new RuntimeException("Reunión no encontrada"));

        return agendaRepository.findByMeetingOrderByNumeroOrdenAsc(meeting).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteAgendaItem(UUID agendaId) {
        if (!agendaRepository.existsById(agendaId)) {
            throw new RuntimeException("Item de agenda no encontrado");
        }
        agendaRepository.deleteById(agendaId);
        log.info("Item de agenda eliminado: {}", agendaId);
    }

    @Transactional
    public AgendaItemDto confirmAgendaItem(UUID agendaId) {
        MeetingAgenda agenda = agendaRepository.findById(agendaId)
                .orElseThrow(() -> new RuntimeException("Item de agenda no encontrado"));

        agenda.setConfirmado(true);
        MeetingAgenda updated = agendaRepository.save(agenda);

        log.info("Item de agenda confirmado: {}", agendaId);
        return mapToDto(updated);
    }

    private AgendaItemDto mapToDto(MeetingAgenda agenda) {
        String asignadoNombre = null;
        if (agenda.getAsignadoA() != null) {
            asignadoNombre = agenda.getAsignadoA().getNombre();
        }

        return AgendaItemDto.builder()
                .id(agenda.getId())
                .meetingId(agenda.getMeeting().getId())
                .numeroOrden(agenda.getNumeroOrden())
                .tipo(agenda.getTipo())
                .titulo(agenda.getTitulo())
                .descripcion(agenda.getDescripcion())
                .asignadoNombre(asignadoNombre)
                .emailAsignado(agenda.getEmailAsignado())
                .tiempoAsignado(agenda.getTiempoAsignado())
                .confirmado(agenda.getConfirmado())
                .presentado(agenda.getPresentado())
                .build();
    }
}