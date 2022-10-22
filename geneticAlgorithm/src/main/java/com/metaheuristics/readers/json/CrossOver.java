package com.metaheuristics.readers.json;

import lombok.*;

@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CrossOver {
    private CrossoverType crossoverType;
    private double probability;
}
