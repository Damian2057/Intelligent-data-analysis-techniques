package com.metaheuristics.readers.json;

import lombok.*;

@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Mutation {
    private boolean isEnable;
    private double probability;
}
