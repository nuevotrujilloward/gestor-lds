package com.gestorlds.controller;

import com.gestorlds.dto.CreateMeetingRequest;
import com.gestorlds.dto.MeetingDto;
import com.gestorlds.entity.MeetingType;
import com.gestorlds.repository.MeetingTypeRepository;
import com.gestorlds.service.MeetingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/meetings")
@RequiredArgsConstructor
public class MeetingController {

    private final MeetingService meetingService;
    private final MeetingTypeRepository meetingTypeRepository;

    @PostMapping
    public ResponseEntity<MeetingDto> createMeeting(@Valid @RequestBody CreateMeetingRequest request) {
        MeetingDto meeting = meetingService.createMeeting(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(meeting);
    }

    @GetMapping
    public ResponseEntity<List<MeetingDto>> getAllMeetings() {
        List<MeetingDto> meetings = meetingService.getAllMeetings();
        return ResponseEntity.ok(meetings);
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<MeetingDto>> getUpcomingMeetings() {
        List<MeetingDto> meetings = meetingService.getUpcomingMeetings();
        return ResponseEntity.ok(meetings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeetingDto> getMeetingById(@PathVariable UUID id) {
        MeetingDto meeting = meetingService.getMeetingById(id);
        return ResponseEntity.ok(meeting);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MeetingDto> updateMeeting(
            @PathVariable UUID id,
            @Valid @RequestBody CreateMeetingRequest request) {
        MeetingDto meeting = meetingService.updateMeeting(id, request);
        return ResponseEntity.ok(meeting);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeeting(@PathVariable UUID id) {
        meetingService.deleteMeeting(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/types")
    public ResponseEntity<List<MeetingType>> getMeetingTypes() {
        List<MeetingType> types = meetingTypeRepository.findAll();
        return ResponseEntity.ok(types);
    }
}