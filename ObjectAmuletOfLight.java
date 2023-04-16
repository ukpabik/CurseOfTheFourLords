package object;

import entity.Entity;
import game.Panel;

public class ObjectAmuletOfLight extends Entity
{
    public static final String objName = "Amulet of Light";
    public ObjectAmuletOfLight(Panel p) {
        super(p);
        
        type = type_light;
        name = objName;
        description = "{" + name + "}\n A shiny amulet, capable \nof emitting light.";
        down1 = setup("/objects/amuletoflight", p.tileSize, p.tileSize);
        price = 10;
        lightRadius = 250;
        
    }
}
