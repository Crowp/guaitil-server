package com.guaitilsoft.models;

import com.guaitilsoft.models.constant.ReviewState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ProductReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime reviewDate;

    @Enumerated(EnumType.STRING)
    private ReviewState state;

    private String comment;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private ProductDescription productDescription;

    @PrePersist
    public void prePersist() {
        this.reviewDate = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.reviewDate = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
