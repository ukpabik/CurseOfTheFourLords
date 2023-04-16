package object;

import entity.Entity;
import game.Panel;

public class ObjectTitaniumSword extends Entity
{
    public static final String objName = "Titanium Sword";

    public ObjectTitaniumSword(Panel p)
    {
        super(p);

        name = objName;
        down1 = setup("/objects/titaniumsword", p.tileSize, p.tileSize);
        attackValue = 4;
        description = "{" + name + "}\n A sword forged in the\n deep caverns.";
        
        type = type_sword;
        
        //CHANGE DEPENDING ON CURRENT WEAPON
        attackArea.width = 36;
        attackArea.height = 36;
        price = 14;
        knockbackPower = 3;
        
        criticalRate = 7;
        attackDuration1 = 9;
        attackDuration2 = 19;
    }

}
