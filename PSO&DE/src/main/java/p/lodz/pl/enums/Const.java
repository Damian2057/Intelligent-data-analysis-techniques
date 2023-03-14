package p.lodz.pl.enums;

public enum Const {
    DE_CONFIG("src/main/resources/DEconfig.json"),
    PSO_CONFIG("src/main/resources/PSOconfig.json"),
    ITERATION("iteration"),
    ACCURACY("accuracy"),
    RANDOM("random"),
    BEST("best"),
    EXPONENTIAL("exponential");

    private final String name;

    Const(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
