package Game;

import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Music {
	public Clip clip;
	
	public Music() {
		
	}
	
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
	
	public void play() {
		clip.setFramePosition(0);
		clip.start();
	}
	
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void stop() {
		clip.stop();
	}
}
