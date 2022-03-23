package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.items.Item;

import java.util.ArrayList;

public class Player extends Actor {
  
    public ArrayList<Item> inventory = new ArrayList<>();

    private boolean key = false;

    public Player(Cell cell) {
        super(cell);
        this.setStrength(5);
        this.setHealth(15);
    }

    public String getTileName() {
        return "player";
    }

    public boolean isKey() {
        return key;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public void insertIntoInventory(Item item){
        inventory.add(item);
    }

    public void move(int dx, int dy) {
        Cell nextCell = this.getCell().getNeighbor(dx, dy);
        if (nextCell.getType() != CellType.WALL){
            if (!(nextCell.getActor() instanceof Actor)){
                this.getCell().setActor(null);
                nextCell.setActor(this);
                this.setCell(nextCell);
                pickUpTheItem();
            }
        }
    }

    public void pickUpTheItem(){
        Cell cell = getCell();
        if (cell.getType() == CellType.FLOOR) {
            if (cell.getItem() instanceof Item) {
                insertIntoInventory(cell.getItem());
                cell.setItem(null);
            }
        }
    }

}
