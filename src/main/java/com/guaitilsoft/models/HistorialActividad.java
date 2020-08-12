package com.guaitilsoft.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guaitilsoft.models.authoriry.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Historial_Actividad")
public class HistorialActividad {

    @Id
    @Column(name = "audit_id")
    private Long id;

    @Column(nullable = false)
    private String action;

    @Column(nullable = false,name = "audit_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date auditDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
