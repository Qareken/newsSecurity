package com.example.newsBlock.service.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;
@UtilityClass
public  class  BeanUtils {
    @SneakyThrows
    public void   copyNonNullProperties(Object source, Object destination)  {
        Class<?> clazz = source.getClass();

        var fields = clazz.getDeclaredFields();
        for(Field field : fields){
            field.setAccessible(true);
            var value = field.get(source);
            if(value!=null){
               field.set(destination, value);
            }
        }

    }
}
