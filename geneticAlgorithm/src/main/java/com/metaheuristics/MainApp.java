package com.metaheuristics;

import com.metaheuristics.readers.json.JsonReader;
import com.metaheuristics.simulation.Algorithm;
import com.metaheuristics.simulation.AlgorithmImpl;

import java.util.ArrayList;
import java.util.List;

public class MainApp {

    private static final int startTimes = JsonReader.getProgramStartTimes();

    public static void main(String[] args) {
        List<Thread> threadsAlgorithm = new ArrayList<>();
        for (int i = 0; i < startTimes; i++) {
            Algorithm algorithm = new AlgorithmImpl();
            threadsAlgorithm.add(new Thread(algorithm));
        }
        for (Thread thread : threadsAlgorithm) {
            thread.start();
        }
    }

}
