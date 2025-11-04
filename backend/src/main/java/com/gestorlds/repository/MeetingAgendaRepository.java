package com.gestorlds.repository;

import com.gestorlds.entity.Meeting;
import com.gestorlds.entity.MeetingAgenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MeetingAgendaRepository extends JpaRepository<MeetingAgenda, UUID> {
    List<MeetingAgenda> findByMeetingOrderByNumeroOrdenAsc(Meeting meeting);
}