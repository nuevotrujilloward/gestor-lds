package com.gestorlds.service;

import com.gestorlds.dto.CreateMeetingRequest;
import com.gestorlds.dto.MeetingDto;
import com.gestorlds.entity.Meeting;
import com.gestorlds.entity.MeetingType;
import com.gestorlds.entity.User;
import com.gestorlds.repository.MeetingRepository;
import com.gestorlds.repository.MeetingTypeRepository;
import com.gestorlds.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MeetingService {

    private final MeetingRepository meetingRepository;
    private final MeetingTypeRepository meetingTypeRepository;
    private final UserRepository userRepository;

    @Transactional
    public MeetingDto createMeeting(CreateMeetingRequest request) {
        MeetingType meetingType = meetingTypeRepository.findById(request.getMeetingTypeId())
                .orElseThrow(() -> new RuntimeException("Tipo de reunión no encontrado"));

        User conductor = userRepository.findById(request.getConductorId())
                .orElseThrow(() -> new RuntimeException("Conductor no encontrado"));

        Meeting meeting = Meeting.builder()
                .meetingType(meetingType)
                .fechaProgramada(request.getFechaProgramada())
                .ubicacion(request.getUbicacion())
                .conductor(conductor)
                .estado("PLANIFICACION")
                .notasGenerales(request.getNotasGenerales())
                .build();

        Meeting savedMeeting = meetingRepository.save(meeting);
        log.info("Reunión creada: {} para {}", meetingType.getNombre(), request.getFechaProgramada());

        return mapToDto(savedMeeting);
    }

    @Transactional(readOnly = true)
    public List<MeetingDto> getAllMeetings() {
        return meetingRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MeetingDto> getUpcomingMeetings() {
        LocalDateTime now = LocalDateTime.now();
        return meetingRepository.findByFechaProgramadaBetween(now, now.plusMonths(1)).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MeetingDto getMeetingById(UUID id) {
        Meeting meeting = meetingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reunión no encontrada"));
        return mapToDto(meeting);
    }

    @Transactional
    public MeetingDto updateMeeting(UUID id, CreateMeetingRequest request) {
        Meeting meeting = meetingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reunión no encontrada"));

        if (request.getMeetingTypeId() != null) {
            MeetingType meetingType = meetingTypeRepository.findById(request.getMeetingTypeId())
                    .orElseThrow(() -> new RuntimeException("Tipo de reunión no encontrado"));
            meeting.setMeetingType(meetingType);
        }

        if (request.getFechaProgramada() != null) {
            meeting.setFechaProgramada(request.getFechaProgramada());
        }

        if (request.getUbicacion() != null) {
            meeting.setUbicacion(request.getUbicacion());
        }

        if (request.getConductorId() != null) {
            User conductor = userRepository.findById(request.getConductorId())
                    .orElseThrow(() -> new RuntimeException("Conductor no encontrado"));
            meeting.setConductor(conductor);
        }

        if (request.getNotasGenerales() != null) {
            meeting.setNotasGenerales(request.getNotasGenerales());
        }

        Meeting updatedMeeting = meetingRepository.save(meeting);
        return mapToDto(updatedMeeting);
    }

    @Transactional
    public void deleteMeeting(UUID id) {
        if (!meetingRepository.existsById(id)) {
            throw new RuntimeException("Reunión no encontrada");
        }
        meetingRepository.deleteById(id);
        log.info("Reunión eliminada: {}", id);
    }

    private MeetingDto mapToDto(Meeting meeting) {
        return MeetingDto.builder()
                .id(meeting.getId())
                .meetingTypeName(meeting.getMeetingType().getNombre())
                .fechaProgramada(meeting.getFechaProgramada())
                .ubicacion(meeting.getUbicacion())
                .conductorName(meeting.getConductor().getNombre())
                .estado(meeting.getEstado())
                .notasGenerales(meeting.getNotasGenerales())
                .build();
    }
}