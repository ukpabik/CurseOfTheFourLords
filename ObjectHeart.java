package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import game.Panel;

public class ObjectHeart extends Entity
{   
    public static final String objName = "Heart";
    Panel p;
    public ObjectHeart(Panel p)
    {
        super(p);
        this.p = p;
        type = type_pickup;
        value = 2;
        down1 = setup("/objects/fullheart", p.tileSize, p.tileSize);
        name = objName;
        image = setup("/objects/fullheart", p.tileSize, p.tileSize);
        image2 = setup("/objects/halfheart", p.tileSize, p.tileSize);
        image3 = setup("/objects/emptyheart", p.tileSize, p.tileSize);
    }
    
    public boolean use(Entity en)
    {
        //PLAY SOUND EFFECT
        p.playEffect(2);
        p.ui.addMessage("Life +" + value);
        en.health += value;
        return true;
        
    }
}
