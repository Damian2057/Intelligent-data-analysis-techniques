package p.lodz.pl.DE;

import p.lodz.pl.DE.factory.SpecimenFactory;
import p.lodz.pl.DE.model.Specimen;
import p.lodz.pl.config.Config;
import p.lodz.pl.config.DEProperties;
import p.lodz.pl.functions.AdaptationFunction;
import p.lodz.pl.functions.Functions;

import java.util.List;

import static p.lodz.pl.enums.Const.ACCURACY;
import static p.lodz.pl.enums.Const.ITERATION;

public class DifferentialEvolution implements DifferentialAlgorithm {

    private final DEProperties properties = Config.getDEProperties();
    private final SpecimenFactory factory;
    private final Functions function;

    private List<Specimen> generation;
    private Specimen bestSpecimen;

    public DifferentialEvolution() {
        this.function = new AdaptationFunction(properties.getAdaptationFunction());
        this.factory = new SpecimenFactory();
        this.generation = factory.createGeneration();
    }

    @Override
    public void run() {
        if (ITERATION.getName().equals(properties.getStopCondition())) {
            for (int i = 0; i < properties.getNumber(); i++) {

            }
        } else if (ACCURACY.getName().equals(properties.getStopCondition())) {
            int repetitionCounter = 0;
            while (repetitionCounter < 100) {
                Specimen bestInIteration = null;


                if (bestSpecimen.getAdaptationValue() - bestInIteration.getAdaptationValue() < properties.getNumber()) {
                    repetitionCounter++;
                } else {
                    repetitionCounter = 0;
                }
            }
        } else {
            throw new IllegalArgumentException("invalid stop condition of the algorithm");
        }
    }


}
