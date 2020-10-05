package com.guaitilsoft.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guaitilsoft.models.constant.ActivityType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
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

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date activityDate;

    @Enumerated(EnumType.STRING)
    private ActivityType activityType;

    @Column
    private Double personCost;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Local> locals;

    @OneToMany(cascade = CascadeType.MERGE)
    private List<Multimedia> multimedia;
}
