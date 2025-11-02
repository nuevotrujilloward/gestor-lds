package com.gestorlds.repository;

import com.gestorlds.entity.MeetingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MeetingTypeRepository extends JpaRepository<MeetingType, UUID> {
    Optional<MeetingType> findByNombre(String nombre);
}

