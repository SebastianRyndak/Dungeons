package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Bat;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.Random;




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

    public void initialize(){
        Thread movement = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.currentThread().sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    enemyMoves();
                    refresh();
                }
            }
        });
        movement.start();

    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        Stage startStage = new Stage();
        AnchorPane anchorPane = new AnchorPane();
        ImageView backgroundImage = new ImageView(new Image("asdsad.png"));
        backgroundImage.setFitWidth(400);
        backgroundImage.setFitHeight(600);


        anchorPane.setPrefWidth(400);
        anchorPane.setPrefHeight(400);

        Button startGameButton = new Button("Start");
        startGameButton.setLayoutX(130);
        startGameButton.setLayoutY(300);
        startGameButton.setMinSize(140,25);


        Label label = new Label("In the Land of Thrillers");
        label.setLayoutX(50);
        label.setLayoutY(30);
        label.setStyle("-fx-font-size: 30");


        TextArea userName= new TextArea("Enter your name");
        userName.setMaxSize(160,20);
        userName.setLayoutX(120);
        userName.setLayoutY(200);


        startGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                startGame(userName.getText());
            }
        });

        anchorPane.getChildren().add(backgroundImage);
        anchorPane.getChildren().add(userName);
        anchorPane.getChildren().add(startGameButton);
        anchorPane.getChildren().add(label);
        Scene scene = new Scene(anchorPane);
        startStage.setScene(scene);

        startGameButton.setOnAction(event -> startGame(userName.getText()));

        startStage.setTitle("In the Land of Thrillers");
        startStage.show();
    }

    public void startGame(String userName) {
        Stage gameStage = new Stage();
        VBox ui = new VBox();
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));


        ui.getChildren().add(new Label("Player: "+ userName));
        ui.getChildren().add(new Label("Health: "));
        ui.getChildren().add(new Label("Inventory: "));
        ui.getChildren().add(inventory);


        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene scene = new Scene(borderPane);
        gameStage.setScene(scene);
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);

        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
        initialize();

    }



    private void onKeyPressed(KeyEvent keyEvent) {
        enemyMoves();

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
    public int x, y = 0;
    private void enemyMoves() {
        for (Cell[] cells: map.getCells()) {
            for (Cell cell: cells) {
                if (cell.getActor() instanceof Skeleton || cell.getActor() instanceof Bat) {
                    System.out.println(cell.getActor().isCanMove());
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

                        cell.getActor().monsterMove(x, y);

                    }
                }
            }
        }
        resetEnemyMove();
    }
    private void resetEnemyMove() {
        for (Cell[] cells: map.getCells()) {
            for (Cell cell: cells) {
                if (cell.getActor() instanceof Skeleton || cell.getActor() instanceof Bat) {
                  /*  cell.getActor().setCanMove(true);*/
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
