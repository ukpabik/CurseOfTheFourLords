package object;

import entity.Entity;
import game.Panel;

public class ObjectWoodShield extends Entity
{
    public static final String objName = "Wood Shield";
    public ObjectWoodShield(Panel p)
    {
        super(p);

        name = objName;
        down1 = setup("/objects/woodshield-d1", p.tileSize, p.tileSize);
        defenseValue = 1;
        description = "{" + name + "}\n A battle-aged shield.";
        
        type = type_shield;
        price = 3;
        knockbackPower = 2;
    }

}
