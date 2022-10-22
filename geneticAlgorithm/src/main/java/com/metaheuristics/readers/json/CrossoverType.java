package com.metaheuristics.readers.json;

public enum CrossoverType {
    ONEPOINT(1),
    MULTIPOINT(3);

    private final int points;

    CrossoverType(int i) {
        this.points = i;
    }

    public int getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return "CrossoverType{" +
                "points=" + points +
                '}';
    }
}
