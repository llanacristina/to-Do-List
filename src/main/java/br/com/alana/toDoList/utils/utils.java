package br.com.alana.toDoList.utils;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class utils {
    //PEGA TUDO QUE É NULO E MESCLA PARA TER SUBSTITUIÇOẼS ESPECÍFICAS

    public static void copyNonNullProperties(Object source, Object target){
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }
    
    public static String[] getNullPropertyNames(Object source){
        final BeanWrapper src = new BeanWrapperImpl(source);

        
       PropertyDescriptor[] pds = src.getPropertyDescriptors();

       Set<String> emptyNanes = new HashSet<>();

       for(PropertyDescriptor pd: pds){
        Object srcValue = src.getPropertyValue(pd.getName());
        if(srcValue == null){
            emptyNanes.add(pd.getName());
        }
       }
       String[] result = new String[emptyNanes.size()];
       return emptyNanes.toArray(result);
    }
}
