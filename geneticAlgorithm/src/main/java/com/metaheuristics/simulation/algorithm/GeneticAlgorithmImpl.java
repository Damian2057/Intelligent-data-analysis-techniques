package com.metaheuristics.simulation.algorithm;

import com.metaheuristics.simulation.factory.SpecimenFactory;
import com.metaheuristics.simulation.model.Specimen;

import java.util.ArrayList;
import java.util.List;

public class GeneticAlgorithmImpl implements GeneticAlgorithm {

    private final List<Specimen> generation;

    public GeneticAlgorithmImpl() {
        generation = new ArrayList<>(SpecimenFactory.getSpecimens());
    }

    @Override
    public void startSimulation() {

    }

    @Override
    public List<Specimen> getGeneration() {
        return generation;
    }
}
