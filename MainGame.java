package game;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
public class MainGame
{
    
    public static JFrame frame;
    public static void main(String[] args)
    {
        
        //REDUCES SCREEN TEARING THAT COMES FROM JFRAME USING DOUBLE BUFFERING
    	Toolkit.getDefaultToolkit().sync();
        
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setTitle("Curse of the Four Lords");
        Panel panel = new Panel();
        frame.add(panel);
        panel.config.loadConfig();
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        new MainGame().icon();
        panel.setGame();
        panel.startThread();
    }
    public void icon() {
    	try {
    	    frame.setIconImage(ImageIO.read(new File("res/bob-d1.png")));
    	}
    	catch (IOException e) {
    	    e.printStackTrace();
    	}
    }
}
