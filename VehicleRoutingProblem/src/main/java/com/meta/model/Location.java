package com.meta.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Location {
    private int id;
    private double x;
    private double y;
    private double demand;
    private double readyTime;
    private double dueDate;
    private double serviceTime;
    private double partialProbability;
}
