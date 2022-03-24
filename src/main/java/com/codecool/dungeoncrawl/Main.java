package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Player;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import javax.swing.text.html.ImageView;
import java.awt.*;


public class Main extends Application {
    GameMap map = MapLoader.loadMap();
    Canvas canvas = new Canvas(
            map.getWidth() * 0.5 * Tiles.TILE_WIDTH,
            map.getHeight() * 0.4 * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    Label inventory = new Label();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Stage startStage = new Stage();
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefWidth(300);
        anchorPane.setPrefHeight(300);

        Button startGameButton = new Button("Start");
        startGameButton.setLayoutX(140);
        startGameButton.setLayoutY(50);

        Label label = new Label("Gra dla Dwojga");
        label.setLayoutX(30);
        label.setLayoutY(30);
        label.setStyle("-fx-font-size: 30; -fx-background-color: lightblue");
        anchorPane.getChildren().add(label);
//        Image image1 = new Image(test.class.getResourceAsStream("src/main/resources/zajebana z neta.jpg"));


        startGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                startGame();
            }
        });

        anchorPane.getChildren().add(startGameButton);
        Scene scene = new Scene(anchorPane);
        startStage.setScene(scene);

        startStage.setTitle("Seba & Kuba project");
        startStage.show();
    }

    public void startGame() {
        Stage gameStage = new Stage();
        GridPane ui = new GridPane();
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));


        ui.add(new Label("Health: "), 0, 0);
        ui.add(healthLabel, 1, 0);
        ui.add(new Label("Inventory:"), 0, 12);
        ui.add(inventory, 13, 12);


        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene scene = new Scene(borderPane);
        gameStage.setScene(scene);
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);

        gameStage.setTitle("Dungeon Crawl");
        gameStage.show();
    }

    private void onKeyPressed(KeyEvent keyEvent) {
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
                map.getPlayer().move(1, 0);
                refresh();
                break;
            case E:
                map.getPlayer().pickUpTheItem();
                refresh();
                break;
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
                } else if (windowX < 0 || windowX >= map.getWidth()) {
                    Tiles.drawTile(context, () -> "empty", x, y);
                } else {
                    Cell cell = map.getCell(windowX, windowY);
                    if (cell.getActor() != null) {
                        Tiles.drawTile(context, cell.getActor(), x, y);
                    } else if (cell.getItem() != null) {
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
