package com.example;

import lombok.Getter;

public class Patch {
    @Getter
    private final String name;
    @Getter
    private final int regionId;
    @Getter
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
