package com.spring.ioc.ConversionService.Convert;


import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConverterBean {
    @Component
    public static class StringToConverter implements Converter<String, ConverterObject> {

        @Override
        public ConverterObject convert(String str) {
            ConverterObject obj = new ConverterObject();
            obj.setName(str.split("\\|")[0]);
            obj.setType(str.split("\\|")[1]);
            obj.setList(Arrays.asList(str.split("\\|")));
            return obj;
        }
    }
    @Component
    public static class ConverterToString implements Converter<ConverterObject,String> {

        @Override
        public String convert(ConverterObject obj) {
            return Stream.of(obj.getClass().getDeclaredFields()).map(field-> {
                try {
                    field.setAccessible(Boolean.TRUE);
                    Object object = field.get(obj);
                    return (CharSequence) field.get(obj);
                } catch (IllegalAccessException e) {
                    return null;
                }
            }).collect(Collectors.joining("|"));
        }
    }
}
