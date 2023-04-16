package entity;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import game.Panel;
import game.Toolbox;
import object.ObjectAmuletOfLight;
import object.ObjectArrow;
import object.ObjectDevilsBlade;
import object.ObjectGKey;
import object.ObjectIronShield;
import object.ObjectRefiller;
import object.ObjectStartSword;
import object.ObjectTitaniumSword;
import object.ObjectWoodShield;

public class NPC_PigMerchant extends Entity
{
    public NPC_PigMerchant(Panel p)
    {
        super(p);
        direction = "down";
        speed = 0;
        
        getSprite();
        setDialogue();
        setItems();
        
        solid.x = 8;
        solid.y = 16;
        solidAreaDefaultX = solid.x;
        solidAreaDefaultY = solid.y;
        solid.width = 32;
        solid.height = 32;
        
    }
    public void getSprite()
    {
       
        up1 = setup("/player/pigmerchant-d1", p.tileSize, p.tileSize);
        up2 = setup("/player/pigmerchant-d2", p.tileSize, p.tileSize);
        down1 = setup("/player/pigmerchant-d1", p.tileSize, p.tileSize);
        down2 = setup("/player/pigmerchant-d2", p.tileSize, p.tileSize);
        left1 = setup("/player/pigmerchant-d1", p.tileSize, p.tileSize);
        left2 = setup("/player/pigmerchant-d2", p.tileSize, p.tileSize);
        right1 = setup("/player/pigmerchant-d1", p.tileSize, p.tileSize);
        right2 = setup("/player/pigmerchant-d2", p.tileSize, p.tileSize);
    }
    public void setDialogue() {
        dialogues[0][0] = "I have some things that you might like. \nHave a look.";
        dialogues[1][0] = "Please come again, and maybe I'll have better loot.";
        dialogues[2][0] = "You can't afford it.";
        dialogues[3][0] = "You cannot hold more items!";
        dialogues[4][0] = "You cannot sell equipped items!";
    }
    
    public void setItems() {
        inventory.add(new ObjectGKey(p));
        inventory.add(new ObjectRefiller(p));
        inventory.add(new ObjectStartSword(p));
        inventory.add(new ObjectWoodShield(p));
        inventory.add(new ObjectIronShield(p));
        inventory.add(new ObjectAmuletOfLight(p));
        inventory.add(new ObjectTitaniumSword(p));
        inventory.add(new ObjectDevilsBlade(p));
    }
    
    public void speak() {
        startDialogue(this, 0);
        p.gameState = p.storeState;
        p.ui.npc = this;
    }
}