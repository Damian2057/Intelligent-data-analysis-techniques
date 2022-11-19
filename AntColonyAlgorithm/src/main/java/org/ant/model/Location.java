package org.ant.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Location {
    private int id;
    private int x;
    private int y;
    private double partialProbability;
}
