package object;

import entity.Entity;
import game.Panel;

public class ObjectIronShield extends Entity
{
    public static final String objName = "Iron Shield";
    public ObjectIronShield(Panel p)
    {
        super(p);

        name = objName;
        down1 = setup("/objects/ironshield-d1", p.tileSize, p.tileSize);
        defenseValue = 4;
        description = "{" + name + "}\n A shield fit for a warrior.";
        
        type = type_shield;
        price = 11;
        knockbackPower = 4;
    }
}