package models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

/**
 * Naive object that has a message which we return out.
 */
public class HelloWorldModel {

    public final static  String mediaType = "application/json";
    @NotNull
    @JsonProperty("message")
    public String message;

    public HelloWorldModel(){
        this.message = "Hello";
    }

    public HelloWorldModel(String name){
        this.message = "Hello " + name;
    }
}
