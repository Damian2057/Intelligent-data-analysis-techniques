package com.metaheuristics.simulation.model;

import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
public class Specimen implements Comparable<Specimen> {

    private List<Integer> gens;
    private double adaptation = 0.0;
    private boolean isCorrect = true;
    private double probabilityOfChoice = 0.8;

    public Specimen(List<Integer> gens) {
        this.gens = gens;
    }

    @Override
    public int compareTo(Specimen o) {
        return Double.compare(getAdaptation(), o.getAdaptation());
    }
}
