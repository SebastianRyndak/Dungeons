package com.codecool.dungeoncrawl.logic;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class PlayMusic {

    public static void playMusic() throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        File file = new File("src/main/resources/music/Celestial_Aeon_Project_-_The_Saga_Begins (online-audio-converter.com).wav");
        Scanner scanner = new Scanner(System.in);

        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        String response = "N";

        while (!response.equals("Q")){
//            System.out.print("  Do you want your game to be accompanied by music? Y/N: ");
            System.out.println();
//            response = scanner.next();
//            response = response.toUpperCase();

            switch (response){
                case "Y" -> clip.start();
                case "S" -> clip.stop();
                case "R" -> clip.setMicrosecondPosition(0);
                case "N" -> clip.close();
            }
            break;
        }
    }
}
