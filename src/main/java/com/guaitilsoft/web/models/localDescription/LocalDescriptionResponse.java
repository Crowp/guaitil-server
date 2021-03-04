package com.guaitilsoft.web.models.localDescription;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guaitilsoft.models.Address;
import com.guaitilsoft.models.constant.LocalType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class LocalDescriptionResponse {

    private Long id;

    private String localName;

    private String description;

    private String localTelephone;

    private LocalType localType;

    private Address address;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime updatedAt;
}
