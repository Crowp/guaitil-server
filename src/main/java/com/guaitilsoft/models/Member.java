package com.guaitilsoft.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.guaitilsoft.models.constant.MemberType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "member")
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id
    @Column(name = "_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String occupation;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="person_id")
    private Person person;

    @JsonIgnoreProperties("member")
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="local_id")
    private List<Local> locals;

    @Enumerated(EnumType.STRING)
    private MemberType memberType;

    public String getEmail(){
        return person.getEmail();
    }

    public String getPersonId() {
        return person.getId();
    }

}

