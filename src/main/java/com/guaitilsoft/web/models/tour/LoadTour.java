package com.guaitilsoft.web.models.tour;

import com.guaitilsoft.web.models.activity.LoadActivityView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class LoadTour {
    private Long id;

    private Long amountPerson;

    private Date createdAt;

    private Date updatedAt;

    private LoadActivityView activity;
}
