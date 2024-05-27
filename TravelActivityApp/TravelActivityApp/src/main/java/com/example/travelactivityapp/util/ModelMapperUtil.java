package com.example.travelactivityapp.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


// For all DTOs, this converts them into an actual entity object
@Component
public class ModelMapperUtil {

    @Autowired
    ModelMapper modelMapper;

    public <D, E> E map(D dto, Class<E> entityType) {

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT).setSkipNullEnabled(true);
        return modelMapper.map(dto, entityType);
    }
}
