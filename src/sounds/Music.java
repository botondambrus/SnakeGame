package sounds;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Music {
    private Clip clip;

    public Music() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/audio/snake.wav"));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            System.out.println("IO error");
        }
    }

    public void soundStart() {
        clip.start();
    }

    public void soundStop() {
        clip.stop();
    }

}
