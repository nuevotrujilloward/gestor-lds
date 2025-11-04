package com.gestorlds.controller;

import com.gestorlds.dto.AgendaItemDto;
import com.gestorlds.dto.CreateAgendaItemRequest;
import com.gestorlds.service.AgendaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/agenda")
@RequiredArgsConstructor
public class AgendaController {

    private final AgendaService agendaService;

    @PostMapping("/meeting/{meetingId}")
    public ResponseEntity<AgendaItemDto> createAgendaItem(
            @PathVariable UUID meetingId,
            @Valid @RequestBody CreateAgendaItemRequest request) {
        AgendaItemDto agendaItem = agendaService.createAgendaItem(meetingId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(agendaItem);
    }

    @GetMapping("/meeting/{meetingId}")
    public ResponseEntity<List<AgendaItemDto>> getAgendaByMeeting(@PathVariable UUID meetingId) {
        List<AgendaItemDto> agenda = agendaService.getAgendaByMeeting(meetingId);
        return ResponseEntity.ok(agenda);
    }

    @DeleteMapping("/{agendaId}")
    public ResponseEntity<Void> deleteAgendaItem(@PathVariable UUID agendaId) {
        agendaService.deleteAgendaItem(agendaId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{agendaId}/confirm")
    public ResponseEntity<AgendaItemDto> confirmAgendaItem(@PathVariable UUID agendaId) {
        AgendaItemDto agendaItem = agendaService.confirmAgendaItem(agendaId);
        return ResponseEntity.ok(agendaItem);
    }
}