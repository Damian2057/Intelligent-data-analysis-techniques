package p.lodz.pl.PSO;

import p.lodz.pl.DE.model.DataSet;
import p.lodz.pl.DE.model.Specimen;
import p.lodz.pl.PSO.factory.ParticleFactory;
import p.lodz.pl.PSO.model.Particle;
import p.lodz.pl.config.Config;
import p.lodz.pl.config.PSOProperties;
import org.apache.commons.lang3.Range;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;
import p.lodz.pl.functions.AdaptationFunction;
import p.lodz.pl.functions.Functions;

import static p.lodz.pl.enums.Const.ACCURACY;
import static p.lodz.pl.enums.Const.ITERATION;

public class PSOAlgorithm implements PSO{

    private static final PSOProperties properties = Config.getPSOProperties();
    private static final int DISPLAY = Config.getPSOProperties().getDisplay();
    private static final Range<Double> xRange = Range.between(properties.getXDownBorder(), properties.getXUpBorder());
    private final Logger logger = Logger.getLogger(PSO.class.getSimpleName());
    private final ParticleFactory factory = new ParticleFactory();
    private final List<Particle> swarm;
    private Particle bestParticle;
    private final Functions function;
    private final Function<Particle, Particle> findTheBest = checked ->
            checked.getBestAdaptation() < bestParticle.getBestAdaptation() ? checked : bestParticle;

    public PSOAlgorithm() {
        logger.info("The simulation has started");
        this.function = new AdaptationFunction(properties.getAdaptationFunction());
        swarm = factory.createSwarm(properties.getNumberOfParticles());
        bestParticle = swarm.get(0);
    }

    public void start() {
        if (ITERATION.getName().equals(properties.getStopCondition())) {
            for (int i = 0; i < properties.getNumber(); i++) {

            }
        } else if (ACCURACY.getName().equals(properties.getStopCondition())) {
            int counter = 0;
            while (counter < properties.getNumber()) {
                counter++;
            }
        } else {
            throw new IllegalArgumentException("invalid stop condition of the algorithm");
        }
    }

    @Override
    public List<DataSet> getDataSets() {
        return null;
    }

    @Override
    public Specimen getBest() {
        return null;
    }
}
