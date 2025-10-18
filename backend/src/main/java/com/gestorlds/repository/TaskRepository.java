package com.gestorlds.repository;

import com.gestorlds.entity.Task;
import com.gestorlds.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findByUser(User user);
    List<Task> findByUserAndEstado(User user, String estado);
    List<Task> findByFechaVencimientoBetween(LocalDateTime inicio, LocalDateTime fin);
}
