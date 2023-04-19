package p.lodz.pl.pso;

import lombok.extern.java.Log;
import p.lodz.pl.chart.DataSet;
import p.lodz.pl.pso.factory.ParticleFactory;
import p.lodz.pl.pso.model.Particle;

import java.util.List;
import java.util.concurrent.Future;

@Log
public class EPSOAlgorithm extends BaseParams implements PSO {

    public EPSOAlgorithm() {
        super();
        ParticleFactory factory = new ParticleFactory();

    }

    @Override
    public Future<PSO> start() {
        return executor.submit(() -> {
            log.info(String.format(ALG_START, Thread.currentThread().getName()));
            return this;
        });
    }

    @Override
    public List<DataSet> getDataSets() {
        return null;
    }

    @Override
    public Particle getBest() {
        return null;
    }
}
