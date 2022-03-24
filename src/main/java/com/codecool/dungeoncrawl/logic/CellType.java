package com.codecool.dungeoncrawl.logic;

public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    WALL("wall"),
    WALL1("wall1"),
    WALL2("wall2"),
    WALL3("wall3"),
    WALL4("wall4"),
    WALL5("wall5"),
    WALL6("wall6"),
    WALL7("wall7"),
    WALL8("wall8"),
    PRISONGRATES("prisonGrates"),
    GRATES("grates"),
    BONES("bones"),
    DOOR("closed door"),
    OPEN_DOOR("open door"),
    CANDLESTICK("candlestick"),
    DEAD("DEAD"),
    POTION("potion"),
    DEADPLAYER("dead player");


    private final String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }

}
