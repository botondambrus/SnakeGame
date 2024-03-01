package sounds;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class GameOverSound {
    private Clip clip;
    public  GameOverSound() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/audio/gameover.wav"));
             clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            System.out.println("IO error");
        }
    }
    public void soundStart() {
        clip.start();
    }
}
