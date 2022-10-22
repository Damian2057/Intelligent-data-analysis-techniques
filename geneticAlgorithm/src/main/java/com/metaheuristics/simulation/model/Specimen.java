package com.metaheuristics.simulation.model;

import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
public class Specimen {

    private List<Integer> gens;
    private double adaptation = 0;
    private boolean isCorrect = true;

    public Specimen(List<Integer> gens) {
        this.gens = gens;
    }
}
