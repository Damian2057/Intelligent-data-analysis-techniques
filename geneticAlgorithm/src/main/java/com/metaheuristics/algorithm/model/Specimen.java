package com.metaheuristics.algorithm.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Specimen {
    private List<Integer> gens = new ArrayList<>();
}
