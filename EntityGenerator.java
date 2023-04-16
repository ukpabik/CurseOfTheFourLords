package game;

import entity.Entity;
import object.ObjectAmuletOfLight;
import object.ObjectArrow;
import object.ObjectBronzeCoin;
import object.ObjectClosedDoor;
import object.ObjectDevilsBlade;
import object.ObjectFireball;
import object.ObjectGKey;
import object.ObjectGoldCoin;
import object.ObjectGrassBall;
import object.ObjectHeart;
import object.ObjectIceball;
import object.ObjectIronShield;
import object.ObjectLightningBall;
import object.ObjectProjectileCount;
import object.ObjectRefiller;
import object.ObjectStartSword;
import object.ObjectSwiftysBoots;
import object.ObjectTitaniumSword;
import object.ObjectWoodShield;

public class EntityGenerator
{
    Panel p;
    
    public EntityGenerator(Panel p) {
        this.p = p;
    }
    
    public Entity getObject(String name) {
        Entity obj = null;
        
        switch(name) {
            case ObjectDevilsBlade.objName: obj = new ObjectDevilsBlade(p); break;
            case ObjectTitaniumSword.objName: obj = new ObjectTitaniumSword(p); break;
            case ObjectStartSword.objName: obj = new ObjectStartSword(p); break;
            case ObjectRefiller.objName: obj = new ObjectRefiller(p); break;
            case ObjectWoodShield.objName: obj = new ObjectWoodShield(p); break;
            case ObjectIronShield.objName: obj = new ObjectIronShield(p); break;
            case ObjectAmuletOfLight.objName: obj = new ObjectAmuletOfLight(p); break;
            case ObjectArrow.objName: obj = new ObjectArrow(p); break;
            case ObjectBronzeCoin.objName: obj = new ObjectBronzeCoin(p); break;
            case ObjectFireball.objName: obj = new ObjectFireball(p); break;
            case ObjectGKey.objName: obj = new ObjectGKey(p); break;
            case ObjectGoldCoin.objName: obj = new ObjectGoldCoin(p); break;
            case ObjectGrassBall.objName: obj = new ObjectGrassBall(p); break;
            case ObjectHeart.objName: obj = new ObjectHeart(p); break;
            case ObjectIceball.objName: obj = new ObjectIceball(p); break;
            case ObjectLightningBall.objName: obj = new ObjectLightningBall(p); break;
            case ObjectProjectileCount.objName: obj = new ObjectProjectileCount(p); break;
            case ObjectSwiftysBoots.objName: obj = new ObjectSwiftysBoots(p); break;
            case ObjectClosedDoor.objName: obj = new ObjectClosedDoor(p); break;

        }
        return obj;
    }
}
