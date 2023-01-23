package com.meta;

import com.meta.config.LocationReader;

public class MainApp {
    public static void main(String[] args) {
        var xd = LocationReader.getLocations("R101").get(0);
        System.out.println(xd);
    }
}
