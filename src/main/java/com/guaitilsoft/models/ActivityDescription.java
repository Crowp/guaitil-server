package com.guaitilsoft.models;

import com.guaitilsoft.models.constant.ActivityType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActivityDescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(length = 150)
    @Size(min = 15, max = 150)
    private String name;

    @Lob
    @NotEmpty
    @Column(length = 1000)
    @Size(min = 100, max = 1000)
    private String description;

    private LocalDateTime activityDate;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @Enumerated(EnumType.STRING)
    private ActivityType activityType;

    private Double personPrice;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

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
