package com.guaitilsoft.web.models.local;

import com.guaitilsoft.web.models.localDescription.LocalDescriptionResponse;
import com.guaitilsoft.web.models.member.MemberLazyResponse;
import com.guaitilsoft.web.models.multimedia.MultimediaResponse;
import com.guaitilsoft.web.models.product.ProductLazyResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class LocalResponse {

    private Long id;

    private LocalDescriptionResponse localDescription;

    private Boolean showLocal;

    private MemberLazyResponse member;

    private List<ProductLazyResponse> products;

    private List<MultimediaResponse> multimedia;

    private boolean firstLogin;

    private boolean resetPassword;
}
