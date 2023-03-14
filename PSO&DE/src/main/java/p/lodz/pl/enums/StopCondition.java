package p.lodz.pl.enums;

public enum StopCondition {
    ITERATION("iteration"),
    ACCURACY("accuracy");

    private final String name;

    StopCondition(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
