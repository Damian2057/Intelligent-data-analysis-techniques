package p.lodz.pl;

import lombok.extern.java.Log;
import p.lodz.pl.DE.DifferentialAlgorithm;
import p.lodz.pl.DE.DifferentialEvolution;
import p.lodz.pl.PSO.PSO;
import p.lodz.pl.PSO.PSOAlgorithm;
import p.lodz.pl.chart.ChartGenerator;
import p.lodz.pl.chart.DataSet;
import p.lodz.pl.config.Config;
import p.lodz.pl.config.Properties;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Log
public class Comparison {

    private final Properties properties = Config.getProperties();
    private final List<Algorithm<?>> results = new ArrayList<>();

    public void compare() {
        createTasks();

        log.info("\n========Data collecting========");
        Algorithm<?> de = getBestDEResult();
        List<DataSet> deBestResult = de.getDataSets();
        List<DataSet> deAvgResult = getAvgDeResult();

        Algorithm<?> pso = getBestPSOResult();
        List<DataSet> psoBestResult = pso.getDataSets();
        List<DataSet> psoAvgResult = getAvgPSOResult();


        log.info("\n========Generating charts========");

//        String titleDE = String.format("DifferentialEvolution for %s runs", results.size());
//        ChartGenerator chartGeneratorDE = new ChartGenerator(titleDE, deBestResult,
//                deAvgResult);
//        chartGeneratorDE.pack();
//        chartGeneratorDE.setVisible(true);
//        String titlePSO = String.format("Particle Swarm Optimization for %s runs", results.size());
//        ChartGenerator chartGeneratorPSO = new ChartGenerator(titlePSO, psoBestResult,
//                psoAvgResult);
//        chartGeneratorPSO.pack();
//        chartGeneratorPSO.setVisible(true);

        String title = String.format("Comparison of two algorithms for %s runs", results.size());
        ChartGenerator chartCompare = new ChartGenerator(title,
                deBestResult,
                deAvgResult,
                psoBestResult,
                psoAvgResult);
        chartCompare.pack();
        chartCompare.setVisible(true);

        log.info(String.format("\n========Summary========\nPSO result: %s\nDE result: %s",
                pso.getBest().getBestAdaptationValue(),
                de.getBest().getAdaptationValue()));
    }

    private void createTasks() {
        try {
            List<Future<?>> tasks = new ArrayList<>();
            for (int i = 0; i < properties.getStartTimes(); i++) {
                Future<?> deTask = new DifferentialEvolution().start();
                Future<?> psoTask = new PSOAlgorithm().start();
                tasks.add(deTask);
                tasks.add(psoTask);

            }

            while (!tasks.stream().allMatch(Future::isDone)) {
            }

            for (Future<?> f : tasks) {
                Algorithm<?> result = (Algorithm<?>) f.get();
                results.add(result);
            }
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Algorithm<?> getBestDEResult() {
        return results.stream()
                .filter(x -> x instanceof DifferentialAlgorithm)
                .min(Comparator.comparingDouble(x -> x.getBest().getAdaptationValue()))
                .orElseThrow(NoSuchElementException::new);
    }

    private List<DataSet> getAvgDeResult() {
        int minIndex = results.stream()
                .filter(x -> x instanceof DifferentialAlgorithm)
                .mapToInt(x -> x.getDataSets().size())
                .min().orElse(0);
        List<DataSet> avgSet = new ArrayList<>();
        for (int i = 0; i < minIndex; i++) {
            int finalI = i;
            double value = results.stream()
                    .filter(x -> x instanceof DifferentialAlgorithm)
                    .mapToDouble(x -> x.getDataSets().get(finalI).getAvgAdaptation())
                    .average().orElse(0.0);
            avgSet.add(new DataSet(i, value, -1));
        }

        return avgSet;
    }

    private Algorithm<?> getBestPSOResult() {
        return results.stream()
                .filter(x -> x instanceof PSO)
                .min(Comparator.comparingDouble(x -> x.getBest().getAdaptationValue()))
                .orElseThrow(NoSuchElementException::new);
    }

    private List<DataSet> getAvgPSOResult() {
        int minIndex = results.stream()
                .filter(x -> x instanceof PSO)
                .mapToInt(x -> x.getDataSets().size())
                .min().orElse(0);
        List<DataSet> avgSet = new ArrayList<>();
        for (int i = 0; i < minIndex; i++) {
            int finalI = i;
            double value = results.stream()
                    .filter(x -> x instanceof PSO)
                    .mapToDouble(x -> x.getDataSets().get(finalI).getAvgAdaptation())
                    .average().orElse(0.0);
            avgSet.add(new DataSet(i, value, -1));
        }

        return avgSet;
    }

}
