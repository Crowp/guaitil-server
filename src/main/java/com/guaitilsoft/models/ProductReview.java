package com.guaitilsoft.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guaitilsoft.models.constant.ReviewState;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Product_Review")
@NoArgsConstructor
public class ProductReview implements Serializable {

    @Id
    @Column(name = "productreview_id")
    private Long id;

    @Column(nullable = false, name = "review_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date reviewDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReviewState state;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Product> products;
}
