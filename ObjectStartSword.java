package object;

import entity.Entity;
import game.Panel;

public class ObjectStartSword extends Entity
{
    public static final String objName = "Starting Sword";

    public ObjectStartSword(Panel p)
    {
        super(p);

        name = objName;
        down1 = setup("/objects/startsworddown1", p.tileSize, p.tileSize);
        attackValue = 2;
        description = "{" + name + "}\n A battle-aged sword.";
        
        type = type_sword;
        
        //CHANGE DEPENDING ON CURRENT WEAPON
        attackArea.width = 36;
        attackArea.height = 36;
        price = 6;
        
        knockbackPower = 2;
        criticalRate = 4;
        attackDuration1 = 10;
        attackDuration2 = 20;
    }

}
