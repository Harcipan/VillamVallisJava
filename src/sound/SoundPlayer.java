package sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * The SoundPlayer class is responsible for loading, playing, and controlling sound playback in the game.
 * It supports functionalities like playing sounds, adjusting volume, looping sounds, and stopping them.
 */
public class SoundPlayer {

    private Clip curClip;

    /**
     * Plays a sound from the specified file path.
     * If a sound is already playing, it stops the current sound before playing the new one.
     *
     * @param filePath the path to the sound file to be played.
     * @return the {@link Clip} object representing the sound being played.
     */
    public Clip playSound(String filePath) {
        // Stop the current sound if it's playing
        if (curClip != null && curClip.isRunning()) {
            stopSound(curClip);
        }

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

    /**
     * Sets the volume of the specified {@link Clip} object.
     * The volume range is from -80.0f (silent) to 6.0f (loudest).
     *
     * @param clip the {@link Clip} object whose volume will be adjusted.
     * @param volume the volume level, in decibels.
     */
    public void setVolume(Clip clip, float volume) {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(volume);
    }

    /**
     * Sets the volume of the currently playing sound.
     * The volume range is from -80.0f (silent) to 6.0f (loudest).
     *
     * @param volume the volume level, in decibels.
     */
    public void setVolume(float volume) {
        volume = volume - 80.0f;  // Normalize volume
        volume = Math.max(-80.0f, Math.min(volume, 6.0f)); // Ensure volume is within valid range

        FloatControl gainControl = (FloatControl) curClip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(volume);
    }

    /**
     * Gets the current volume of the currently playing sound.
     *
     * @return the current volume level in decibels.
     */
    public float getVolume() {
        FloatControl gainControl = (FloatControl) curClip.getControl(FloatControl.Type.MASTER_GAIN);
        return gainControl.getValue() + 80.0f;  // Normalize volume back to original range
    }

    /**
     * Stops the playback of the specified {@link Clip}.
     *
     * @param clip the {@link Clip} object to stop.
     */
    public void stopSound(Clip clip) {
        clip.stop();
    }

    /**
     * Loops the specified {@link Clip} continuously.
     *
     * @param clip the {@link Clip} object to loop.
     */
    public void loopSound(Clip clip) {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}
