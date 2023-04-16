package monster;

import java.util.Random;

import data.BattleProgress;
import entity.Entity;
import game.Panel;
import object.ObjectBronzeCoin;
import object.ObjectClosedDoor;
import object.ObjectGoldCoin;
import object.ObjectHeart;
import object.ObjectProjectileCount;
import object.ObjectSilverCoin;

public class MON_IceLord extends Entity
{
    public static final String monName = "Ice Lord";
    Panel p;
    public MON_IceLord(Panel p)
    {
        super(p);
        this.p = p;
        type = type_monster;
        name = monName;
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxHealth = 50;
        health = maxHealth;
        stasis = true;
        isBoss = true;
        attack = 10;
        defense = 2;
        exp = 50;
        attackDuration1 = 25;
        attackDuration2 = 50;
        
        int size = p.tileSize*5;
        solid.x = 48;
        solid.y = 48;
        solid.width = size - 48*2;
        solid.height = size - 48;
        solidAreaDefaultX = solid.x;
        solidAreaDefaultY = solid.y;
        attackArea.width = 170;
        attackArea.height = 170;
        knockbackPower = 5;
        
        getImage();
        getAttackImage();
        setDialogue();
    }
    public void setDialogue() {
    	dialogues[0][0] = "Zzzz, zzz, zzz.......";
    	dialogues[0][1] = "Ugh, another one.";
    	dialogues[0][2] = "You'll be the 10th human this week I've killed.";
    }
    public void getImage()
    {
        //SCALING OF IMAGE WITH INT I = 5
        int i = 5;
        
        up1 = setup("/boss/blue-up1", p.tileSize*i, p.tileSize*i);
        up2 = setup("/boss/blue-up2", p.tileSize*i, p.tileSize*i);
        down1 = setup("/boss/blue-down1", p.tileSize*i, p.tileSize*i);
        down2 = setup("/boss/blue-down2", p.tileSize*i, p.tileSize*i);
        left1 = setup("/boss/blue-left1", p.tileSize*i, p.tileSize*i);
        left2 = setup("/boss/blue-left2", p.tileSize*i, p.tileSize*i);
        right1 = setup("/boss/blue-right1", p.tileSize*i, p.tileSize*i);
        right2 = setup("/boss/blue-right2", p.tileSize*i, p.tileSize*i);
        
    }   
    public void getAttackImage() {
        
        int i = 5;
            attackUp1 = setup("/boss/blue-au1", p.tileSize*i, p.tileSize * 2*i);
            attackUp2 = setup("/boss/blue-au2", p.tileSize*i, p.tileSize * 2*i);
            attackDown1 = setup("/boss/blue-ad1", p.tileSize*i, p.tileSize * 2*i);
            attackDown2 = setup("/boss/blue-ad2", p.tileSize*i, p.tileSize * 2*i);
            attackLeft1 = setup("/boss/blue-al1", p.tileSize * 2*i, p.tileSize*i);
            attackLeft2 = setup("/boss/blue-al2", p.tileSize * 2*i, p.tileSize*i);
            attackRight1 = setup("/boss/blue-ar1", p.tileSize * 2*i, p.tileSize*i);
            attackRight2 = setup("/boss/blue-ar2", p.tileSize * 2*i, p.tileSize*i);
        
    }
    //SAME AS NPC BEHAVIOR
    public void setAction()
    {
        if (enraged == false && health < maxHealth/2) {
            enraged = true;
            defaultSpeed++;
            speed = defaultSpeed;
            attack *= 2;
        }
        //TO MAKE MONSTER AGGRO WHEN PLAYER GETS CLOSE
        if (getTileDistance(p.player) < 9) {
            moveTowardPlayer(60);
        }
        else {
            getRandomDirection(120);
        }
        
        if (attacking == false) {
            //30 = rate of attack
            checkAttacking(60, p.tileSize*10, p.tileSize*5);
        }
    }
    
    public void damageReaction()
    {
        actionCount = 0;

    }
    
    //CHECKS MONSTER ITEM DROPS
    public void checkDrop()
    {
    	p.battleStart = false;
    	BattleProgress.iceLordKilled = true;
    	p.stopMusic();
    	for (int i = 0; i < p.object[1].length; i++) {
    		if (p.object[p.mapCurrent][i] != null && 
    				p.object[p.mapCurrent][i].name.equals(ObjectClosedDoor.objName)) {
    			p.playEffect(3);
    			p.object[p.mapCurrent][i] = null;
    		}
    	}
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
