package com.metaheuristics.algorithm.factory;

import com.metaheuristics.algorithm.model.Specimen;
import com.metaheuristics.readers.csv.CsvReader;
import com.metaheuristics.readers.json.JsonReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SpecimenFactory {

    public static List<Specimen> getSpecimens() throws IOException {
        List<Specimen> specimenList = new ArrayList<>();
        int populationSize = JsonReader.getPopulationSize();
        for (int i = 0; i < populationSize; i++) {
            specimenList.add(new Specimen(generateChromosome()));
        }
        return specimenList;
    }

    private static List<Integer> generateChromosome() {
        List<Integer> chromosome = new ArrayList<>();
        int chromosomeSize = CsvReader.getBagPackItems().size();
        for (int i = 0; i < chromosomeSize; i++) {
            chromosome.add(1); //TODO: random 1/0
        }
        return chromosome;
    }
}
