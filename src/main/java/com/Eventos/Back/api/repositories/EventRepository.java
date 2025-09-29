package com.Eventos.Back.api.repositories;

import com.Eventos.Back.api.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {
}
