package object;

import entity.Entity;
import game.Panel;

public class ObjectSilverCoin extends Entity
{
    public static final String objName = "Silver Coin";
    Panel p;
    public ObjectSilverCoin(Panel p)
    {
        super(p);
        this.p = p;
        
        name = objName;
        down1 = setup("/objects/coin_silver", p.tileSize, p.tileSize);
        value = 3;
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