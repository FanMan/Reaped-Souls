package Game;

import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Music {
	public Clip clip;
	
	public Music() {
		
	}
	
	/**
	 * looks for the location of the music file in the resources folder
	 */
	public void playMusic() {
		try {
			InputStream is = this.getClass().getResourceAsStream("/music/She - Fuse.wav");
			AudioInputStream audioInputStream = 
					AudioSystem.getAudioInputStream(is);
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			//clip.start();
		}catch(Exception ex) {
			System.out.println("Music File not found");
		}
	}
	
	/**
	 * set to play the music when this function is called
	 */
	public void play() {
		clip.setFramePosition(0);
		clip.start();
	}
	
	/**
	 * allows for the music to loop so it does not end
	 */
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	/**
	 * stops the music when this is called
	 */
	public void stop() {
		clip.stop();
	}
}
