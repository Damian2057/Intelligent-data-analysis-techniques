import org.testng.annotations.Test;
import p.lodz.pl.PSO.PSOAlgorithm;

public class PSOAlgorithmTest {
    @Test
    public void algorithmStartTest() {
        PSOAlgorithm algorithm = new PSOAlgorithm();
        algorithm.run();
    }
}
