package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Bat;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Random;


public class Main extends Application {
    GameMap map = MapLoader.loadMap();
    Canvas canvas = new Canvas(
            map.getWidth()*0.5 * Tiles.TILE_WIDTH,
            map.getHeight()*0.4 * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    Label inventory = new Label();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        GridPane ui = new GridPane();
//        GridPane ziemiak = new GridPane();
//        ziemiak.setPrefHeight(50);
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));


        ui.add(new Label("Health: "), 0, 0);
        ui.add(healthLabel, 1, 0);
        ui.add(new Label("Inventory:"),0,12);
        ui.add(inventory,13,12);




        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);
//        borderPane.setBottom(ziemiak);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);

        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        enemyMoves();
        resetMonsterMove();
        switch (keyEvent.getCode()) {
            case UP:
                map.getPlayer().move(0, -1);
                refresh();
                break;
            case DOWN:
                map.getPlayer().move(0, 1);
                refresh();
                break;
            case LEFT:
                map.getPlayer().move(-1, 0);
                refresh();
                break;
            case RIGHT:
                map.getPlayer().move(1,0);
                refresh();
                break;
            case E:
                map.getPlayer().pickUpTheItem();
                refresh();
                break;
        }
    }
    public int x, y = 0;
    private void enemyMoves() {
        for (Cell[] cells: map.getCells()) {
            for (Cell cell: cells) {
                if (cell.getActor() instanceof Skeleton || cell.getActor() instanceof Bat) {
                    if (cell.getActor().isCanMove()) {
                        Random r = new Random();
                        int direction = r.nextInt(2);
                        int upOrDown = r.nextInt(2);
                        if (direction == 0) {
                            y = 0;
                            x = (upOrDown == 0) ? -1 : 1;
                        } else {
                            x = 0;
                            y = (upOrDown == 0) ? -1 : 1;
                        }
                        cell.getActor().setCanMove(false);
                        cell.getActor().monsterMove(x, y);


                    }
                }
            }
        }
    }
    private void resetMonsterMove() {
        for (Cell[] cells: map.getCells()) {
            for (Cell cell: cells) {
                if (cell.getActor() instanceof Skeleton || cell.getActor() instanceof Bat) {
                    cell.getActor().setCanMove(true);
                }
            }
        }
    }

    private void refresh() {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < 43; x++) {
            for (int y = 0; y < 20; y++) {
                Player player = map.getPlayer();
                int playerPositionX = player.getX();
                int playerPositionY = player.getY();
                int windowX = playerPositionX + x - 11;
                int windowY = playerPositionY + y - 11;
                if (windowY < 0 || windowY >= map.getHeight()) {
                    Tiles.drawTile(context, () -> "empty", x, y);
                } else if (windowX < 0 || windowX >= map.getWidth()){
                    Tiles.drawTile(context, () -> "empty", x, y);
                }else {
                    Cell cell = map.getCell(windowX, windowY);
                    if (cell.getActor() != null) {
                        Tiles.drawTile(context, cell.getActor(), x, y);
                    } else if (cell.getItem() != null){
                        Tiles.drawTile(context, cell.getItem(), x, y);
                    } else if (cell.getType() == CellType.DEAD){
                        cell.setType(CellType.FLOOR);
                    }
                    else {
                        Tiles.drawTile(this.context, cell, x, y);
                    }
                }

            }
        }
        healthLabel.setText("" + map.getPlayer().getHealth());
        inventory.setText("" + map.getPlayer().getInventoryContent());
    }
}
