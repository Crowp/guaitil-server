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

    @OneToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH })
    private ActivityDescription activityDescription;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @JoinTable(name = "activity_local_description",
            joinColumns = { @JoinColumn(name = "fk_activity_id") },
            inverseJoinColumns = { @JoinColumn(name = "fk_local_description_id") })
    private Set<LocalDescription> localsDescriptions = new HashSet<>();

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private List<Multimedia> multimedia;

    private Boolean isActive;

    @PrePersist
    public void prePersist(){
        this.isActive = true;
    }

}
