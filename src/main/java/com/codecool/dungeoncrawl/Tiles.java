package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Drawable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class Tiles {
    public static int TILE_WIDTH = 32;

    private static Image tileset = new Image("/tiles.png", 543 * 2, 543 * 2, true, false);
    private static Map<String, Tile> tileMap = new HashMap<>();
    public static class Tile {
        public final int x, y, w, h;
        Tile(int i, int j) {
            x = i * (TILE_WIDTH + 2);
            y = j * (TILE_WIDTH + 2);
            w = TILE_WIDTH;
            h = TILE_WIDTH;
        }
    }

    static {
        tileMap.put("empty", new Tile(0, 0));
        tileMap.put("stairs", new Tile(2, 6));
        tileMap.put("wall", new Tile(10, 17));
        tileMap.put("wall1",new Tile(18,0));
        tileMap.put("wall2",new Tile(19,0));
        tileMap.put("wall3",new Tile(20,0));
        tileMap.put("wall4",new Tile(18,1));
        tileMap.put("wall5",new Tile(20,1));
        tileMap.put("wall6",new Tile(18,2));
        tileMap.put("wall7",new Tile(19,2));
        tileMap.put("wall8",new Tile(20,2));
        tileMap.put("corner1",new Tile(18,3));
        tileMap.put("corner2",new Tile(19,3));
        tileMap.put("corner3",new Tile(18,4));
        tileMap.put("corner4",new Tile(19,4));
        tileMap.put("prisonGrates",new Tile(13,11));
        tileMap.put("grates",new Tile(5,5));
        tileMap.put("bones",new Tile(0,15));
        tileMap.put("floor", new Tile(2, 0));
        tileMap.put("player", new Tile(27, 0));
        tileMap.put("skeleton", new Tile(29, 6));
        tileMap.put("sword +5 attack", new Tile(0, 31));
        tileMap.put("key", new Tile(16, 23));
        tileMap.put("bat", new Tile(26, 8));
        tileMap.put("giantspider", new Tile(28, 5));
        tileMap.put("closed door", new Tile(3, 9));
        tileMap.put("candlestick", new Tile(4,15));
        tileMap.put("potion", new Tile(17,25));
    }

    public static void tileReplace(){
        tileMap.put("closed door", new Tile(6, 9));
    }

    public static void tileReplaceForBat(){
        tileMap.put("bat", new Tile(17, 24));
    }

    public static void tileReplaceForSkeleton(){
        tileMap.put("skeleton", new Tile(17, 24));
    }

    public static void tileReplaceForSpider(){
        tileMap.put("giantspider", new Tile(17, 24));
    }

    public static void tileReplaceForPlayer(){
        tileMap.put("player", new Tile(18, 24));
    }


    public static void drawTile(GraphicsContext context, Drawable d, int x, int y) {
        Tile tile = tileMap.get(d.getTileName());
        context.drawImage(tileset, tile.x, tile.y, tile.w, tile.h,
                x * TILE_WIDTH, y * TILE_WIDTH, TILE_WIDTH, TILE_WIDTH);
    }
}
