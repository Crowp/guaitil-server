package com.guaitilsoft.config;

import com.guaitilsoft.models.Address;
import com.guaitilsoft.models.Local;
import com.guaitilsoft.web.models.local.LocalResponse;
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
        return modelMapper;
    }

    private void mapperMember(ModelMapper modelMapper) {
        memberResponseConfig(modelMapper);
    }

    private void memberResponseConfig(ModelMapper modelMapper) {
        modelMapper.typeMap(Local.class, LocalResponse.class)
                .addMappings(mapping -> {
                    mapping.map(
                            source -> source.getLocalDescription().getAddress(),
                            (destination, value) -> destination.setAddress((Address) value)
                    );
                });
        modelMapper.typeMap(LocalResponse.class, Local.class)
                .addMappings(mapping -> {
                    mapping.map(LocalResponse::getAddress,
                            (destination, value) -> destination.getLocalDescription().setAddress((Address) value));
                });
    }
}
