package game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Toolbox
{
    public BufferedImage scaleImage(BufferedImage original, int width, int height)
    {
        //Creates a graphics2D to draw the buffered image
        BufferedImage scaledImage = new BufferedImage(width, height, 2);
        //saving what graph2 is drawing into scaledImage variable
        Graphics2D graph2 = scaledImage.createGraphics();
        graph2.drawImage(original,  0, 0, width, height, null);
        graph2.dispose();
        
        return scaledImage;
    }
}
