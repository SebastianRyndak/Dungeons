package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Bat;
import com.codecool.dungeoncrawl.logic.actors.GiantSpider;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import com.codecool.dungeoncrawl.logic.items.*;

import java.io.InputStream;
import java.util.Scanner;

public class MapLoader {
    public static GameMap loadMap() {
        InputStream is = MapLoader.class.getResourceAsStream("/map.txt");
        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        scanner.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY);
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    switch (line.charAt(x)) {
                        case ' ':
                            cell.setType(CellType.EMPTY);
                            break;
                        case '#':
                            cell.setType(CellType.WALL);
                            break;
                        case '1':
                            cell.setType(CellType.WALL1);
                            break;
                        case '2':
                            cell.setType(CellType.WALL2);
                            break;
                        case '3':
                            cell.setType(CellType.WALL3);
                            break;
                        case '4':
                            cell.setType(CellType.WALL4);
                            break;
                        case '5':
                            cell.setType(CellType.WALL5);
                            break;
                        case '6':
                            cell.setType(CellType.WALL6);
                            break;
                        case '7':
                            cell.setType(CellType.WALL7);
                            break;
                        case '8':
                            cell.setType(CellType.WALL8);
                            break;
                        case 'p':
                            cell.setType(CellType.PRISONGRATES);
                            break;
                        case '!':
                            cell.setType(CellType.GRATES);
                            break;
                        case '/':
                            cell.setType(CellType.BONES);
                            break;
                        case '.':
                            cell.setType(CellType.FLOOR);
                            break;
                        case 's':
                            cell.setType(CellType.FLOOR);
                            new Skeleton(cell);
                            break;
                        case 'g':
                            cell.setType(CellType.FLOOR);
                            new GiantSpider(cell);
                            break;
                        case 'b':
                            cell.setType(CellType.FLOOR);
                            new Bat(cell);
                            break;
                        case '@':
                            cell.setType(CellType.FLOOR);
                            map.setPlayer(new Player(cell));
                            break;
                        case 'w':
                            cell.setType(CellType.FLOOR);
                            new Sword(cell);
                            break;
                        case 'k':
                            cell.setType(CellType.FLOOR);
                            new Key(cell);
                            break;
                        case 'd':
                            cell.setType(CellType.DOOR);
                            new Door(cell);
                            break;
                        case 'c':
                            cell.setType(CellType.CANDLESTICK);
                            break;
                        case 'h':
                            cell.setType(CellType.FLOOR);
                            new Potion(cell);
                            break;
                        case 'S':
                            cell.setType(CellType.STAIRS);
                            break;
                        case 't':
                            cell.setType(CellType.CORNER1);
                            break;
                        case 'y':
                            cell.setType(CellType.CORNER2);
                            break;
                        case 'u':
                            cell.setType(CellType.CORNER3);
                            break;
                        case 'i':
                            cell.setType(CellType.CORNER4);
                            break;
                        case 'e':
                            cell.setType(CellType.FLOOR);
                            new Helmet(cell);
                            break;
                        case 'z':
                            cell.setType(CellType.CHRISTMASTREE);
                            break;
                        case 'x':
                            cell.setType(CellType.CHRISTMASTREE1);
                            break;
                        case 'v':
                            cell.setType(CellType.BUSCH);
                            break;
                        case 'n':
                            cell.setType(CellType.BUSCH1);
                            break;
                        case 'm':
                            cell.setType(CellType.GRASS);
                            break;
                        case 'a':
                            cell.setType(CellType.ROAD);
                            break;
                        case 'f':
                            cell.setType(CellType.TREE);
                            break;
                        case 'j':
                            cell.setType(CellType.DUBLETREE);
                            break;
                        case 'l':
                            cell.setType(CellType.SIGN);
                            break;
                        case 'q':
                            cell.setType(CellType.HOUSE);
                            break;
                        case 'r':
                            cell.setType(CellType.FIREPIT);
                            break;
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }

}
