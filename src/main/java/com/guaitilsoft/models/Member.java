package com.guaitilsoft.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.guaitilsoft.models.constant.MemberType;
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
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String occupation;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updatedAt;

    @OneToOne(cascade = CascadeType.ALL)
    private Person person;

    @OneToMany(targetEntity = Local.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "member")
    @JsonManagedReference
    private List<Local> locals;

    @Enumerated(EnumType.STRING)
    private MemberType memberType;

    public String getEmail(){
        return person.getEmail();
    }

    public String getPersonId() {
        return person.getId();
    }

    @PrePersist
    public void populateLocals() {
        for(Local local : locals)
            local.setMember(this);
    }
}

