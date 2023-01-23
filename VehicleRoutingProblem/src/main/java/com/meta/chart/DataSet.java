package com.meta.chart;

import com.meta.model.Ant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class DataSet {
    private int round;
    private List<Ant> list;
}