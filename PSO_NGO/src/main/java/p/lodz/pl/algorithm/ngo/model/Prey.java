package p.lodz.pl.algorithm.ngo.model;

import lombok.Data;

import java.util.List;

@Data
public class Prey {
    private List<Double> xVector;

    public Prey(List<Double> x) {
        this.xVector = x;
    }
}
