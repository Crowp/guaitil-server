package com.guaitilsoft.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Audit {

    @Id
    @Column(name = "audit_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String action;

    @Column(nullable = false,name = "audit_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date auditDate;

    @Column(nullable = false)
    private String role;
}
