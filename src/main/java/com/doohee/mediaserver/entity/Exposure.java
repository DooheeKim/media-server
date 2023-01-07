package com.doohee.mediaserver.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum Exposure {
    PRIVATE("private"), PUBLIC("public");
    private final String value;

    Exposure(String value) {
        this.value = value;
    }

    @JsonCreator
    public static Exposure from(String value){
        for(Exposure exposure: Exposure.values()){
            if(exposure.getValue().equals(value)){
                return exposure;
            }
        }
        return null;
    }

    @JsonValue
    public String getValue(){
        return value;
    }
}
