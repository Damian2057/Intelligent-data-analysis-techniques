package com.metaheuristics.simulation.algorithm;

import com.metaheuristics.readers.csv.BagPackItem;
import com.metaheuristics.readers.csv.CsvReader;
import com.metaheuristics.readers.json.CrossOver;
import com.metaheuristics.readers.json.CrossoverType;
import com.metaheuristics.readers.json.JsonReader;
import com.metaheuristics.readers.json.Mutation;
import com.metaheuristics.simulation.model.Specimen;

import java.text.DecimalFormat;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Logger;

public class GeneticImpl implements Genetic {

    private final static CrossOver crossOver = JsonReader.getCrossOverProperties();
    private final static Mutation mutation = JsonReader.getMutationProperties();
    private static final DecimalFormat decimalFormat = new DecimalFormat("########.#");
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
                    logger.info("Adaptation started with value: " + decimalFormat.format(adaptation));
                    lock = false;
                }
            }
        }
    }

    @Override
    public List<Specimen> rouletteSelection(List<Specimen> generation, int numberOfParents) {
        List<Specimen> selected = new ArrayList<>();
        setProbabilityInPopulation(generation);
        //TODO: complete
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
    public List<Specimen> crossGenes(List<Specimen> parents) {
        List<Specimen> copyOfList = new ArrayList<>(parents);
        return crossOver.getCrossoverType() == CrossoverType.ONEPOINT
                ? onePointGenCross(copyOfList) : doublePointGenCross(copyOfList);
    }

    @Override
    public Specimen getTheBestSpecimen(List<Specimen> generation) {
        List<Specimen> copy = new ArrayList<>(generation);
        Collections.sort(copy);
        return copy.get(0);
    }

    @Override
    public String interpretThings(List<Integer> chromosome) {
        StringBuilder builder = new StringBuilder();
        int index = 0;
        for (int gen : chromosome) {
            if(gen == 1) {
                builder.append(bagPackItems.get(index).getId())
                        .append(". ")
                        .append(bagPackItems.get(index).getName())
                        .append("\n");
            }
            index++;
        }
        return builder.toString();
    }

    @Override
    public List<Specimen> createNewGeneration(List<Specimen> generation, List<Specimen> parents, List<Specimen> kids) {
        List<Specimen> copyOfList = new ArrayList<>(generation);
        copyOfList.removeAll(parents);
        Collections.sort(copyOfList);
        killSpecimen(copyOfList, kids.size());
        copyOfList.addAll(kids);
        copyOfList.addAll(parents);

        return copyOfList;
    }

    private void killSpecimen(List<Specimen> generation, int count) {
        for (int i = 0; i < count; i++) {
            generation.remove(generation.size()-1);
        }
    }

    private List<Specimen> onePointGenCross(List<Specimen> parents) {
        List<Specimen> newGeneration = new ArrayList<>();
        int size = parents.size();
        for (int i = 0; i < size / 2; i++) {
            List<Specimen> selectedParents = getRandomParents(parents);
            newGeneration.addAll(inheritedSingleChromosome(selectedParents.get(0).getGens(), selectedParents.get(1).getGens()));
        }
        mutationChance(newGeneration);

        return newGeneration;
    }

    private List<Specimen> getRandomParents(List<Specimen> parents) {
        List<Specimen> selectedParents = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            int randomIndex = new Random().nextInt(parents.size());
            Specimen randomElement = parents.get(randomIndex);
            parents.remove(randomIndex);
            selectedParents.add(randomElement);
        }

        return selectedParents;
    }

    private List<Specimen> inheritedSingleChromosome(List<Integer> parent1, List<Integer> parent2) {
        int barrier = random(1);
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

    private List<Integer> getRange(List<Integer> list, int start, int end) {
        List<Integer> newList = new ArrayList<>();
        for (int i = start; i < end; i++) {
            newList.add(list.get(i));
        }

        return newList;
    }

    private int random(int min) {
        return new Random().nextInt(26 - min) + min;
    }

    private void mutationChance(List<Specimen> newGeneration) {
        if((0.0 + (1) * new Random().nextDouble()) < mutation.getProbability() && mutation.isEnable()) {
            logger.info("The gene has mutated");
            Random rand = new Random();
            newGeneration.get(rand.nextInt(newGeneration.size())).reverseSingleGen(random(1));
        }
    }

    private List<Specimen> doublePointGenCross(List<Specimen> parents) {
        List<Specimen> newGeneration = new ArrayList<>();
        int size = parents.size();
        for (int i = 0; i < size / 2; i++) {
            List<Specimen> selectedParents = getRandomParents(parents);
            newGeneration.addAll(inheritedDoubleChromosome(selectedParents.get(0).getGens(), selectedParents.get(1).getGens()));
        }
        mutationChance(newGeneration);

        return newGeneration;
    }

    private List<Specimen> inheritedDoubleChromosome(List<Integer> parent1, List<Integer> parent2) {
        int barrier1 = random(1);
        int barrier2 = random(barrier1);
        List<Specimen> result = new ArrayList<>();
        result.add(createSpecimen(parent1, parent2, barrier1, barrier2));
        result.add(createSpecimen(parent2, parent1, barrier1, barrier2));

        return result;
    }

    private Specimen createSpecimen(List<Integer> parent1, List<Integer> parent2, int barrier1, int barrier2) {
        List<Integer> chromosome = new ArrayList<>();
        if((0.0 + (1) * new Random().nextDouble()) > crossOver.getProbability()) {
            if(new Random().nextBoolean()) {
                chromosome.addAll(new ArrayList<>(parent1));
            } else {
                chromosome.addAll(new ArrayList<>(parent2));
            }
        } else {
            chromosome.addAll(getRange(parent1, 0, barrier1));
            chromosome.addAll(getRange(parent1, barrier1, barrier2));
            chromosome.addAll(getRange(parent2, barrier2, 26));
        }

        return new Specimen(chromosome);
    }

}
