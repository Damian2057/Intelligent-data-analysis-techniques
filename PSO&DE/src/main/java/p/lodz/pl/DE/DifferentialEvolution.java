package p.lodz.pl.DE;

import lombok.extern.java.Log;
import p.lodz.pl.BaseParams;
import p.lodz.pl.DE.factory.SpecimenFactory;
import p.lodz.pl.DE.model.Specimen;
import p.lodz.pl.chart.DataSet;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.Future;

import static p.lodz.pl.enums.Const.*;

@Log
public class DifferentialEvolution extends BaseParams implements DifferentialAlgorithm {

    private final List<Specimen> generation;
    private Specimen bestSpecimen;

    public DifferentialEvolution() {
        super();
        SpecimenFactory factory = new SpecimenFactory();
        this.generation = factory.createGeneration();
        calculateAdaptationForWholeGeneration();
    }

    public Future<DifferentialAlgorithm> start() {
        return executor.submit(() -> {
            log.info(String.format(ALG_START,
                    Thread.currentThread().getName()));

            if (ITERATION.getName().equals(properties.getStopCondition())) {

                for (int i = 0; i < properties.getNumber(); i++) {
                    differentialEvolution();
                    this.bestSpecimen = getBestSpecimen();
                    dataSets.add(new DataSet(i, getAvgAdaptation(), bestSpecimen.getAdaptationValue()));
                }

            } else if (ACCURACY.getName().equals(properties.getStopCondition())) {
                int repetitionCounter = 0;
                int index = 0;
                this.bestSpecimen = generation.get(0);
                double repetition = properties.getDe().getPopulationSize() * 0.5;

                while (repetitionCounter < repetition) {
                    differentialEvolution();
                    double previousBest = bestSpecimen.getAdaptationValue();
                    this.bestSpecimen = getBestSpecimen();
                    dataSets.add(new DataSet(index, getAvgAdaptation(), bestSpecimen.getAdaptationValue()));
                    if (previousBest - bestSpecimen.getAdaptationValue() < properties.getNumber()) {
                        repetitionCounter++;
                    } else {
                        repetitionCounter = 0;
                    }
                    index++;
                }

            } else {
                throw new IllegalArgumentException("invalid stop condition of the algorithm");
            }
            log.info(String.format(ALG_SOL, Thread.currentThread().getName(),
                    bestSpecimen.getAdaptationValue(), dataSets.size()));

            return this;
        });
    }

    @Override
    public List<DataSet> getDataSets() {
        return dataSets;
    }

    @Override
    public Specimen getBest() {
        return bestSpecimen;
    }

    private void differentialEvolution() {
        Specimen baseVector = getBase();
        for (int j = 0; j < properties.getDe().getPopulationSize(); j++) {
            Specimen mutantSpecimen = generateMutant(generation.get(j), baseVector);
            Specimen testSpecimen = crossOver(generation.get(j), mutantSpecimen);
            calculateSingleAdaptation(testSpecimen);
            if (testSpecimen.getAdaptationValue() < generation.get(j).getAdaptationValue()) {
                generation.set(j, testSpecimen);
            }
        }
    }

    private double getAvgAdaptation() {
        return generation.stream()
                .mapToDouble(Specimen::getAdaptationValue)
                .average()
                .orElse(0.0);
    }

    private Specimen crossOver(Specimen specimen, Specimen mutantSpecimen) {
        if (RANDOM.getName().equals(properties.getDe().getCrossOver().getCrossoverType())) {
            return new Specimen(randomChain(specimen.getX(), mutantSpecimen.getX()));
        } else if (EXPONENTIAL.getName().equals(properties.getDe().getCrossOver().getCrossoverType())) {
            return new Specimen(exponentialChain(specimen.getX(), mutantSpecimen.getX()));
        } else {
            throw new IllegalArgumentException("invalid crossover type");
        }
    }

    public List<Double> exponentialChain(List<Double> parent, List<Double> mutant) {
        List<Double> chain = new ArrayList<>();
        int range = properties.getDe().getCrossOver().getNumberOfCopies();
        boolean flag = true;
        for (int i = 0; i < parent.size(); i += range) {
            if (flag) {
                chain.addAll(getRange(parent, i, i + range));
                flag = false;
            } else {
                chain.addAll(getRange(mutant, i, i + range));
                flag = true;
            }
        }
        return chain;
    }

    private List<Double> getRange(List<Double> list, int start, int end) {
        List<Double> newList = new ArrayList<>();
        for (int i = start; i < end; i++) {
            newList.add(list.get(i));
        }
        return newList;
    }

    private List<Double> randomChain(List<Double> parent, List<Double> mutant) {
        List<Double> chain = new ArrayList<>();
        for (int i = 0; i < properties.getDimension(); i++) {
            double x = random.nextDouble() < properties.getDe().getCrossOver().getCR() ? mutant.get(i) : parent.get(i);
            chain.add(x);
        }
        return chain;
    }

    private Specimen generateMutant(Specimen specimen, Specimen baseVector) {
        List<Specimen> copy = new ArrayList<>(generation);
        copy.removeAll(List.of(specimen, baseVector));
        List<Specimen> twoSpecimens = getRandomSpecimens(copy);
        final double[] xRanges = properties.getXRange();
        List<Double> mutatedChain = new ArrayList<>();
        for (int i = 0; i < properties.getDimension(); i++) {
            double v = baseVector.getX().get(i) + properties.getDe().getAmplificationFactor() *
                    (twoSpecimens.get(0).getX().get(i) - twoSpecimens.get(1).getX().get(i));
            if (v < xRanges[0]) {
                v = xRanges[0];
            } else if (v > xRanges[1]) {
                v = xRanges[1];
            }
            mutatedChain.add(v);
        }

        return new Specimen(mutatedChain);
    }

    private void calculateAdaptationForWholeGeneration() {
        generation.forEach(specimen -> specimen.setAdaptationValue(function.function(specimen.getX())));
    }

    private void calculateSingleAdaptation(Specimen specimen) {
        specimen.setAdaptationValue(function.function(specimen.getX()));
    }

    private List<Specimen> getRandomSpecimens(List<Specimen> partialGeneration) {
        List<Specimen> specimenList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            int index = random.nextInt(partialGeneration.size());
            Specimen specimen = partialGeneration.remove(index);
            specimenList.add(specimen);
        }

        return specimenList;
    }

    private Specimen getBase() {
        if (RANDOM.getName().equals(properties.getDe().getReproductionType())) {
            return generation.get(random.nextInt(generation.size()));
        } else if (BEST.getName().equals(properties.getDe().getReproductionType())){
            return getBestSpecimen();
        } else {
            throw new IllegalArgumentException("invalid stop condition of the algorithm");
        }
    }

    private Specimen getBestSpecimen() {
        return generation.stream()
                .min(Comparator.comparingDouble(Specimen::getAdaptationValue))
                .orElseThrow(NoSuchElementException::new);
    }
}
