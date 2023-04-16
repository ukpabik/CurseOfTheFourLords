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

public class MON_DemonLord extends Entity
{
    public static final String monName = "Demon Lord";
    Panel p;
    public MON_DemonLord(Panel p)
    {
        super(p);
        this.p = p;
        type = type_monster;
        name = monName;
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxHealth = 50;
        health = maxHealth;
        isBoss = true;
        stasis = true;
        attack = 10;
        defense = 2;
        exp = 50;
        attackDuration1 = 28;
        attackDuration2 = 56;
        
        int size = p.tileSize*5;
        solid.x = 4;
        solid.y = 4;
        solid.width = size - 48*2;
        solid.height = size - 48;
        solidAreaDefaultX = solid.x;
        solidAreaDefaultY = solid.y;
        attackArea.width = 150;
        attackArea.height = 150;
        knockbackPower = 5;
        
        getImage();
        getAttackImage();
        setDialogue();
    }
    public void setDialogue() {
    	dialogues[0][0] = "Why have you disturbed my slumber?!";
    	dialogues[0][1] = "You will pay for this.";
    	dialogues[0][2] = "PREPARE FOR MY WRATH, HUMAN!";
    }
    public void getImage()
    {
        //SCALING OF IMAGE WITH INT I = 5
        int i = 5;
        
        up1 = setup("/boss/red-up1", p.tileSize*i, p.tileSize*i);
        up2 = setup("/boss/red-up2", p.tileSize*i, p.tileSize*i);
        down1 = setup("/boss/red-down1", p.tileSize*i, p.tileSize*i);
        down2 = setup("/boss/red-down2", p.tileSize*i, p.tileSize*i);
        left1 = setup("/boss/red-left1", p.tileSize*i, p.tileSize*i);
        left2 = setup("/boss/red-left2", p.tileSize*i, p.tileSize*i);
        right1 = setup("/boss/red-right1", p.tileSize*i, p.tileSize*i);
        right2 = setup("/boss/red-right2", p.tileSize*i, p.tileSize*i);
        
    }   
    public void getAttackImage() {
        
        int i = 5;
            attackUp1 = setup("/boss/red-au1", p.tileSize*i, p.tileSize * 2*i);
            attackUp2 = setup("/boss/red-au2", p.tileSize*i, p.tileSize * 2*i);
            attackDown1 = setup("/boss/red-ad1", p.tileSize*i, p.tileSize * 2*i);
            attackDown2 = setup("/boss/red-ad2", p.tileSize*i, p.tileSize * 2*i);
            attackLeft1 = setup("/boss/red-al1", p.tileSize * 2*i, p.tileSize*i);
            attackLeft2 = setup("/boss/red-al2", p.tileSize * 2*i, p.tileSize*i);
            attackRight1 = setup("/boss/red-ar1", p.tileSize * 2*i, p.tileSize*i);
            attackRight2 = setup("/boss/red-ar2", p.tileSize * 2*i, p.tileSize*i);
        
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
            checkAttacking(60, p.tileSize*7, p.tileSize*5);
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
    	p.stopMusic();
    	BattleProgress.demonLordKilled = true;
    	
    	//TESTING
//    	BattleProgress.iceLordKilled = true;
//    	BattleProgress.grassLordKilled = true;
//    	BattleProgress.thunderLordKilled = true;
    	
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
