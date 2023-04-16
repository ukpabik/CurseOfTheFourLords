package object;

import entity.Entity;
import game.Panel;

public class ObjectBronzeCoin extends Entity
{
    public static final String objName = "Bronze Coin";
    Panel p;
    public ObjectBronzeCoin(Panel p)
    {
        super(p);
        this.p = p;
        
        name = objName;
        down1 = setup("/objects/coin_bronze", p.tileSize, p.tileSize);
        value = 1;
        type = type_pickup;
    }
    
    
    //EFFECT OF ITEM
    public boolean use(Entity en)
    {
        
        //PLAY SOUND EFFECT
        p.playEffect(2);
        p.ui.addMessage("Coin + " + value);
        p.player.coin += value;
        return true;
    }

}
