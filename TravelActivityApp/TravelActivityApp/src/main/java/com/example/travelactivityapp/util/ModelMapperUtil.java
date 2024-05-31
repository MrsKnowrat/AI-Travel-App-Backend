package com.example.travelactivityapp.util;

/* This class is a utility class that uses the ModelMapper library to map between DTOs and entities. 
It provides methods for mapping a single object, and a list of objects. It enhances
the default behavior of the ModelMapper library by setting a strict matching strategy 
and enabling the skipping of null values. */

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

// For all DTOs, this converts them into an actual entity object
@Component // This component is a Spring component and can be injected into other components
public class ModelMapperUtil {

    @Autowired // This autowires the ModelMapper instance into this class
    ModelMapper modelMapper; // This is the ModelMapper instance; automates mapping of objects to different types    

// Defines a generic method named 'map' that takes an object of type D (DTO) and a Class object representing the type E (entity). It returns an object of type E.
    public <D, E> E map(D dto, Class<E> entityType) { // This method maps a DTO to an entity
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT).setSkipNullEnabled(true); // This sets the matching strategy to strict and skips null values
        // Configures the ModelMapper:
        // - setMatchingStrategy(MatchingStrategies.STRICT): Ensures that only fields with exactly matching names and types are mapped.
        // - setSkipNullEnabled(true): Configures the mapper to skip null values during the mapping process.
        return modelMapper.map(dto, entityType);
        // Performs the mapping from the source object (dto) to the target type (entityType) and returns the mapped object.
    }
    public <S, T> List<T> mapList(List<S> source, Class<T> targetClass) { // This method maps a list of DTOs to a list of entities
// Defines a generic method named 'mapList' that takes a List of objects of type S and a Class object representing the type T. It returns a List of objects of type T.
        return modelMapper.map(source, new TypeToken<List<T>>() {}.getType()); // This maps the list of DTOs to the list of entities
// Uses ModelMapper to map the list 'source' to a new list of 'targetClass' type. TypeToken is used here to preserve the generic type information which is necessary for ModelMapper to perform the correct mapping.
    }
}