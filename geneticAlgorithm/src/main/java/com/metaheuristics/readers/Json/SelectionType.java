package com.metaheuristics.readers.Json;

public enum SelectionType {
    ROULETTE("Roulette"),
    RANKING("Ranking");

    private final String ranking;
    SelectionType(String ranking) {
        this.ranking = ranking;
    }

    @Override
    public String toString() {
        return "SelectionType{" +
                "ranking='" + ranking + '\'' +
                '}';
    }
}
