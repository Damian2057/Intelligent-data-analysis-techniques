package com.metaheuristics;


import com.metaheuristics.algorithm.factory.SpecimenFactory;
import com.metaheuristics.readers.json.JsonReader;

import java.io.IOException;

public class MainApp {

    public static void main(String[] args) throws IOException {
        System.out.println(JsonReader.getMutationProperties());
    }
}
