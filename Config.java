package game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Config
{
    Panel p;
    
    public Config(Panel p) {
        this.p = p;
    }
    public void saveConfig() {
        try
        {
            BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));
            
            //MUSIC VOLUME 
            bw.write(String.valueOf(p.music.vScale));
            bw.newLine();
            
            //SOUND EFFECT VOLUME
            bw.write(String.valueOf(p.soundEffect.vScale));
            bw.newLine();
            
            bw.close();
            
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public void loadConfig() {
        try
        {
            BufferedReader br = new BufferedReader(new FileReader("config.txt"));
           
            //MUSIC
            String s = br.readLine();
            p.music.vScale = Integer.parseInt(s);
            
            //SOUND EFFECT
            s = br.readLine();
            p.soundEffect.vScale = Integer.parseInt(s);
            
            br.close();
            
            
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
