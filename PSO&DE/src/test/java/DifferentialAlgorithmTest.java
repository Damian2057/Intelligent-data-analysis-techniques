import org.testng.annotations.Test;
import p.lodz.pl.DE.DifferentialAlgorithm;
import p.lodz.pl.DE.DifferentialEvolution;

public class DifferentialAlgorithmTest {

    @Test
    public void algorithmStartTest() {
        DifferentialAlgorithm algorithm = new DifferentialEvolution();
        algorithm.run();
    }
}
