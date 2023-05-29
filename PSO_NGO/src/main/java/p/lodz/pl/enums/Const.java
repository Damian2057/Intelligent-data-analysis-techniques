package p.lodz.pl.enums;

public enum Const {

    CONFIG("src/main/resources/config.json"),
    ITERATION("iteration"),
    ACCURACY("accuracy");

    private final String name;

    Const(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
