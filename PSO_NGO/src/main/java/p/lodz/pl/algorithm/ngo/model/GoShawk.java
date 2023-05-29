package p.lodz.pl.algorithm.ngo.model;

import lombok.Data;
import p.lodz.pl.Adaptation;

@Data
public class GoShawk implements Adaptation, Cloneable {

    @Override
    public double getAdaptationValue() {
        return 0;
    }

    @Override
    public double getBestAdaptationValue() {
        return 0;
    }
}
