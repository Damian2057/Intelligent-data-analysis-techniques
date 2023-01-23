package com.meta.model;

import lombok.Builder;
import lombok.Data;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location)) return false;
        Location location = (Location) o;
        return id == location.id &&
                Double.compare(location.x, x) == 0 &&
                Double.compare(location.y, y) == 0 &&
                Double.compare(location.demand, demand) == 0 &&
                Double.compare(location.readyTime, readyTime) == 0 &&
                Double.compare(location.dueDate, dueDate) == 0 &&
                Double.compare(location.serviceTime, serviceTime) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, x, y, demand, readyTime, dueDate, serviceTime);
    }
}
