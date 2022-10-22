package com.metaheuristics.simulation.factory;

import com.metaheuristics.simulation.model.Specimen;
import com.metaheuristics.readers.csv.CsvReader;
import com.metaheuristics.readers.json.JsonReader;

import java.util.ArrayList;
import java.util.List;

public class SpecimenFactory {

    /**
     * @return the first generation
     */
    public static List<Specimen> getSpecimens() {
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
            chromosome.add(random());
        }
        return chromosome;
    }

    private static int random() {
        return (int) (Math.random() * ((1) + 1));
    }
}