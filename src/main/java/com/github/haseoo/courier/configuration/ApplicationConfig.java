package com.slicesy.pizzaorder.configuration;

import com.slicesy.pizzaorder.configuration.converters.MapperConverter;
import com.slicesy.pizzaorder.exceptions.ModelMapperInitializeException;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.reflections.Reflections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.InvocationTargetException;

import static com.slicesy.pizzaorder.utilities.Constants.REFLECTION_PREFIX;

@Configuration
public class ApplicationConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        Reflections reflections = new Reflections(REFLECTION_PREFIX);
        loadConverters(modelMapper, reflections);
        return modelMapper;
    }

    @SuppressWarnings("unchecked")
    private void loadConverters(ModelMapper modelMapper, Reflections reflections) {
        reflections.getSubTypesOf(MapperConverter.class).forEach(clazz -> {
                    try {
                        modelMapper.addConverter((Converter) clazz.getDeclaredConstructor().newInstance());
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        throw new ModelMapperInitializeException(e);
                    }
                }
        );
    }
}
