package com.example;

public class Patch {
    private final String name;
    private final int regionId;
    private final int varbit;
    private final Seed bestSeed;
    public Patch(String name, int regionId, int varbit, Seed bestSeed) {
        this.name = name;
        this.bestSeed = bestSeed;
        this.regionId = regionId;
        this.varbit = varbit;
    }

    String configKey()
    {
        return regionId + "." + varbit;
    }
}
