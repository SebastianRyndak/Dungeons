package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.Tiles;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.items.Item;
import javafx.stage.Stage;

import java.util.ArrayList;

import static com.codecool.dungeoncrawl.Tiles.*;

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

    public String getInventoryContent(){
        String itemsos = "";
        for (Item items: getInventory()) {
            itemsos += items.getTileName() + "\n";
        }
        return itemsos;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<Item> inventory) {
        this.inventory = inventory;
    }

    public void insertIntoInventory(Item item){
        inventory.add(item);
    }

    public void move(int dx, int dy) {
        Cell nextCell = this.getCell().getNeighbor(dx, dy);
        if (nextCell.getType() == CellType.FLOOR || nextCell.getType() == CellType.DOOR || nextCell.getType() == CellType.OPEN_DOOR || nextCell.getType() == CellType.DEAD){
            if(nextCell.getType() == CellType.DEAD && nextCell.getActor() instanceof Actor ){
                this.getCell().setActor(null);
                nextCell.setActor(this);
                this.setCell(nextCell);
            }
            if (!(nextCell.getActor() instanceof Actor || nextCell.getType() == CellType.DOOR || this.getCell().getType() == CellType.DEADPLAYER)){
                this.getCell().setActor(null);
                nextCell.setActor(this);
                this.setCell(nextCell);
            }
            if (nextCell.getType() == CellType.DOOR && key == true) {
                this.getCell().setActor(null);
                nextCell.setActor(this);
                this.setCell(nextCell);
                Tiles.tileReplace();
                this.getCell().setType(CellType.OPEN_DOOR);
            }if (nextCell.getActor().getTileName().equals("skeleton")){
                battleWithSkeleton(dx,dy);
                playerDead();
            }else if(nextCell.getActor().getTileName().equals("bat")){
                battleWithBat(dx, dy);
                playerDead();
            }else if(nextCell.getActor().getTileName().equals("giantspider")){
                battleWithSpider(dx, dy);
                playerDead();
            }
        }
    }


    public void pickUpTheItem(){
        Cell cell = getCell();
        if (cell.getType() == CellType.FLOOR) {
            if (cell.getItem() instanceof Item) {
                insertIntoInventory(cell.getItem());
                cell.setItem(null);
                checkIfYouHaveTheKey();
                checkIfYouHavePotion();
                checkIfYouHaveSword();
            }

        }

    }

    private boolean checkIfYouHaveTheKey(){
        for (Item item: inventory) {
            if (item.getTileName().equals("key")){
                return key=true;
            }
        }
        return key;
    }

    private void checkIfYouHavePotion(){
        for (Item item: inventory) {
            if (item.getTileName().equals("potion")){
                this.getCell().getActor().setHealth(getHealth() + 5);
            }
        }

    }

    private void checkIfYouHaveSword(){
        for (Item item: inventory) {
            if (item.getTileName().equals("sword")){
                this.getCell().getActor().setStrength(getStrength() + 5);
            }
        }

    }

    private void battleWithSkeleton(int dx, int dy){
        System.out.println("skeleton");
        Cell nextCell = this.getCell().getNeighbor(dx, dy);
        System.out.println(nextCell.getActor().getTileName());
        this.setHealth(getHealth() - nextCell.getActor().getStrength());
        System.out.println(nextCell.getActor().getStrength());
        nextCell.getActor().setHealth(nextCell.getActor().getHealth() - this.getStrength());
        System.out.println(nextCell.getActor().getHealth());
        if(nextCell.getActor().getHealth() < 1){
            tileReplaceForSkeleton();
            nextCell.setType(CellType.DEAD);
        }

    }

    private void battleWithBat(int dx, int dy) {
        System.out.println("nitoperek");
        Cell nextCell = this.getCell().getNeighbor(dx, dy);
        System.out.println(nextCell.getActor().getTileName());
        this.setHealth(getHealth() - nextCell.getActor().getStrength());
        System.out.println(nextCell.getActor().getStrength());
        nextCell.getActor().setHealth(nextCell.getActor().getHealth() - this.getStrength());
        System.out.println(nextCell.getActor().getHealth());
        if (nextCell.getActor().getHealth() < 1) {
            tileReplaceForBat();
            nextCell.setType(CellType.DEAD);
        }
    }

    private void battleWithSpider(int dx, int dy){
        System.out.println("nitoperek");
        Cell nextCell = this.getCell().getNeighbor(dx, dy);
        System.out.println(nextCell.getActor().getTileName());
        this.setHealth(getHealth() - nextCell.getActor().getStrength());
        System.out.println(nextCell.getActor().getStrength());
        nextCell.getActor().setHealth(nextCell.getActor().getHealth() - this.getStrength());
        System.out.println(nextCell.getActor().getHealth());
        if (nextCell.getActor().getHealth() < 1) {
            tileReplaceForSpider();
            nextCell.setType(CellType.DEAD);
        }
    }

    private void playerDead(){
        if(this.getCell().getActor().getHealth() < 1){
            tileReplaceForPlayer();
            this.getCell().setType(CellType.DEADPLAYER);

        }
    }

}
