package com.metaheuristics;


import com.metaheuristics.algorithm.factory.SpecimenFactory;
import com.metaheuristics.algorithm.model.Specimen;

public class MainApp {

    public static void main(String[] args) {
        System.out.println(SpecimenFactory.getSpecimens());
    }
}
