package org.ant.factory;

import org.ant.model.Ant;

import java.util.ArrayList;
import java.util.List;

public class Factory {

    public List<Ant> createColony(int size) {
        List<Ant> colony = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            colony.add(new Ant());
        }
        return colony;
    }

}
