package com.guaitilsoft.config;

import com.guaitilsoft.models.*;
import com.guaitilsoft.models.constant.ProductType;
import com.guaitilsoft.web.models.activity.ActivityResponse;
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
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        mapperMember(modelMapper);
        mapperProduct(modelMapper);
        return modelMapper;
    }

    private void mapperMember(ModelMapper modelMapper) {
        memberResponseConfig(modelMapper);
    }

    private void mapperProduct(ModelMapper modelMapper) {
        productRequestConfig(modelMapper);
    }

    private void memberResponseConfig(ModelMapper modelMapper) {
        modelMapper.typeMap(Local.class, LocalResponse.class)
                .addMappings(mapping -> mapping.map(
                        source -> source.getLocalDescription().getAddress(),
                        (destination, value) -> destination.setAddress((Address) value)
                ));
        modelMapper.typeMap(LocalResponse.class, Local.class)
                .addMappings(mapping -> mapping.map(LocalResponse::getAddress,
                        (destination, value) -> destination.getLocalDescription().setAddress((Address) value)));
    }

    private void productRequestConfig(ModelMapper modelMapper) {
        modelMapper.typeMap(Product.class, ProductRequest.class)
                .addMappings(mapping -> mapping.map(
                        source -> source.getProductDescription().getName(),
                        (destination, value) -> destination.setName((String) value)
                ));

        modelMapper.typeMap(Product.class, ProductLazyResponse.class)
                .addMappings(mapping -> mapping.map(
                        source -> source.getProductDescription().getName(),
                        (destination, value) -> destination.setName((String) value)
                ));

        modelMapper.typeMap(ProductRequest.class, Product.class)
                .addMappings(mapping -> mapping.map(ProductRequest::getName,
                        (destination, value) -> destination.getProductDescription().setName((String) value)
                ))
                .addMappings(mapping -> mapping.map(ProductRequest::getProductType,
                        (destination, value) -> destination.getProductDescription().setProductType((ProductType) value)
                ))
                .addMappings(mapping -> mapping.map(ProductRequest::getProductPrice,
                        (destination, value) -> destination.getProductDescription().setProductPrice((ProductPrice) value)
                ));
    }
}
