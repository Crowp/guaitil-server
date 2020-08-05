package com.guaitilsoft.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Person implements Serializable {

    @Id
    @Column
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, name = "first_last_name")
    private String firstLastName;

    @Column(nullable = false, name = "second_last_name")
    private String secondLastName;

    @Column(nullable = false)
    private String telephone;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getFirstLastName() { return firstLastName; }

    public void setFirstLastName(String firstLastName) { this.firstLastName = firstLastName; }

    public String getSecondLastName() { return secondLastName; }

    public void setSecondLastName(String secondLastName) { this.secondLastName = secondLastName; }

    public String getTelephone() { return telephone; }

    public void setTelephone(String telephone) { this.telephone = telephone; }
}
