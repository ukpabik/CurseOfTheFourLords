package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import game.Panel;

//different keys for different chests
public class ObjectGKey extends Entity
{
    public static final String objName = "Golden Key";
    public ObjectGKey(Panel p)
    {
        super(p);
        name = objName;
        down1 = setup("/objects/GoldenKey", p.tileSize, p.tileSize);
        
        description = "{" + name + "}\n A rusted old key.";
        
        solid.x = 5;
        solid.y = 5;
        price = 5;
        stackable = true;
    }
}