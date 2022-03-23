package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Bat extends Actor {

    public Bat(Cell cell) {
        super(cell);
        this.setStrength(1);
        this.setHealth(9);
    }

    @Override
    public String getTileName() {
        return "bat";
    }
}