package org.ant;

import org.ant.exceptions.DataCollectingException;
import org.ant.simulation.AlgorithmImpl;

public class MainApp {
    public static void main(String[] args) {
        try {
            new Thread(new AlgorithmImpl()).start();
        } catch (Exception e) {

            throw new DataCollectingException("Error while collecting data");
        }
    }

}
