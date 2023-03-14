package p.lodz.pl.functions;

import java.util.List;
import java.util.stream.IntStream;

public class AdaptationFunction {

    public static double sphereFunction(List<Double> x) {
        return x.stream().mapToDouble(value -> Math.pow(value, 2)).sum();
    }

    public static double secondFunction(List<Double> x) {
        return IntStream.range(0, x.size())
                .mapToDouble(i -> Math.pow((x.get(i) - i), 2))
                .sum();
    }

    public static double cigarFunction(List<Double> x) {
        double sum = Math.pow(x.get(0), 2);
        sum *= Math.pow(10, 6);
        sum += IntStream.range(1, x.size())
                .mapToDouble(i -> Math.pow(x.get(i), 2))
                .sum();
        return sum;
    }

    public static double rastriginFunction(List<Double> x) {
        return x.stream().mapToDouble(aDouble -> Math.pow(aDouble, 2) - 10 * Math.cos(2 * Math.PI * aDouble) + 10)
                .sum();
    }
}
