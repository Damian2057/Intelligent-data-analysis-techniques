package com.metaheuristics.readers.Json;

import lombok.*;

import java.util.Optional;

@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Mutation {
    private Optional<Boolean> isEnable = Optional.empty();
    private Optional<Double> value = Optional.empty();
}
