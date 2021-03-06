package com.guaitilsoft.config;

import com.guaitilsoft.models.*;
import com.guaitilsoft.models.constant.ProductType;
import com.guaitilsoft.web.models.local.LocalResponse;
import com.guaitilsoft.web.models.product.ProductLazyResponse;
import com.guaitilsoft.web.models.product.ProductRequest;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper;
    }

}
