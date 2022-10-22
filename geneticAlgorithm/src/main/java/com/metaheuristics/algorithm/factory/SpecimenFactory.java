package com.metaheuristics.algorithm.factory;

import com.metaheuristics.algorithm.model.Specimen;
import com.metaheuristics.readers.csv.CsvReader;
import com.metaheuristics.readers.json.JsonReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SpecimenFactory {

    private static final int populationSize;

    static {
        try {
            populationSize = JsonReader.getPopulationSize();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Specimen> getSpecimens() {
        List<Specimen> specimenList = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            specimenList.add(new Specimen(generateChromosome()));
        }
        return specimenList;
    }

    private static List<Integer> generateChromosome(){
        List<Integer> chromosome = new ArrayList<>();
        int chromosomeSize = CsvReader.getBagPackItems().size();
        for (int i = 0; i < chromosomeSize; i++) {
            chromosome.add(1); //TODO: random 1/0
        }
        return chromosome;
    }
}
