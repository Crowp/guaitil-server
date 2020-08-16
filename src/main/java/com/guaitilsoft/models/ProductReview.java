package com.guaitilsoft.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guaitilsoft.models.constant.ReviewState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "product_review")
@AllArgsConstructor
@NoArgsConstructor
public class ProductReview {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date reviewDate;

    @Enumerated(EnumType.STRING)
    private ReviewState state;

    @OneToOne(cascade = CascadeType.REFRESH)
    private Product product;
}
