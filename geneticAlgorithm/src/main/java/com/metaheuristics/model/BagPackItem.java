package com.metaheuristics.model;

import lombok.*;

@Builder
@Getter
@Setter
public class BagPackItem {
    private int id;
    private String name;
    private int weight;
    private int price;
}
