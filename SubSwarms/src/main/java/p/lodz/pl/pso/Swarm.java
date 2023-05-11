package p.lodz.pl.pso;

import org.apache.commons.lang3.Range;
import p.lodz.pl.config.Config;
import p.lodz.pl.config.Properties;
import p.lodz.pl.functions.AdaptationFunction;
import p.lodz.pl.functions.Functions;
import p.lodz.pl.pso.factory.ParticleFactory;
import p.lodz.pl.pso.model.Particle;

import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Swarm {

    private static final Properties properties = Config.getProperties();
    private static final Range<Double> xRange = Range.between(properties.getXRange()[0], properties.getXRange()[1]);
    private final Random random = new Random();
    private final Functions function;
    private final List<Particle> swarm;
    private Particle lBest;

    public Swarm(int size) {
        ParticleFactory factory = new ParticleFactory();
        this.swarm = factory.createSwarm(size);
        this.function = new AdaptationFunction(properties.getAdaptationFunction());
        calculateAdaptation();

        Particle best = getBestParticle();
        swarm.remove(best);
        this.lBest = best;
    }

    public List<Particle> getSwarm() {
        return swarm;
    }

    public Particle getBestParticle() {
        return swarm.stream().min(Comparator.comparingDouble(Particle::getBestAdaptation))
                .orElseThrow(() -> {
                    throw new RuntimeException("Cannot find the best particle");
                });
    }

    public void setBestParticle() {
        Particle best = getBestParticle();
        if (best.getId() != lBest.getId()) {
            swarm.remove(best);
            swarm.add(lBest);
            this.lBest = best;
        }
    }

    public Particle getCurrentBestParticle() {
        return swarm.stream().min(Comparator.comparingDouble(Particle::getAdaptationValue))
                .orElseThrow(() -> {
                    throw new RuntimeException("Cannot find the best particle");
                });
    }

    public boolean removeParticle(Particle particle) {
        return swarm.remove(particle);
    }

    public boolean addParticle(Particle particle) {
        return swarm.add(particle);
    }

    public void calculateAdaptation() {
        swarm.forEach(particle -> particle.setAdaptationValue(function.function(particle.getXVector())));
        swarm.forEach(particle -> particle.setBestAdaptation(Math.min(particle.getAdaptationValue(), particle.getBestAdaptation())));
    }

    public void updateParticlePosition(Particle particle) {
        for (int i = 0; i < properties.getDimension(); i++) {
            double speed = calculateSpeed(particle, i);
            particle.getSpeed().set(i, speed);
            double newXPos = particle.getXVector().get(i) + particle.getSpeed().get(i);
            if (xRange.contains(newXPos)) {
                particle.getXVector().set(i, newXPos);
            } else {
                if (newXPos > properties.getXRange()[1]) {
                    particle.getXVector().set(i, properties.getXRange()[1]);
                }
                else {
                    particle.getXVector().set(i, properties.getXRange()[0]);
                }
            }
        }
    }

    private double calculateSpeed(Particle particle, int index) {
        double inertia = properties.getInertia() * particle.getSpeed().get(index);

        double socialComponent = socialAcceleration() * (lBest.getXVector().get(index) - particle.getXVector().get(index));
        double cognitiveComponent = cognitiveAcceleration() * (particle.getBestXVector().get(index) - particle.getXVector().get(index));
        return inertia + socialComponent + cognitiveComponent;
    }

    private double socialAcceleration() {
        return properties.getSocialConstant() * getLevelOfComponent();
    }

    private double cognitiveAcceleration() {
        return properties.getCognitiveConstant() * getLevelOfComponent();
    }

    private double getLevelOfComponent() {
        return 0 + (1) * random.nextDouble();
    }

    private double getAvgAdaptation() {
        return swarm.stream()
                .mapToDouble(Particle::getBestAdaptation)
                .average()
                .orElse(0.0);
    }
}
