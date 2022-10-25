package com.metaheuristics.chart;

import com.metaheuristics.simulation.model.Specimen;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class DataSet {
    private int round;
    private List<Specimen> list;
}
