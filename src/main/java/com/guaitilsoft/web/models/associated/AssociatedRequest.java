package com.guaitilsoft.web.models.associated;

import com.guaitilsoft.web.models.person.PersonRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class AssociatedRequest {

    private Long id;

    @NotBlank
    private String occupation;

    @NotNull
    private Date membershipDate;

    private String personId;
}
