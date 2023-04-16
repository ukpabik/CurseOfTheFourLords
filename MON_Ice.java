package monster;

import java.util.Random;

import entity.Entity;
import game.Panel;
import object.ObjectBronzeCoin;
import object.ObjectFireball;
import object.ObjectGoldCoin;
import object.ObjectHeart;
import object.ObjectIceball;
import object.ObjectProjectileCount;
import object.ObjectSilverCoin;

public class MON_Ice extends Entity
{
    Panel p;
    public MON_Ice(Panel p)
    {
        super(p);
        this.p = p;
        type = type_monster;
        name = "Ice";
        defaultSpeed = 2;
        speed = defaultSpeed;
        maxHealth = 6;
        health = maxHealth;
        
        attack = 4;
        defense = 0;
        exp = 3;
        
        solid.x = 8;
        solid.y = 14;
        solid.width = 32;
        solid.height = 32;
        solidAreaDefaultX = solid.x;
        solidAreaDefaultY = solid.y;
        projectile = new ObjectIceball(p);
        
        
        getImage();
    }
    
    public void getImage()
    {
        up1 = setup("/monsters/icehead1", p.tileSize, p.tileSize);
        up2 = setup("/monsters/icehead2", p.tileSize, p.tileSize);
        down1 = setup("/monsters/icehead1", p.tileSize, p.tileSize);
        down2 = setup("/monsters/icehead2", p.tileSize, p.tileSize);
        left1 = setup("/monsters/icehead1", p.tileSize, p.tileSize);
        left2 = setup("/monsters/icehead2", p.tileSize, p.tileSize);
        right1 = setup("/monsters/icehead1", p.tileSize, p.tileSize);
        right2 = setup("/monsters/icehead2", p.tileSize, p.tileSize);
        
    }
    
    
    //SAME AS NPC BEHAVIOR
    public void setAction()
    {
        //TO MAKE MONSTER AGGRO WHEN PLAYER GETS CLOSE
        if (onPath == true) {
            //MONSTER LOSES AGGRO
            checkChasing(p.player, 25, 100);
            //FOLLOWING THE PLAYER
            
            pathSearch(getGoalCol(p.player), getGoalRow(p.player));
            //SIMPLE AI FOR PROJECTILES
            checkShooting(200, 30);
        }
        else {
                //CHECK IF THE ENTITY STARTS CHASING
                checkStartChasing(p.player, 5, 100);
                getRandomDirection(60);
        }
    }
    public void damageReaction()
    {
        actionCount = 0;
      //GETS AGGRO ON PLAYER WHEN IT TAKES DAMAGE
        onPath = true;
    }
    public void update() {
        super.update();
        
        //TO MAKE MONSTER AGGRO WHEN PLAYER GETS CLOSE
        
        int xDistance = Math.abs(worldX - p.player.worldX);
        int yDistance = Math.abs(worldY - p.player.worldY);
        int tileDistance = (xDistance + yDistance) / p.tileSize;
        
        if (onPath == false && tileDistance < 6) {
            //ENEMY GETS AGGRO 50% of the time when within 5 tile 
            int i = new Random().nextInt(100)+1;
            if (i > 50) {
                onPath = true;
            }
        }
        
        //MONSTER LOSES AGGRO
        if (onPath == true && tileDistance > 25) {
            onPath = false;
        }
    }
    //CHECKS MONSTER ITEM DROPS
    public void checkDrop()
    {
      //ROLLING DICE TO DETERMINE DROPPED ITEMS
        int i = new Random().nextInt(100)+1;
        
        //SET THE ITEMS DROPPED
        if (i < 20 && i > 0)
        {
            dropItem(new ObjectSilverCoin(p));
        }
        if (i < 50 && i > 20)
        {
            dropItem(new ObjectBronzeCoin(p));
        }
        if (i >= 50 && i < 75)
        {
            dropItem(new ObjectHeart(p));
        }
        if (i >= 75 && i < 90)
        {
            dropItem(new ObjectProjectileCount(p));
        }
        if (i >= 90 && i < 100)
        {
            dropItem(new ObjectGoldCoin(p));
        }
    }
    
    
}
