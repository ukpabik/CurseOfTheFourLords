package object;

import entity.Entity;
import game.Panel;

public class ObjectProjectileCount extends Entity
{
    public static final String objName = "ProjectileCount";
    Panel p;
    public ObjectProjectileCount(Panel p)
    {
        super(p);
        this.p = p;
        
        type = type_pickup;
        down1 = setup("/objects/projectile_full", p.tileSize, p.tileSize);
        value = 1;
        name = objName;
        image = setup("/objects/projectile_full", p.tileSize, p.tileSize);
        image2 = setup("/objects/projectile_empty", p.tileSize, p.tileSize);
    }
    
    public boolean use(Entity en)
    {
        //PLAY SOUND EFFECT
        p.playEffect(2);
        p.ui.addMessage("Proj +" + value);
        en.projectileAmount += value;
        return true;
    }

}
