package com.metaheuristics;


import com.metaheuristics.readers.Json.JsonReader;

import java.io.IOException;

public class MainApp {

    public static void main(String[] args) throws IOException {
        System.out.println(JsonReader.getMutationProperties().getIsEnable().get());
    }
}
