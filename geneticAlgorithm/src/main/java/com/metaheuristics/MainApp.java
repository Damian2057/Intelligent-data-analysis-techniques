package com.metaheuristics;


import com.metaheuristics.readers.json.JsonReader;

public class MainApp {

    public static void main(String[] args) {
        System.out.println(JsonReader.getCrossOverProperties().toString());
    }
}
