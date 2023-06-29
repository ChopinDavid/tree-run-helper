package com.example;

import net.runelite.api.World;
import net.runelite.api.coords.WorldPoint;

public class Patch {
    private final String name;
    private final Seed bestSeed;
//    private final WorldPoint location;
    public Patch(String name/*, WorldPoint location*/, Seed bestSeed) {
        this.name = name;
        this.bestSeed = bestSeed;
    }
}
