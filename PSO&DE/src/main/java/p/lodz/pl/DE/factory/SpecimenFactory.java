package p.lodz.pl.DE.factory;

import p.lodz.pl.DE.model.Specimen;
import p.lodz.pl.config.Config;
import p.lodz.pl.config.Properties;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpecimenFactory {

    private final int size;
    private final int dimension;
    private final double[] xRange;
    private final Random random = new Random();

    public SpecimenFactory() {
        Properties properties = Config.getProperties();
        this.size = properties.getDe().getPopulationSize();
        this.dimension = properties.getDimension();
        this.xRange = properties.getXRange();

    }

    public List<Specimen> createGeneration() {
        List<Specimen> generation = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            generation.add(new Specimen(generateChromosome()));
        }
        return generation;
    }

    private List<Double> generateChromosome() {
        return random.doubles(dimension, xRange[0], xRange[1])
                .boxed().toList();
    }
}
