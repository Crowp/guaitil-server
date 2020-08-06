package com.guaitilsoft.models;

import javax.persistence.Column;
import javax.persistence.Id;

public class Client extends Person{

    @Id
    @Column(name = "client_id")
    private Long id;

    @Column(nullable = false)
    private String email;
}
