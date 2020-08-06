package com.guaitilsoft.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


@Setter
@Getter
@Entity
@NoArgsConstructor
public class Member extends Person implements Serializable {

    @Id
    @Column(name = "member_id")
    private Long id;
}
