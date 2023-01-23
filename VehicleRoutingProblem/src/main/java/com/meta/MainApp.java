package com.meta;

import com.meta.simulation.SimulationImp;

public class MainApp {
    public static void main(String[] args) {
        try {
            new Thread(new SimulationImp()).start();
        } catch (Exception e) {

            throw new RuntimeException("Error while collecting data");
        }
    }
}
