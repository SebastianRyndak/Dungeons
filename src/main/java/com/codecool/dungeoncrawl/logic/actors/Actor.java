package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Drawable;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.Sword;

import java.util.ArrayList;

public abstract class Actor implements Drawable {
    private String name;
    private Cell cell;
    private int health;
    private int strength;
    private boolean canMove;
    public boolean isCanMove() { return canMove; }


    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }



    public int getHealth() {
        return health;
    }

    public void setHealth(int health) { this.health = health; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Cell getCell() {
        return cell;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public int getStrength() { return strength; }

    public void setStrength(int strength) { this.strength = strength; }

    public void setCanMove(boolean canMove) { this.canMove = canMove; }

}
