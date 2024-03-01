package sounds;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class EatSound {
    private Clip clip;
    public  EatSound() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/audio/eat.wav"));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            System.out.println("IO error");
        }
    }
}