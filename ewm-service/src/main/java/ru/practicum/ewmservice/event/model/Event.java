package ru.practicum.ewmservice.event.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewmservice.category.model.Category;
import ru.practicum.ewmservice.event.enums.State;
import ru.practicum.ewmservice.user.model.User;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "annotation", nullable = false, length = 2000)
    private String annotation;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "confirmed_requests")
    private Long confirmedRequests;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "description", length = 7000)
    private String description;

    @Column(name = "event_date")
    private LocalDateTime eventDate;

    @ManyToOne
    @JoinColumn(name = "initiator_id")
    private User initiator;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(name = "paid")
    private Boolean paid;

    @Column(name = "participant_limit")
    private Long participantLimit;

    @Column(name = "published_on")
    private LocalDateTime publishedOn;

    @Column(name = "request_moderation")
    private Boolean requestModeration;

    @Enumerated(EnumType.STRING)
    private State state;

    @Column(name = "title", nullable = false, length = 120)
    private String title;

    @Column(name = "views")
    private Long views;
}