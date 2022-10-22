package com.metaheuristics.simulation.algorithm;

import com.metaheuristics.readers.csv.BagPackItem;
import com.metaheuristics.readers.csv.CsvReader;
import com.metaheuristics.readers.json.JsonReader;
import com.metaheuristics.simulation.model.Specimen;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class GeneticImpl implements Genetic {

    private final List<BagPackItem> bagPackItems = CsvReader.getBagPackItems();
    private final int backpackCapacity = JsonReader.getBackpackCapacity();
    private final Function<Specimen, Double> function = Specimen::getAdaptation;

    @Override
    public double adaptationFunction(Specimen specimen) {
        int weight = 0;
        int price = 0;
        int index = 0;
        for (int gen : specimen.getGens()) {
            if(gen == 1) {
                weight += bagPackItems.get(index).getWeight();
                price += bagPackItems.get(index).getPrice();
            }
            index++;
        }
        if(weight > backpackCapacity) {
            return 0.0;
        }
        return price;
    }

    @Override
    public void adaptationFunction(List<Specimen> generation) {
        for (Specimen specimen : generation) {
            double adaptation = adaptationFunction(specimen);
            if (adaptation == 0.0) {
                specimen.setCorrect(false);
            } else {
                specimen.setAdaptation(adaptation);
            }
        }
    }

    @Override
    public List<Specimen> rouletteSelection(List<Specimen> generation, int numberOfParents) {
        setProbabilityInPopulation(generation);
        return null;
    }


    private void setProbabilityInPopulation(List<Specimen> generation) {
        double adaptationSum = getAdaptationSum(generation);
        Consumer<Specimen> consumer = x -> x.setProbabilityOfChoice(x.getAdaptation()/adaptationSum);
        for (Specimen specimen : generation) {
            consumer.accept(specimen);
        }
    }

    private double getAdaptationSum(List<Specimen> generation) {
        double adaptationSum = 0.0;
        for (Specimen specimen : generation) {
            adaptationSum += function.apply(specimen);
        }
        return adaptationSum;
    }

    @Override
    public List<Specimen> rankingSelection(List<Specimen> generation, int numberOfParents) {
        return null;
    }

}
