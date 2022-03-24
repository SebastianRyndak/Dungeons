package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.Tiles;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.items.Item;
import javafx.scene.control.Alert;

import java.util.ArrayList;

import static com.codecool.dungeoncrawl.Tiles.*;

public class Player extends Actor {

    Alert alert = new Alert(Alert.AlertType.INFORMATION);
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
                battleWithSpider(dx,dy);
                playerDead();
            }else if(nextCell.getActor().getTileName().equals("bat")){
                battleWithSpider(dx, dy);
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
            if (cell.getItem() instanceof Item && cell.getItem().getTileName().equals("potion")) {
                checkIfYouHavePotion();
                cell.setItem(null);
            }else if(cell.getItem() instanceof Item && cell.getItem().getTileName().equals("sword +5 attack")) {
                checkIfYouHaveSword();
                insertIntoInventory(cell.getItem());
                cell.setItem(null);
                checkIfYouHaveTheKey();
            }else if(cell.getItem() instanceof Item && cell.getItem().getTileName().equals("key")){
                insertIntoInventory(cell.getItem());
                cell.setItem(null);
                checkIfYouHaveTheKey();
            }else if(cell.getItem() instanceof Item && cell.getItem().getTileName().equals("helmet")){
                insertIntoInventory(cell.getItem());
                cell.setItem(null);
                checkIfYouHavePotion();
                checkIfYouHaveHelmet();
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
        this.getCell().getActor().setHealth(getHealth() + 5);
    }

    private void checkIfYouHaveHelmet(){
        checkIfYouHavePotion();
        tileReplaceForPlayersHelmet();

    }

    private void checkIfYouHaveSword(){
        this.getCell().getActor().setStrength(getStrength() + 5);
        tileReplaceForPlayersSword();

    }

    private void battle(int dx, int dy) {
        System.out.println("skeleton");
        Cell nextCell = this.getCell().getNeighbor(dx, dy);
        this.setHealth(getHealth() - nextCell.getActor().getStrength());
        nextCell.getActor().setHealth(nextCell.getActor().getHealth() - this.getStrength());
        if (nextCell.getActor().getHealth() < 1) {
            nextCell.setActor(null);
        }
    }


    private void playerDead(){
        if(this.getCell().getActor().getHealth() < 1){
            tileReplaceForPlayer();
            alert.setTitle("Game Over");
            alert.setHeaderText("GAME OVER");
            alert.setContentText("Tak bardzo się starałeś lecz z gry wyleciałeś, na na na na na !!");
            alert.showAndWait();
            System.exit(0);
        System.out.println(nextCell.getActor().getHealth());
        if(nextCell.getActor().getHealth() < 1){
//            tileReplaceForSkeleton();
            nextCell.setType(null);
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
//            tileReplaceForBat();
            nextCell.setActor(null);
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
//            tileReplaceForSpider();
            nextCell.setActor(null);
        }
    }

    private void playerDead(){
        if(this.getCell().getActor().getHealth() < 1){
            tileReplaceForDeadPlayer();
            this.getCell().setType(CellType.DEADPLAYER);

        }
    }
}
