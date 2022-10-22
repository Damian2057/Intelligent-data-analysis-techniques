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
            specimenList.add(new Specimen(generateTotallyRandomChromosome()));
        }
        return specimenList;
    }

    private List<Integer> generateTotallyRandomChromosome() {
        List<Integer> chromosome = new ArrayList<>();
        for (int i = 0; i < chromosomeSize; i++) {
            chromosome.add(random());
        }
        return chromosome;
    }

    private int random() {
        return (int) (Math.random() * ((1) + 1));
    }

    @Deprecated
    private List<Integer> simulatedRandomChromosome() {
        List<Integer> chromosome = new ArrayList<>();
        int max = 0;
        for (int i = 0; i < chromosomeSize; i++) {
            int value = random();
            if(value == 1 && max < 12) {
                chromosome.add(1);
                max++;
            } else {
                chromosome.add(0);
            }
        }
        return chromosome;
    }

}