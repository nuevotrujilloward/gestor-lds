package com.gestorlds.repository;

import com.gestorlds.entity.Meeting;
import com.gestorlds.entity.MeetingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, UUID>{
    List<Meeting> findByMeetingType(MeetingType meetingType);

    List<Meeting> findByFechaProgramadaBetween(LocalDateTime inicio, LocalDateTime fin);
    List<Meeting> findByEstado(String estado);
}
