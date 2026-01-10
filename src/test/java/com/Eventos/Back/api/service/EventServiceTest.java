package com.Eventos.Back.api.service;
import com.Eventos.Back.api.domain.address.Address;
import com.Eventos.Back.api.domain.event.Event;
import com.Eventos.Back.api.domain.event.EventRequestDTO;
import com.Eventos.Back.api.domain.event.EventResponseDTO;
import com.Eventos.Back.api.repositories.EventRepository;
import com.amazonaws.services.s3.AmazonS3;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private AmazonS3 s3Client;

    @Mock
    private AddressService addressService;

    @Mock
    private EventRepository repository;

    @InjectMocks
    private EventService eventService;

    @Mock
    private MultipartFile image;

    @Test
    void createEvent_shouldSaveEventAndCreateAddress_whenNotRemote() {

        Long nowMillis = System.currentTimeMillis();
        EventRequestDTO dto = new EventRequestDTO(
                "Titulo",
                "Descricao",
                nowMillis,
                "Brasilia",
                "DF",
                false,                 // remote = false
                "https://event-url",
                image
        );

        when(image.getOriginalFilename()).thenReturn("foto.png");

        Event result = eventService.createEvent(dto);

        ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
        verify(repository).save(eventCaptor.capture());
        Event saved = eventCaptor.getValue();

        assertThat(saved.getTitle()).isEqualTo("Titulo");
        assertThat(saved.getDescription()).isEqualTo("Descricao");
        assertThat(saved.getEventUrl()).isEqualTo("https://event-url");
        assertThat(saved.isRemote()).isFalse();
        assertThat(saved.getDate()).isCloseTo(new Date(nowMillis), 1000);
        verify(addressService).createAddress(dto, saved);

        verify(s3Client).putObject(anyString(), anyString(), (File) any());
    }
    @Test
    void getUpcomingEvents_shouldMapEventsToResponseDTO() {
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);

        Event event = new Event();
        event.setId(UUID.randomUUID());
        event.setTitle("Evento 1");
        event.setDescription("Descricao 1");
        event.setEventUrl("https://event-1");
        event.setImgUrl("https://img-1");
        event.setRemote(false);
        event.setDate(new Date());

        Address address = new Address();
        address.setCity("Brasilia");
        address.setUf("DF");
        event.setAddress(address);

        Page<Event> pageResult = new PageImpl<>(List.of(event), pageable, 1);
        when(repository.findUpcomingEvents(any(Date.class), eq(pageable))).thenReturn(pageResult);

        List<EventResponseDTO> result = eventService.getUpcomingEvents(page, size);

        assertThat(result).hasSize(1);
        EventResponseDTO dto = result.get(0);

        assertThat(dto.title()).isEqualTo("Evento 1");
        assertThat(dto.description()).isEqualTo("Descricao 1");
        assertThat(dto.eventUrl()).isEqualTo("https://event-1");
        assertThat(dto.imgUrl()).isEqualTo("https://img-1");
        assertThat(dto.remote()).isFalse();
        assertThat(dto.city()).isEqualTo("Brasilia");
        assertThat(dto.state()).isEqualTo("DF");

        verify(repository).findUpcomingEvents(any(Date.class), eq(pageable));
    }

    @Test
    void getFilteredEvents_shouldCallRepositoryWithNormalizedParams_andMapToDTO() {
        int page = 1;
        int size = 5;

        String title = "conf";
        String city = null;   // vai virar ""
        String uf = "DF";
        Date startDate = null; // vira new Date(0)
        Date endDate = new Date();

        Pageable pageable = PageRequest.of(page, size);

        Event event = new Event();
        event.setId(UUID.randomUUID());
        event.setTitle("Conf Java");
        event.setDescription("Conf desc");
        event.setEventUrl("https://conf");
        event.setImgUrl("https://img-conf");
        event.setRemote(true);
        event.setDate(new Date());

        Address address = new Address();
        address.setCity("Brasilia");
        address.setUf("DF");
        event.setAddress(address);

        Page<Event> pageResult = new PageImpl<>(List.of(event), pageable, 1);

        when(repository.findFilteredEvents(
                anyString(), anyString(), anyString(),
                any(Date.class), any(Date.class),
                eq(pageable))
        ).thenReturn(pageResult);

        List<EventResponseDTO> result = eventService.getFilteredEvents(
                page, size, title, city, uf, startDate, endDate
        );

        assertThat(result).hasSize(1);
        EventResponseDTO dto = result.get(0);

        assertThat(dto.title()).isEqualTo("Conf Java");
        assertThat(dto.city()).isEqualTo("Brasilia");
        assertThat(dto.state()).isEqualTo("DF");
        assertThat(dto.remote()).isTrue();

        verify(repository).findFilteredEvents(
                eq("conf"),
                eq(""),
                eq("DF"),
                any(Date.class),
                eq(endDate),
                eq(pageable)
        );
    }
}

