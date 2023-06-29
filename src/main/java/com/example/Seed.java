package com.example;

import java.util.List;

public class Seed {
    final String name;
    final List<Payment> payments;
    public Seed(String name, List<Payment> payments) {
        this.name = name;
        this.payments = payments;
    }
}
