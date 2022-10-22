package com.metaheuristics.simulation.factory;

import com.metaheuristics.simulation.model.Specimen;
import com.metaheuristics.readers.csv.CsvReader;
import com.metaheuristics.readers.json.JsonReader;

import java.util.ArrayList;
import java.util.List;

public class SpecimenFactory {

    private static final int populationSize = JsonReader.getPopulationSize();
    private static final int chromosomeSize = CsvReader.getBagPackItems().size();

    /**
     * @return the first generation
     */
    public List<Specimen> getSpecimens() {
        List<Specimen> specimenList = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            specimenList.add(new Specimen(generateChromosome()));
        }
        return specimenList;
    }

    private List<Integer> generateChromosome() {
        List<Integer> chromosome = new ArrayList<>();
        for (int i = 0; i < chromosomeSize; i++) {
            chromosome.add(random());
        }
        return chromosome;
    }

    private int random() {
        return (int) (Math.random() * ((1) + 1));
    }
}