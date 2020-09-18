package com.guaitilsoft.web.models.tour;

import com.guaitilsoft.web.models.activity.ActivityView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
public class TourView {

    private Long id;

    private Long amountPerson;

    private Date createdAt;

    private Date updatedAt;

    private ActivityView activity;
}
