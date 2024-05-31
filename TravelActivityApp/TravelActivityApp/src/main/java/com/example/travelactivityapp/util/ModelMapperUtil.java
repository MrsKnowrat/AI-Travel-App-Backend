package com.example.travelactivityapp.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

// For all DTOs, this converts them into an actual entity object
@Component
public class ModelMapperUtil {

    @Autowired
    ModelMapper modelMapper;

    public <D, E> E map(D dto, Class<E> entityType) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT).setSkipNullEnabled(true);
        return modelMapper.map(dto, entityType);
    }

    public <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return modelMapper.map(source, new TypeToken<List<T>>() {}.getType());
    }
}