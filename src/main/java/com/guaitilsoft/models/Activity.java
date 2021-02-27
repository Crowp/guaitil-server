package com.guaitilsoft.models;

import com.guaitilsoft.models.constant.ActivityType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @JoinTable(name = "activity_local_description",
            joinColumns = { @JoinColumn(name = "fk_activity_id") },
            inverseJoinColumns = { @JoinColumn(name = "fk_local_description_id") })
    private Set<LocalDescription> localsDescriptions = new HashSet<>();

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
