package game;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sounds
{
    
    //OBJECTS
	Clip clip;
    URL soundURL[] = new URL[30];
    FloatControl fc;
    
    //VOLUME VARIABLES
    int vScale = 3;
    float volume;
    
    public Sounds()
    {
        //music
        soundURL[0] = getClass().getResource("/sounds/npcmusic.wav");
        soundURL[1] = getClass().getResource("/sounds/finalboss.wav");
        
        //sound effects
        soundURL[2] = getClass().getResource("/sounds/itempickup.wav");
        soundURL[3] = getClass().getResource("/sounds/opendoor.wav");
        soundURL[4] = getClass().getResource("/sounds/menu select.wav");
        soundURL[5] = getClass().getResource("/sounds/select sound.wav");
        soundURL[6] = getClass().getResource("/sounds/upgrade.wav");
        soundURL[7] = getClass().getResource("/sounds/attack variation.wav");
        soundURL[8] = getClass().getResource("/sounds/hit 2 medio attack.wav");
        soundURL[9] = getClass().getResource("/sounds/Dialog voice 1.wav");
        soundURL[10] = getClass().getResource("/sounds/death.wav");
        soundURL[11] = getClass().getResource("/sounds/teleport.wav");
        soundURL[12] = getClass().getResource("/sounds/monster_attack.wav");
        soundURL[13] = getClass().getResource("/sounds/block.wav");
        soundURL[14] = getClass().getResource("/sounds/Dialog voice 3.wav");
        //ADD SOUND EFFECTS FOR BATTLE
        
    }
    
    public void setFile(int i)
    {
        try
        {
            //format to open an audio file in java
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            //PASS VALUE TO CLIP TO CHANGE VOLUME
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            checkVolume();
        }catch(Exception e)
        {
            
        }
        
    }
    public void play()
    {
        clip.start();
    }
    public void loop()
    {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop()
    {
        clip.stop();
    }
    
    //Volume ranges FROM -80f to 6f
    public void checkVolume() {
        switch(vScale) {
            case 0: volume = -80f; break;
            case 1: volume = -20f; break;
            case 2: volume = -12f; break;
            case 3: volume = -5f; break;
            case 4: volume = 1f; break;
            case 5: volume = 6f; break;
        }
        fc.setValue(volume);
    }
}
