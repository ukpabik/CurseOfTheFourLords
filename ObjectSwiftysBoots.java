package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import game.Panel;

public class ObjectSwiftysBoots extends Entity
{
    public static final String objName = "Swiftys Boots";
    public ObjectSwiftysBoots(Panel p)
    {
        super(p);
        name = objName;
        
        down1 = setup("/objects/swiftysBoots", p.tileSize, p.tileSize);
    }
}
