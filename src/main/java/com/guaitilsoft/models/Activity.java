package com.guaitilsoft.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class Activity implements Serializable {

    @Id
    @Column(name = "activity_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false,name = "activity_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date activityDate;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Local> local;
}
