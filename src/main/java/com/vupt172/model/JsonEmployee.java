package com.vupt172.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class JsonEmployee implements Serializable {
    private String firstName;
    private String lastName;
    private String email;
    public JsonEmployee(){}
    @JsonCreator
    public JsonEmployee(@JsonProperty("firstName")String firstName,@JsonProperty("lastName")String lastName,@JsonProperty("email")String email){
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
    }
}
