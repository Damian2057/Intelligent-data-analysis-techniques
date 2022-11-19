package org.particle.chart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.particle.model.Particle;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class DataSet {
    private int round;
    private List<Particle> list;
}