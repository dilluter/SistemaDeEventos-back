package com.Eventos.Back.api.controller;

import com.Eventos.Back.api.domain.event.Event;
import com.Eventos.Back.api.domain.event.EventResponseDTO;
import com.Eventos.Back.api.service.EventService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = EventController.class)
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @Test
    void create_shouldReturnEventCreated() throws Exception {
        MockMultipartFile image = new MockMultipartFile(
                "image",
                "foto.png",
                MediaType.IMAGE_PNG_VALUE,
                "fake-image".getBytes(StandardCharsets.UTF_8)
        );

        long nowMillis = System.currentTimeMillis();

        Event eventMock = new Event();
        eventMock.setId(UUID.randomUUID());
        eventMock.setTitle("Evento IT");
        eventMock.setDescription("Descricao IT");
        eventMock.setEventUrl("https://event-it");
        eventMock.setDate(new Date(nowMillis));
        eventMock.setRemote(false);

        when(eventService.createEvent(any())).thenReturn(eventMock);

        mockMvc.perform(multipart("/api/event")
                        .file(image)
                        .param("title", "Evento IT")
                        .param("description", "Descricao IT")
                        .param("date", String.valueOf(nowMillis))
                        .param("city", "Brasilia")
                        .param("state", "DF")
                        .param("remote", "false")
                        .param("eventUrl", "https://event-it")
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Evento IT"))
                .andExpect(jsonPath("$.eventUrl").value("https://event-it"));
    }

    @Test
    void getEvents_shouldReturnUpcomingEvents() throws Exception {
        EventResponseDTO dto = new EventResponseDTO(
                UUID.randomUUID(),
                "Futuro",
                "Evento futuro",
                new Date(),
                "Brasilia",
                "DF",
                true,
                "https://futuro",
                null
        );

        when(eventService.getUpcomingEvents(0, 10)).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/event")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Futuro"));
    }

    @Test
    void getFilteredEvents_shouldApplyFilters() throws Exception {
        EventResponseDTO dto = new EventResponseDTO(
                UUID.randomUUID(),
                "Futuro",
                "Evento futuro",
                new Date(),
                "Brasilia",
                "DF",
                true,
                "https://futuro",
                null
        );

        when(eventService.getFilteredEvents(
                anyInt(), anyInt(),
                anyString(), anyString(), anyString(),
                any(), any())
        ).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/event/filter")
                        .param("page", "0")
                        .param("size", "10")
                        .param("title", "Futuro")
                        .param("city", "Brasilia")
                        .param("uf", "DF")
                        .param("startDate", "2026-01-01")
                        .param("endDate", "2026-12-31"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Futuro"));
    }
}
