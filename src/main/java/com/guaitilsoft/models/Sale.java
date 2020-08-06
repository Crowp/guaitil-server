package com.guaitilsoft.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class Sale implements Serializable {

    @Id
    @Column(name = "sale_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Local local;

    @Column(nullable = false,name = "sale_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date saleDate;

}
