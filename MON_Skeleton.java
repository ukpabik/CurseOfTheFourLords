package monster;

import java.util.Random;

import entity.Entity;
import game.Panel;
import object.ObjectBronzeCoin;
import object.ObjectFireball;
import object.ObjectGoldCoin;
import object.ObjectHeart;
import object.ObjectProjectileCount;
import object.ObjectSilverCoin;

public class MON_Skeleton extends Entity
{
    Panel p;
    public MON_Skeleton(Panel p)
    {
        super(p);
        this.p = p;
        type = type_monster;
        name = "Skeleton";
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxHealth = 12;
        health = maxHealth;
        
        
        attack = 5;
        defense = 2;
        exp = 8;
        attackDuration1 = 40;
        attackDuration2 = 85;
        
        solid.x = 4;
        solid.y = 4;
        solid.width = 40;
        solid.height = 44;
        solidAreaDefaultX = solid.x;
        solidAreaDefaultY = solid.y;
        attackArea.width = 40;
        attackArea.height = 40;
        knockbackPower = 5;
        
        getImage();
        getAttackImage();
    }
    
    public void getImage()
    {
        up1 = setup("/monsters/skeleton-u1", p.tileSize, p.tileSize);
        up2 = setup("/monsters/skeleton-u2", p.tileSize, p.tileSize);
        down1 = setup("/monsters/skeleton-d1", p.tileSize, p.tileSize);
        down2 = setup("/monsters/skeleton-d2", p.tileSize, p.tileSize);
        left1 = setup("/monsters/skeleton-l1", p.tileSize, p.tileSize);
        left2 = setup("/monsters/skeleton-l2", p.tileSize, p.tileSize);
        right1 = setup("/monsters/skeleton-r1", p.tileSize, p.tileSize);
        right2 = setup("/monsters/skeleton-r2", p.tileSize, p.tileSize);
        
    }   
    public void getAttackImage() {
        
            attackUp1 = setup("/monsters/skeleton-swordup1", p.tileSize, p.tileSize * 2);
            attackUp2 = setup("/monsters/skeleton-swordup2", p.tileSize, p.tileSize * 2);
            attackDown1 = setup("/monsters/skeleton-sworddown1", p.tileSize, p.tileSize * 2);
            attackDown2 = setup("/monsters/skeleton-sworddown2", p.tileSize, p.tileSize * 2);
            attackLeft1 = setup("/monsters/skeleton-swordleft1", p.tileSize * 2, p.tileSize);
            attackLeft2 = setup("/monsters/skeleton-swordleft2", p.tileSize * 2, p.tileSize);
            attackRight1 = setup("/monsters/skeleton-swordright1", p.tileSize * 2, p.tileSize);
            attackRight2 = setup("/monsters/skeleton-swordright2", p.tileSize * 2, p.tileSize);
        
    }
    //SAME AS NPC BEHAVIOR
    public void setAction()
    {
        //TO MAKE MONSTER AGGRO WHEN PLAYER GETS CLOSE
        if (onPath == true) {
            //MONSTER LOSES AGGRO
            checkChasing(p.player, 15, 100);
            //FOLLOWING THE PLAYER
            
            pathSearch(getGoalColumn(p.player), getGoalRow(p.player));
        }
        else {
                //CHECK IF THE ENTITY STARTS CHASING
                checkStartChasing(p.player, 5, 100);
                getRandomDirection(120);
        }
        
        if (attacking == false) {
            //30 = rate of attack
            checkAttacking(25, p.tileSize*4, p.tileSize);
        }
    }
    
    public void damageReaction()
    {
        actionCount = 0;
//        direction = p.player.direction;
        
        //GETS AGGRO ON PLAYER WHEN IT TAKES DAMAGE
        onPath = true;
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


