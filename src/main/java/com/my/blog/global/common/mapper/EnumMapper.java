package com.my.blog.global.common.mapper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
public class EnumMapper {
    Map<String, List<EnumValueDTO>> factory = new HashMap<>();

    private List<EnumValueDTO> toEnumValues(Class<? extends EnumModel> e){
        return Arrays.stream(e.getEnumConstants())
                .map(EnumValueDTO::new)
                .collect(Collectors.toList());
    }

    public void put(String key, Class<? extends EnumModel> e) {
        factory.put(key, toEnumValues(e));
    }

    public Map<String,List<EnumValueDTO>> getAll(){
        return factory;
    }

    public List<EnumValueDTO> getOne(String key){
        return  factory.get(key);
    }

    public Map<String,List<EnumValueDTO>> get(String keys){
        return Arrays.stream(keys.split(","))
                .collect(Collectors.toMap(Function.identity(), key -> factory.get(key)));
    }



}
