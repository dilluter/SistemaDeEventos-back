package com.Eventos.Back.api.domain.coupon;

import com.Eventos.Back.api.domain.event.Event;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "event-coupon")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class coupon {
    @Id
    @GeneratedValue
    private UUID id;
    private String code;
    private String discount;
    private Date valid;
    @ManyToOne
    @JoinColumn (name = "event_id")
    private Event event;
}
