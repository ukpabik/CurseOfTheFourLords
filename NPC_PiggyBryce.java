package entity;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import game.Panel;
import game.Toolbox;

public class NPC_PiggyBryce extends Entity
{
    public NPC_PiggyBryce(Panel p)
    {
        super(p);
        direction = "down";
        speed = 0;
        
        getSprite();
        setDialogue();
        
        //STARTS THE SPEAKING INDEX AT 0 WHEN SPOKEN TO
        //SPEAK METHOD ADDS 1 TO THIS
        dialogueChapter = -1;
        
        solid.x = 8;
        solid.y = 16;
        solidAreaDefaultX = solid.x;
        solidAreaDefaultY = solid.y;
        solid.width = 32;
        solid.height = 32;
        
    }
    public void getSprite()
    {
       
        up1 = setup("/player/PiggyBryce-d1", p.tileSize, p.tileSize);
        up2 = setup("/player/PiggyBryce-d2", p.tileSize, p.tileSize);
        down1 = setup("/player/PiggyBryce-d1", p.tileSize, p.tileSize);
        down2 = setup("/player/PiggyBryce-d2", p.tileSize, p.tileSize);
        left1 = setup("/player/PiggyBryce-d1", p.tileSize, p.tileSize);
        left2 = setup("/player/PiggyBryce-d2", p.tileSize, p.tileSize);
        right1 = setup("/player/PiggyBryce-d1", p.tileSize, p.tileSize);
        right2 = setup("/player/PiggyBryce-d2", p.tileSize, p.tileSize);
    }
    
    //Setting the character's behavior (AI Related)
    
    public void setAction()
    {
        
        if (onPath == true) {
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
    //setting dialogue for character
    public void setDialogue()
    {
        dialogues[0][0] = "You there! Traveler! You have the eyes of a \nwarrior.";
        dialogues[0][1] = "The four corners of this world have been \ncursed with great blights.";
        dialogues[0][2] = "If you can defeat them, this world will owe you\n a great debt.";
        dialogues[0][3] = "Please! Help save this world!";
        
        dialogues[1][0] = "The portals in each corner take you to \n another dimension.";
        dialogues[1][1] = "Those dimensions have portals in the corners\n so that you may return.";
        dialogues[1][2] = "Those dimensions also have dungeons located in the\n portal closest to you"
            + " when you appear.";
        
        dialogues[2][0] = "If you get weakened, make sure to check the shop!";
        dialogues[2][1] = "Try not to push yourself so much.";
        
        dialogues[3][0] = "Why am I a talking pig? I have no idea.";
        dialogues[3][1] = "Psst. You ever tried using deodorant, \nyou smell terrible.";
    }
    //action to speak for npcs
    public void speak()
    {
        facePlayer();
        startDialogue(this, dialogueChapter);
        
        //CHANGES THE PAGE TEXT IS READ ON
        dialogueChapter++;
        if (dialogues[dialogueChapter][0] == null) {
            dialogueChapter = 0;

        }

    }
}
