package com.gestorlds.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="meetings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="meeting_type_id", nullable = false)
    private MeetingType meetingType;

    @Column(nullable = false)
    private LocalDate fechaProgramada;

    @Column(length = 255)
    private String ubicacion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="conductor_id", nullable = false)
    private User conductor;

    @Column(nullable = false)
    private String estado = "PLANIFICACION";

    @Column(columnDefinition = "TEXT")
    private String notasGenerales;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
