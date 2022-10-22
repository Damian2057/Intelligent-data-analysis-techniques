package com.metaheuristics.simulation.algorithm;

import com.metaheuristics.readers.csv.BagPackItem;
import com.metaheuristics.readers.csv.CsvReader;
import com.metaheuristics.readers.json.CrossOver;
import com.metaheuristics.readers.json.CrossoverType;
import com.metaheuristics.readers.json.JsonReader;
import com.metaheuristics.simulation.factory.SpecimenFactory;
import com.metaheuristics.simulation.model.Specimen;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GeneticImpl implements Genetic {

    private final List<BagPackItem> bagPackItems = CsvReader.getBagPackItems();
    private final int backpackCapacity = JsonReader.getBackpackCapacity();
    private final static CrossOver crossOver = JsonReader.getCrossOverProperties();
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
        List<Specimen> selected = new ArrayList<>();
        setProbabilityInPopulation(generation);
        return selected;
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
    public List<Specimen> tournamentSelection(List<Specimen> generation, int numberOfParents) {
        List<Specimen> selected = new ArrayList<>();
        List<Specimen> copyOfList = new ArrayList<>(generation);
        List<List<Specimen>> tournamentGroups = new ArrayList<>();
        int size = generation.size() / numberOfParents;
        for (int i = 0; i < numberOfParents; i++) {
            tournamentGroups.add(new ArrayList<>(getSubgroup(copyOfList, size)));
        }
        if(!copyOfList.isEmpty()) {
            int index = 0;
            for (Specimen specimen : copyOfList) {
                tournamentGroups.get(index).add(specimen);
                index++;
                if (index > numberOfParents) {
                    index = 0;
                }
            }
        }
        //sort - The best with the greatest adaptation is at the beginning
        for (List<Specimen> specimenList : tournamentGroups){
            Collections.sort(specimenList);
            selected.add(specimenList.get(0));
        }
        return selected;
    }

    private List<Specimen> getSubgroup(List<Specimen> generation, int size) {
        List<Specimen> result = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            int randomIndex = rand.nextInt(generation.size());
            Specimen randomElement = generation.get(randomIndex);
            generation.remove(randomIndex);
            result.add(randomElement);
        }
        return result;
    }

    @Override
    public List<Specimen> crossOver(List<Specimen> parents, int populationSize) {
        return crossOver.getCrossoverType() == CrossoverType.ONEPOINT
                ? onePointCross(parents, populationSize) : doublePointCross(parents, populationSize);
    }


    private List<Specimen> onePointCross(List<Specimen> parents, int populationSize) {
        List<Specimen> newGeneration = new ArrayList<>();
        for (int i = 0; i < populationSize / 2; i++) {
            newGeneration.addAll(inheritedChromosome(parents.get(0).getGens(), parents.get(1).getGens()));
        }

        return newGeneration;
    }

    private List<Specimen> inheritedChromosome(List<Integer> parent1, List<Integer> parent2) {
        int barrier = random();
        List<Specimen> result = new ArrayList<>();
        List<Integer> chromosome = new ArrayList<>(getRange(parent1, 0, barrier));
        chromosome.addAll(getRange(parent2, barrier, 26));
        result.add(new Specimen(chromosome));
        chromosome.clear();
        chromosome.addAll(getRange(parent2, 0, barrier));
        chromosome.addAll(getRange(parent1, barrier, 26));
        result.add(new Specimen(chromosome));
        return result;
    }

    private List<Specimen> doublePointCross(List<Specimen> parents, int populationSize) {
        return null;
    }

    private List<Integer> getRange(List<Integer> list, int start, int end) {
        List<Integer> newList = new ArrayList<>();
        for (int i = start; i < end; i++) {
            newList.add(list.get(i));
        }
        return newList;
    }

    private int random() {
        return new Random().nextInt((26 - 1) + 1) + 1;
    }

}
