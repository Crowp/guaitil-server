package com.guaitilsoft.models;

import com.guaitilsoft.models.constant.ReservationState;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateReservation;

    @Column(nullable = false)
    private Long amountPerson;

    @Enumerated(EnumType.STRING)
    private ReservationState reservationState;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.EAGER)
    private ActivityDescription activityDescription;

    @ManyToOne(targetEntity = Person.class, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "personId")
    private Person person;

    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate(){
        this.updatedAt = LocalDateTime.now();
    }
}
