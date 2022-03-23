package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Player extends Actor {


    public Player(Cell cell) {
        super(cell);
        this.setStrength(5);
        this.setHealth(15);
    }

    public String getTileName() {
        return "player";
    }
}
