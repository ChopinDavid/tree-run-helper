package com.example;

public class Payment {
    final int itemId;
    final String name;
    final int amount;
    public Payment(int itemId, String name, int amount) {
        this.itemId = itemId;
        this.name = name;
        this.amount = amount;
    }
}
