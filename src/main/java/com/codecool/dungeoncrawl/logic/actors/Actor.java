package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Drawable;

public abstract class Actor implements Drawable {
    private String name;
    private Cell cell;
    private int health;
    private int strength;

    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }


    public void move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        if (nextCell.getType() != CellType.WALL){
            if (!(nextCell.getActor() instanceof Actor)){
                cell.setActor(null);
                nextCell.setActor(this);
                cell = nextCell;
            }
        }
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

    public int getStrength() { return strength; }

    public void setStrength(int strength) { this.strength = strength; }


}
