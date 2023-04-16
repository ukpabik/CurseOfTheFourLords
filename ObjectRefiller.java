package object;

import entity.Entity;
import game.Panel;

public class ObjectRefiller extends Entity
{
    public static final String objName = "Refiller";
    Panel p;
    
    public ObjectRefiller(Panel p)
    {
        super(p);
        this.p = p;
        
        type = type_consumable;
        name = objName;
        value = 5;
        down1 = setup("/objects/projectile_full", p.tileSize, p.tileSize);
        description = "{" + name + "}\nRestores " + value + " health\n and " + value + " projectiles.";
        price = 4;
        stackable = true;
        setDialogue();
    }
    
    public void setDialogue() {
        dialogues[0][0] = "You consume the " + name + "!\n"
            + "You feel refreshed";
    }
    
    public boolean use(Entity en)
    {
        startDialogue(this, 0);
        
        en.health += value;
        en.projectileAmount += value-2;
        p.playEffect(6);
        return true;
        
    }
}
