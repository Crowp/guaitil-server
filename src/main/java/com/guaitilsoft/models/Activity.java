package com.guaitilsoft.models;

import com.guaitilsoft.models.constant.ActivityType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    private LocalDateTime activityDate;

    @Enumerated(EnumType.STRING)
    private ActivityType activityType;

    @Column
    private Double personCost;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Local> locals;

    @OneToMany(cascade = CascadeType.MERGE)
    private List<Multimedia> multimedia;

    @PrePersist
    public void prePersist(){
        this.activityDate = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate(){
        this.updatedAt = LocalDateTime.now();
    }
}
