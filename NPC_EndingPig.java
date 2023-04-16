package entity;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import data.BattleProgress;
import game.Panel;
import game.Toolbox;

public class NPC_EndingPig extends Entity
{
    public NPC_EndingPig(Panel p)
    {
        super(p);
        direction = "down";
        speed = 0;
        
        getSprite();
        setDialogue();
        
        solid.x = 8;
        solid.y = 16;
        solidAreaDefaultX = solid.x;
        solidAreaDefaultY = solid.y;
        solid.width = 32;
        solid.height = 32;
        
    }
    public void setDialogue() {
    	if (BattleProgress.allBossesDefeated()) {
    		dialogues[0][0] = "Good job defeating those evil lords.\nMay you prosper for the rest of your life.";
    	}
    	else {
    		dialogues[0][0] = "You must defeat all four bosses before you can leave.\n "
    				+ "Come back to me when you have done so.";
    	}
		
		
	}
	public void getSprite()
    {
       
        up1 = setup("/player/endPig-d1", p.tileSize, p.tileSize);
        up2 = setup("/player/endPig-d2", p.tileSize, p.tileSize);
        down1 = setup("/player/endPig-d1", p.tileSize, p.tileSize);
        down2 = setup("/player/endPig-d2", p.tileSize, p.tileSize);
        left1 = setup("/player/endPig-d1", p.tileSize, p.tileSize);
        left2 = setup("/player/endPig-d2", p.tileSize, p.tileSize);
        right1 = setup("/player/endPig-d1", p.tileSize, p.tileSize);
        right2 = setup("/player/endPig-d2", p.tileSize, p.tileSize);
    }
    
    //Setting the character's behavior (AI Related)
    
    public void setAction()
    {
        
        if (onPath == true) {
            
//            //LOCATION OF THE FIRST PORTAL
//            int goalCol = 6;
//            int goalRow = 6;
            
            //FOLLOWING THE PLAYER
            int goalCol = (p.player.worldX + p.player.solid.x)/p.tileSize;
            int goalRow = (p.player.worldY + p.player.solid.y)/p.tileSize;
            
            pathSearch(goalCol, goalRow);
        }
        else {
            actionCount++;
            
            //Interval for picking direction
            //Wont change for the next 150 frames
            if (actionCount == 80)
            {
                Random rand = new Random();
                
                //gets a random number from 1-100
                int i = rand.nextInt(100) + 1;
                
                //Simple ai for npc movement
                if (i <= 25){direction = "up";}
                if (i > 25 && i <= 50){direction = "down";}
                if (i > 50 && i <= 75){direction = "left";}
                if (i > 75 && i <= 100){direction = "right";}
                
                //resetting the counter
                actionCount = 0;
            }
        }
        
      }
    
    //action to speak for npcs
    public void speak()
    {
    	if (BattleProgress.allBossesDefeated() == true) {
    		p.gameState = p.cutsceneState;
    		p.cs.scene = p.cs.end;
    	}
    	else {
    		startDialogue(this, 0);
    		onPath = true;
    	}
    }
}
