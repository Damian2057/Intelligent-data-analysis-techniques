package com.metaheuristics;


import com.metaheuristics.algorithm.factory.SpecimenFactory;

import java.io.IOException;

public class MainApp {

    public static void main(String[] args) throws IOException {
        System.out.println(SpecimenFactory.getSpecimens());
    }
}
