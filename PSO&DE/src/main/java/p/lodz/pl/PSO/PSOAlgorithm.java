package p.lodz.pl.PSO;

import p.lodz.pl.PSO.factory.ParticleFactory;
import p.lodz.pl.PSO.model.Particle;
import p.lodz.pl.config.Config;
import p.lodz.pl.config.PSOProperties;
import org.apache.commons.lang3.Range;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class PSOAlgorithm implements PSO{

    private static final PSOProperties properties = Config.getPSOProperties();
    private static final int NUMBER_OF_ITERATION = Config.getPSOProperties().getNumberOfIteration();
    private static final int DISPLAY = Config.getPSOProperties().getDisplay();
    private static final Range<Double> xRange = Range.between(properties.getXDownBorder(), properties.getXUpBorder());
    private static final Range<Double> yRange = Range.between(properties.getYDownBorder(), properties.getYUpBorder());
    private final Expression expression = new ExpressionBuilder(properties.getAdaptationFunction())
            .variables("x", "y")
            .build();
    private final Logger logger = Logger.getLogger(PSO.class.getSimpleName());
    private final ParticleFactory factory = new ParticleFactory();
    private final List<Particle> swarm;
    private Particle bestParticle;
    private final Function<Particle, Particle> findTheBest = checked ->
            checked.getBestAdaptation() < bestParticle.getBestAdaptation() ? checked : bestParticle;

    public PSOAlgorithm() {
        logger.info("The simulation has started");
        swarm = factory.createSwarm(properties.getNumberOfParticles());
        bestParticle = swarm.get(0);
    }

    @Override
    public void run() {

    }
}
