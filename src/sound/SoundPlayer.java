package sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundPlayer {
    Clip curClip;
    public Clip playSound(String filePath) {
        Clip clip = null;
        try {
            File soundFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
            curClip = clip;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        return clip;
    }
    public void setVolume(Clip clip, float volume) {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(volume);
    }

    public void setVolume(float volume) {
        volume = volume-80;
        volume = Math.max(-80.0f, Math.min(volume, 6.0f));

        FloatControl gainControl = (FloatControl) curClip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(volume);
    }

    public float getVolume() {
        FloatControl gainControl = (FloatControl) curClip.getControl(FloatControl.Type.MASTER_GAIN);
        return gainControl.getValue()+80;
    }

    public void stopSound(Clip clip) {
        clip.stop();
    }

    public void loopSound(Clip clip) {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}
