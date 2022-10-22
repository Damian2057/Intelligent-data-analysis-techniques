package com.metaheuristics.simulation.algorithm;

import com.metaheuristics.readers.csv.BagPackItem;
import com.metaheuristics.readers.csv.CsvReader;
import com.metaheuristics.readers.json.CrossOver;
import com.metaheuristics.readers.json.CrossoverType;
import com.metaheuristics.readers.json.JsonReader;
import com.metaheuristics.readers.json.Mutation;
import com.metaheuristics.simulation.model.Specimen;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Logger;

public class GeneticImpl implements Genetic {

    private final static CrossOver crossOver = JsonReader.getCrossOverProperties();
    private final static Mutation mutation = JsonReader.getMutationProperties();
    private final Logger logger = Logger.getLogger(Genetic.class.getSimpleName());
    private final List<BagPackItem> bagPackItems = CsvReader.getBagPackItems();
    private final int backpackCapacity = JsonReader.getBackpackCapacity();
    private final Function<Specimen, Double> function = Specimen::getAdaptation;
    private boolean lock = true;

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
                specimen.setCorrect(true);
                if(lock) {
                    logger.info("Adaptation started with value: " + adaptation);
                    lock = false;
                }
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
    public List<Specimen> crossGenes(List<Specimen> parents, int populationSize) {
        return crossOver.getCrossoverType() == CrossoverType.ONEPOINT
                ? onePointCross(parents, populationSize) : doublePointCross(parents, populationSize);
    }

    private List<Specimen> onePointCross(List<Specimen> parents, int populationSize) {
        List<Specimen> newGeneration = new ArrayList<>();
        for (int i = 0; i < populationSize / 2; i++) {
            List<Specimen> selectedParents = getRandomParents(parents);
            newGeneration.addAll(inheritedChromosome(selectedParents.get(0).getGens(), selectedParents.get(1).getGens()));
        }
        mutationChance(newGeneration);
        return newGeneration;
    }

    private List<Specimen> getRandomParents(List<Specimen> parents) {
        List<Specimen> copyOfList = new ArrayList<>(parents);
        List<Specimen> selectedParents = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            int randomIndex = new Random().nextInt(copyOfList.size());
            Specimen randomElement = copyOfList.get(randomIndex);
            copyOfList.remove(randomIndex);
            selectedParents.add(randomElement);
        }

        return selectedParents;
    }

    private List<Specimen> inheritedChromosome(List<Integer> parent1, List<Integer> parent2) {
        int barrier = random();
        List<Specimen> result = new ArrayList<>();
        result.add(createSpecimen(parent1, parent2, barrier));
        result.add(createSpecimen(parent2, parent1, barrier));
        return result;
    }

    private Specimen createSpecimen(List<Integer> parent1, List<Integer> parent2, int barrier) {
        List<Integer> chromosome = new ArrayList<>();
        if((0.0 + (1) * new Random().nextDouble()) > crossOver.getProbability()) {
            if(new Random().nextBoolean()) {
                chromosome.addAll(new ArrayList<>(parent1));
            } else {
                chromosome.addAll(new ArrayList<>(parent2));
            }
        } else {
            chromosome.addAll(getRange(parent1, 0, barrier));
            chromosome.addAll(getRange(parent2, barrier, 26));
        }
        return new Specimen(chromosome);
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
        return new Random().nextInt((25 - 1) + 1) + 1;
    }

    private void mutationChance(List<Specimen> newGeneration) {
        if((0.0 + (1) * new Random().nextDouble()) < mutation.getProbability()) {
            logger.info("The gene has mutated");
            Random rand = new Random();
            newGeneration.get(rand.nextInt(newGeneration.size())).reverseSingleGen(random());
        }
    }
}
