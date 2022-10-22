package com.metaheuristics.readers.json;

public enum SelectionType {
    ROULETTE("Roulette"),
    RANKING("Ranking");

    private final String ranking;
    SelectionType(String ranking) {
        this.ranking = ranking;
    }

    public String getRanking() {
        return ranking;
    }

    @Override
    public String toString() {
        return "SelectionType{" +
                "ranking='" + ranking + '\'' +
                '}';
    }
}
