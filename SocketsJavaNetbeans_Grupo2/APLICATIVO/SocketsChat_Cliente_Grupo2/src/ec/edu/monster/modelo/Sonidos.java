package ec.edu.monster.modelo;


import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Grupo6
 */
public enum Sonidos {
    
    MessageReceive("/audio/notificacion.wav", false), // 
    FileSharing("/audio/notificacionArchivo.wav", false); //   
    private Clip clip;
    private boolean loop;
    
    Sonidos(String filename, boolean loop){
        try {
            this.loop = loop;
            URL url = this.getClass().getResource(filename);
            AudioInputStream audioIS = AudioSystem.getAudioInputStream(url);
            
            clip = AudioSystem.getClip();
            clip.open(audioIS);
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            System.out.println("[SoundEffect]" +e.getMessage());
        }
    }
    
    public void reproducir(){
        if(clip.isRunning()){
            clip.stop(); //  Stop Audio
        }
        //  Reset Audio from the beginning
        clip.setFramePosition(0);
        clip.start();
        //  Check if audio reproducir contineously
        if(loop){
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }
    
    public void detener(){
        if(clip.isRunning()){
            clip.stop(); //   Stop Audio
        }
    }
}