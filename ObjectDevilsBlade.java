package object;

import entity.Entity;
import game.Panel;

public class ObjectDevilsBlade extends Entity
{

    public static final String objName = "Devils Blade";
    public ObjectDevilsBlade(Panel p)
    {
        super(p);

        name = objName;
        down1 = setup("/objects/devilsblade", p.tileSize, p.tileSize);
        attackValue = 7;
        description = "{" + name + "}\n The finest blade, made \nfrom the depths of hell.";
        
        type = type_sword;
        
        //CHANGE DEPENDING ON CURRENT WEAPON
        attackArea.width = 36;
        attackArea.height = 36;
        price = 50;
        knockbackPower = 4;
        criticalRate = 11;
        
        attackDuration1 = 8;
        attackDuration2 = 18;
    }

}