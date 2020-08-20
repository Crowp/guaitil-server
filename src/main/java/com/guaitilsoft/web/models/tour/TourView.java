package com.guaitilsoft.web.models.tour;

import com.guaitilsoft.models.Activity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class TourView {

    private Long id;

    private Long amountPerson;

    private Activity activity;
}
