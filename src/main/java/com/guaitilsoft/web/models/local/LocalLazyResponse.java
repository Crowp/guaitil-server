package com.guaitilsoft.web.models.local;

import com.guaitilsoft.web.models.localDescription.LocalDescriptionRequest;
import com.guaitilsoft.web.models.multimedia.MultimediaResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class LocalLazyResponse {

    private Long id;

    private LocalDescriptionRequest localDescription;

    private Boolean state;

    private List<MultimediaResponse> multimedia;
}
