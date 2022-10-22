package com.metaheuristics.readers;

public enum Const {
    CONFIG("src/main/resources/config.json"),
    POPULATION("populationSize"),
    BACKPACK("backpackCapacity"),
    CROSSOVER("crossoverType"),
    SELECTION("selectionType"),
    MUTATION("mutation"),
    ENABLE("isEnable"),
    VALUE("value"),
    COMMA_DELIMITER(","),
    ITEMS("src/main/resources/objects.csv");


    private final String property;
    Const(String property) {
        this.property = property;
    }

    public String getProperty() {
        return property;
    }

    @Override
    public String toString() {
        return "Const{" +
                "property='" + property + '\'' +
                '}';
    }
}
