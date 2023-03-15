import lombok.extern.java.Log;
import org.testng.annotations.Test;
import p.lodz.pl.DE.DifferentialAlgorithm;
import p.lodz.pl.DE.DifferentialEvolution;
import p.lodz.pl.DE.model.Specimen;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Log
public class DifferentialAlgorithmTest {

    @Test
    public void algorithmStartTest() {
        DifferentialAlgorithm alg = new DifferentialEvolution();
        alg.start();
    }

    @Test
    public void concurrentTest() throws InterruptedException, ExecutionException {
        List<Future<?>> futures = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Future<?> f = new DifferentialEvolution().start();
            futures.add(f);

        }
        log.info("All tasks are submitted.");

        while (!futures.stream().allMatch(Future::isDone)) {
        }

        for (Future<?> f : futures) {
            Object result = f.get();
            //log.info("Solution: " + ((Specimen) result).getAdaptationValue());
        }
        log.info("All tasks are completed.");
    }
}
