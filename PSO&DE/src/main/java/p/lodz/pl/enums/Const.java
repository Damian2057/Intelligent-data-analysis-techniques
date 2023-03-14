package p.lodz.pl.enums;

public enum Const {
    DE_CONFIG("src/main/resources/DEconfig.json"),
    PSO_CONFIG("src/main/resources/PSOconfig.json");

    private final String name;

    Const(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
