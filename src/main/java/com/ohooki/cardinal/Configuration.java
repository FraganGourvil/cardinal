package com.ohooki.cardinal;

import com.google.gson.JsonObject;

import java.util.ArrayList;


public class Configuration {

    private Cardinal instance;
    public String redisIp;
    public String redisPassword;
    public int redisPort;
    private JsonObject jsonConfiguration;

    public Configuration(Cardinal instance) {
        this.instance = instance;
    }


    public void load(String path) {
        System.out.println("Configuration file path is : " + path);


        this.redisIp = "127.0.0.1";

        this.redisPassword = "";

        this.redisPort = 6379;
    }


    public boolean validate(JsonObject object) {
        boolean validated = true;

        ArrayList<String> required = new ArrayList<String>() {

        };


        for (String contained : required) {

            if (!object.has(contained)) validated = false;

        }

        return validated;
    }
}
