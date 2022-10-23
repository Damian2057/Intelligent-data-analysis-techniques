package com.metaheuristics.readers.fileoperation;

import com.metaheuristics.simulation.model.Specimen;

import java.util.List;

public interface FileOperation {

    /**
     * @param round number of actual round
     * @param generation whole generation to save
     */
    void writeData(int round, List<Specimen> generation);
}
