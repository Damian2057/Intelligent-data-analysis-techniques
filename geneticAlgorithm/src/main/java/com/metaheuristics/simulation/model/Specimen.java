package com.metaheuristics.simulation.model;

import lombok.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

@ToString
@Setter
public class Specimen implements Comparable<Specimen>, Cloneable {

    private List<Integer> gens;
    private double adaptation = 0.0;
    private boolean isCorrect = true;
    private double probabilityOfChoice = 0.8;

    public Specimen(List<Integer> gens) {
        this.gens = gens;
    }

    @Override
    public int compareTo(Specimen o) {
        return Double.compare(o.getAdaptation(), getAdaptation());
    }

    public List<Integer> getGens() {
        return Collections.unmodifiableList(gens);
    }

    public double getAdaptation() {
        return adaptation;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public double getProbabilityOfChoice() {
        return probabilityOfChoice;
    }

    public void reverseSingleGen(int index) {
        Function<Integer, Integer> function = x -> x == 0 ? 1 : 0;
        gens.set(index, function.apply(gens.get(index)));
    }

    @Override
    public Specimen clone() {
        try {
            Specimen clone = (Specimen) super.clone();
            List<Integer> copyList = new ArrayList<>(gens);
            clone.setGens(copyList);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

}
