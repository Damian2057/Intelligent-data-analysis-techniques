package p.lodz.pl;

import lombok.extern.java.Log;
import p.lodz.pl.DE.DifferentialEvolution;
import p.lodz.pl.config.Config;
import p.lodz.pl.config.DEProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Log
public class Comparison {

    private final DEProperties properties = Config.getDEProperties();


    public void compare() {
        try {
            List<Future<?>> tasks = new ArrayList<>();
            for (int i = 0; i < properties.getStartTimes(); i++) {
                Future<?> deTask = new DifferentialEvolution().start();
//                Future<?> psoTask = new PSOAlgorithm().start();
                tasks.add(deTask);
//                tasks.add(psoTask);

            }
            log.info("All tasks are submitted!");
            while (!tasks.stream().allMatch(Future::isDone)) {
            }

            List<Algorithm> results = new ArrayList<>();
            for (Future<?> f : tasks) {
                Algorithm result = (Algorithm) f.get();
                results.add(result);
            }

        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
