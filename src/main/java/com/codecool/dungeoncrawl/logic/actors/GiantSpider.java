package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class GiantSpider extends Actor {

    public GiantSpider(Cell cell) {
        super(cell);
        this.setStrength(4);
        this.setHealth(15);
    }

    @Override
    public String getTileName() {
        return "giantspider";
    }
}