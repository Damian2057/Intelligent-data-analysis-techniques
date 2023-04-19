package p.lodz.pl.functions;

import java.util.List;
import java.util.stream.IntStream;

public class AdaptationFunction implements Functions {

    private final int functionNumber;

    public AdaptationFunction(int functionNumber) {
        this.functionNumber = functionNumber;
    }

    @Override
    public double function(List<Double> x) {
        switch (functionNumber){
            case 1 -> {
                return AdaptationFunction.sphereFunction(x);
            }
            case 2 -> {
                return AdaptationFunction.secondFunction(x);
            }
            case 3 -> {
                return AdaptationFunction.cigarFunction(x);
            }
            case 4 -> {
                return AdaptationFunction.rastriginFunction(x);
            }
            case 5 -> {
                return AdaptationFunction.sumPower(x);
            }
        }
        throw new IllegalArgumentException("invalid function number");
    }

    private static double sphereFunction(List<Double> x) {
        return x.stream().mapToDouble(value -> Math.pow(value, 2)).sum();
    }

    private static double secondFunction(List<Double> x) {
        return IntStream.range(0, x.size())
                .mapToDouble(i -> Math.pow((x.get(i) - i), 2))
                .sum();
    }

    private static double cigarFunction(List<Double> x) {
        double sum = Math.pow(x.get(0), 2);
        sum *= Math.pow(10, 6);
        sum += IntStream.range(1, x.size())
                .mapToDouble(i -> Math.pow(x.get(i), 2))
                .sum();
        return sum;
    }

    private static double rastriginFunction(List<Double> x) {
        return x.stream().mapToDouble(aDouble -> Math.pow(aDouble, 2) - 10 * Math.cos(2 * Math.PI * aDouble) + 10)
                .sum();
    }

    private static double sumPower(List<Double> x) {
        return IntStream.range(0, x.size() - 1)
                .mapToDouble(i -> Math.abs(Math.pow(x.get(i), i)))
                .sum();
    }
}
