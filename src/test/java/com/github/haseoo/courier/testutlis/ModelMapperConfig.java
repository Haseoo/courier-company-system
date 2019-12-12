package com.github.haseoo.courier.testutlis;

import com.github.haseoo.courier.configuration.converters.MapperConverter;
import com.github.haseoo.courier.exceptions.ModelMapperInitializeException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;

import static com.github.haseoo.courier.utilities.Constants.REFLECTION_PREFIX;
import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ModelMapperConfig {
    static public ModelMapper ModelMapperConfig() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFieldAccessLevel(PRIVATE);
        Reflections reflections = new Reflections(REFLECTION_PREFIX);
        loadConverters(modelMapper, reflections);
        return modelMapper;
    }

    @SuppressWarnings("unchecked")
    private static void loadConverters(ModelMapper modelMapper, Reflections reflections) {
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
