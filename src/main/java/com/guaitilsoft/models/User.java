package com.guaitilsoft.models;

import com.guaitilsoft.models.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String password;

    private Boolean firstLogin;

    @Enumerated(value = EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    List<Role> roles;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ActivityHistory> activityHistories;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="member_id")
    private Member member;

    public String getEmail(){
        return member.getEmail();
    }
}
