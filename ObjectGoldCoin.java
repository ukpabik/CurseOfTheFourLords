package object;

import entity.Entity;
import game.Panel;

public class ObjectGoldCoin extends Entity
{
    public static final String objName = "Gold Coin";
    Panel p;
    public ObjectGoldCoin(Panel p)
    {
        super(p);
        this.p = p;
        
        name = objName;
        down1 = setup("/objects/coin_gold", p.tileSize, p.tileSize);
        value = 6;
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