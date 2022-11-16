package org.ant.chart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.ant.simulation.model.Ant;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class DataSet {
    private int round;
    private List<Ant> list;
}