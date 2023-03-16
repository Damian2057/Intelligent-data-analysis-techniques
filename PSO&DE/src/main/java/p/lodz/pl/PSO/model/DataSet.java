package p.lodz.pl.PSO.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DataSet {
    private int round;
    private double avgAdaptation;
    private double bestAdaptation;
}
