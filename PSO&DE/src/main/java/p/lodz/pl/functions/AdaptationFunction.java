package p.lodz.pl.functions;

import java.util.List;

public interface AdaptationFunction {
    static double function(String number, List<Double> x) {
        switch (number){
            case "1" -> Functions.sphereFunction(x);
            case "2" -> Functions.secondFunction(x);
            case "3" -> Functions.cigarFunction(x);
            case "4" -> Functions.rastriginFunction(x);
        };
        throw new IllegalArgumentException("invalid function number");
    }
}
