package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.PlayMusic;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        PlayMusic.playMusic();
        Main.main(args);
    }
}
