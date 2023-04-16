package game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import data.BattleProgress;
import entity.Entity;
import monster.MON_DemonLord;
import monster.MON_GrassLord;
import monster.MON_IceLord;
import monster.MON_ThunderLord;
import object.ObjectBronzeCoin;
import object.ObjectHeart;
import object.ObjectProjectileCount;

public class UI
{
    Panel p;
    Graphics2D graphics2;
    public boolean gameFinished = false;
    
    //IMAGES
    BufferedImage fullheart, halfheart, emptyheart, projectile_full, projectile_empty, coin;
    
    //FONT
    public Font pixeloid;
    
    //INVENTORY
    public int playerSlotCol = 0;
    public int playerSlotRow = 0;
    public int npcSlotCol = 0;
    public int npcSlotRow = 0;
    int subState = 0;
    int count;
    
    
    //TEXT
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCount = new ArrayList<>();
    public boolean messageState = false;
    
    
    //DIALOGUE
    public String currentDialogue = "";
    int charIndex = 0;
    String combinedText = "";
    
    //FOR CHOOSING IN MAIN MENU
    public int commandChoice = 0;
    public int titleScreenState = 0; // 0: First screen, 1: Second screen, etc
    public Entity npc;
    
    
    
    
    
    public UI(Panel p)
    {
        this.p = p;
        try
        {
            InputStream is = getClass().getResourceAsStream("/font/PixeloidSans.ttf");
            pixeloid = Font.createFont(Font.TRUETYPE_FONT, is);
        }catch(FontFormatException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        
        //CREATE HUD
        Entity heart = new ObjectHeart(p);
        fullheart = heart.image;
        halfheart = heart.image2;
        emptyheart = heart.image3;
        
        Entity projectileCount = new ObjectProjectileCount(p);
        projectile_full = projectileCount.image;
        projectile_empty = projectileCount.image2;
        Entity bronzeCoin = new ObjectBronzeCoin(p);
        coin = bronzeCoin.down1;
        
        
    }
    public void addMessage(String text)
    {
        message.add(text);
        messageCount.add(0);
    }
    
    public void draw(Graphics2D graph2)
    {
        this.graphics2 = graph2;
        graph2.setFont(pixeloid);
        graph2.setColor(Color.white);
        
        //titleState
        if (p.gameState == p.titleState)
        {
            drawTitleScreen();
        }
        
        //playstate
        if (p.gameState == p.playState) 
        {
        	drawMonsterHealth();
            drawPlayerHealth();
            drawMessage();
        }
        //pausestate
        if(p.gameState == p.pauseState)
        {
            drawPlayerHealth();
            drawPauseScreen();
        }
        //dialoguestate
        if (p.gameState == p.dialogueState)
        {
            drawPlayerHealth();
            drawDialogueScreen();
        }
        //characterstate
        if (p.gameState == p.characterState)
        {
            drawCharacterScreen();
            drawInventoryScreen(p.player, true);
        }
        //optionsstate
        if(p.gameState == p.optionsState) {
            drawOptionsScreen();
        }
        //gameoverstate
        if(p.gameState == p.gameOverState) {
            drawGameOverScreen();
        }
        //TRANSITION
        if(p.gameState == p.transitionState) {
            drawTransition();
        }
        //STORE STATE
        if(p.gameState == p.storeState) {
            drawStoreScreen();
        }
    }
    
    public void drawTransition() {
        //USED TO DRAW SHADING EFFECT
        count++;
        graphics2.setColor(new Color(0,0,0,count*5));
        graphics2.fillRect(0, 0, p.screenWidth, p.screenHeight);
        
        //TRANSITION
        if(count == 50) {
            count = 0;
            p.gameState = p.playState;
            p.mapCurrent = p.eh.tempMap;
            p.player.worldX = p.tileSize * p.eh.tempCol;
            p.player.worldY = p.tileSize * p.eh.tempRow;
            p.eh.previousEventX = p.player.worldX;
            p.eh.previousEventY = p.player.worldY;
            p.changeArea();
        }
    }
    
    public void drawPlayerHealth()
    {
        //POSITION FOR HEARTS
        int x = p.tileSize / 2;
        int y = p.tileSize / 2;
        int size = 32;
        int i = 0;
        
        //SINCE 2 HEALTH = 1 HEART, DIVIDE THIS NUMBER BY 2
        while (i < p.player.maxHealth / 2)
        {
            //DRAWING BLANK HEART IMAGE FIRST DEPENDING ON CURRENT HEALTH
            graphics2.drawImage(emptyheart, x, y, size, size, null);
            i++;
            x += size;
            if (i % 7 == 0) {
            	x = p.tileSize/2;
            	y += size;
            }
        }
        
        //RESET THE IMAGES
        x = p.tileSize / 2;
        y = p.tileSize / 2;
        i = 0;
        
        //DRAWING THE HALF AND FULL HEARTS
        while (i < p.player.health) 
        {
            graphics2.drawImage(halfheart, x, y, size, size, null);
            i++;
            if (i < p.player.health)
            {
                graphics2.drawImage(fullheart, x, y, size, size, null);
            }
            i++;
            x += size;
        }
        
        //DRAWING PROJECTILE COUNTER
        x = (p.tileSize/2) - 5;
        y = (int)(p.tileSize*2);
        
        
        i = 0;
        while(i < p.player.maxProjectileAmount)
        {
            graphics2.drawImage(projectile_empty, x, y, size, size, null);
            i++;
            x += 35;
        }
        
        //DRAW CURRENT PROJECTILE AMOUNT
        x = (p.tileSize/2) - 5;
        y = (int)(p.tileSize*2);
        i = 0;
        while (i < p.player.projectileAmount)
        {
            graphics2.drawImage(projectile_full, x, y, size, size, null);
            i++;
            x += 35;
        }
        
        
    }
    
    public void drawTitleScreen()
    {
        if (titleScreenState == 0) 
        {
            graphics2.setColor(new Color(120, 102, 102));
            graphics2.fillRect(0, 0, p.screenWidth, p.screenHeight);
            
            graphics2.setColor(Color.pink);
            graphics2.setFont(graphics2.getFont().deriveFont(Font.BOLD, 15F));
            String text = "Make sure to speak with";
            
            int x = 700;
            int y = p.tileSize * 4;
            graphics2.drawString(text, x, y);
            
            text = "the PINK pig on start!";
            y += 30;
            graphics2.drawString(text, x, y);
            
            //TITLE NAME
            
            graphics2.setFont(graphics2.getFont().deriveFont(Font.BOLD, 55F));
            text = "The Curse of the Four Lords";
            x = getCenterX(text);
            y = p.tileSize * 2;
           
            //SHADOW
            graphics2.setColor(Color.black);
            graphics2.drawString(text, x + 3, y + 3);
            //MAIN COLOR
            graphics2.setColor(new Color(51, 51, 51));
            graphics2.drawString(text, x, y);
            
            //MAIN CHARACTER IMAGE
            x = p.screenWidth / 2 - (p.tileSize * 2) / 2 - 10;
            y += p.tileSize * 2;
            graphics2.drawImage(p.player.down1, x, y, p.tileSize * 2, p.tileSize * 2, null);
            
            //DISPLAYS BOSS IMAGES
            if (BattleProgress.allBossesDefeated()) {
            	int bossX = 45;
                int bossY = p.tileSize * 3;
                int checkmarkX = bossX + 80;
                graphics2.drawImage(new MON_DemonLord(p).down1, bossX, bossY, p.tileSize, p.tileSize, null);
                graphics2.drawImage(p.player.checkmark, checkmarkX, bossY-5, p.tileSize, p.tileSize, null);
                bossY += p.tileSize;
                graphics2.drawImage(new MON_IceLord(p).down1, bossX, bossY, p.tileSize, p.tileSize, null);
                graphics2.drawImage(p.player.checkmark, checkmarkX, bossY-5, p.tileSize, p.tileSize, null);
                bossY += p.tileSize;
                graphics2.drawImage(new MON_GrassLord(p).down1, bossX, bossY, p.tileSize, p.tileSize, null);
                graphics2.drawImage(p.player.checkmark, checkmarkX, bossY-5, p.tileSize, p.tileSize, null);
                bossY += p.tileSize;
                graphics2.drawImage(new MON_ThunderLord(p).down1, bossX, bossY, p.tileSize, p.tileSize, null);
                graphics2.drawImage(p.player.checkmark, checkmarkX, bossY-5, p.tileSize, p.tileSize, null);
            }
            
            
            //MENU IMAGES
            graphics2.setFont(graphics2.getFont().deriveFont(Font.BOLD, 48F));
            
            
            
            //NEW GAME
            text = "NEW GAME";
            x = getCenterX(text);
            y += p.tileSize * 4.2;
            graphics2.drawString(text, x, y);
            if (commandChoice == 0)
            {
                graphics2.drawString(">", x - p.tileSize, y);
            }
            
            //LOAD GAME
            text = "LOAD GAME";
            x = getCenterX(text);
            y += p.tileSize;
            graphics2.drawString(text, x, y);
            if (commandChoice == 1)
            {
                graphics2.drawString(">", x - p.tileSize, y);
            }
            
            //QUIT GAME
            text = "QUIT GAME";
            x = getCenterX(text);
            y += p.tileSize;
            graphics2.drawString(text, x, y);
            if (commandChoice == 2)
            {
                graphics2.drawString(">", x - p.tileSize, y);
            }
        }
        else if(titleScreenState == 1)
        {
            //CHARACTER SELECT SCREEN
            graphics2.setColor(Color.white);
            graphics2.setFont(graphics2.getFont().deriveFont(30F));
            
            
            //SHOWS EACH CHARACTER
            String text = "Select the cooler number.";
            int x = getCenterX(text);
            int y = p.tileSize * 3;
            graphics2.drawString(text, x, y);
            
            text = "One";
            x = getCenterX(text);
            y += p.tileSize * 3;
            graphics2.drawString(text, x, y);
            if (commandChoice == 0)
            {
                graphics2.drawString(">", x-p.tileSize, y);
            }
            text = "Two";
            x = getCenterX(text);
            y += p.tileSize;
            graphics2.drawString(text, x, y);
            if (commandChoice == 1)
            {
                graphics2.drawString(">", x-p.tileSize, y);
            }
            text = "Three";
            x = getCenterX(text);
            y += p.tileSize;
            graphics2.drawString(text, x, y);
            if (commandChoice == 2)
            {
                graphics2.drawString(">", x-p.tileSize, y);
            }
            text = "Go Back";
            x = getCenterX(text);
            y += p.tileSize * 2;
            graphics2.drawString(text, x, y);
            if (commandChoice == 3)
            {
                graphics2.drawString(">", x-p.tileSize, y);
            }
            
        }
        
        
        
        
        
        
    }
    
    public void drawMessage()
    {
        int messageX = p.tileSize;
        int messageY = p.tileSize * 4;
        
        graphics2.setFont(graphics2.getFont().deriveFont(Font.BOLD, 25F));
        for (int i = 0; i < message.size(); i++)
        {
            if (message.get(i) != null)
            {
                graphics2.setColor(Color.black);
                graphics2.drawString(message.get(i), messageX + 2, messageY + 2);
                graphics2.setColor(Color.white);
                graphics2.drawString(message.get(i), messageX, messageY);
                
                int counter = messageCount.get(i) + 1; //SAME AS MESSAGECOUNT++
                messageCount.set(i, counter); //SET THE COUNTER TO THE ARRAYLIST
                messageY += 40;
                
                //GETS RID OF MESSAGE AFTER 3 SECONDS
                if (messageCount.get(i) > 180)
                {
                    message.remove(i);
                    messageCount.remove(i);
                }
            }
        }
    }
    
    public void drawPauseScreen()
    {
        graphics2.setFont(graphics2.getFont().deriveFont(Font.PLAIN, 54F));
        String text = "PAUSED";
        int x = getCenterX(text);
        int y = p.screenHeight / 2;
        
        
        
        graphics2.drawString(text, x, y);
    }
    
    public void drawDialogueScreen()

    {
        //WINDOW for DIALOGUE
        //parameters
        int x = p.tileSize * 2; // 2 tiles to right of left edge
        int y = p.tileSize / 2;
        int width = p.screenWidth - (p.tileSize * 6);
        int height = p.tileSize * 4;
        
        drawSubWindow(x, y, width, height);
        
        graphics2.setFont(graphics2.getFont().deriveFont(Font.PLAIN, 19F));
        x += p.tileSize;
        y += p.tileSize;
        
        if (npc.dialogues[npc.dialogueChapter][npc.dialogueIndex] != null) {
            // IF YOU WANT TO DISPLAY TEXT ALL AT ONCE UNCOMMENT THIS
            // ALSO DELETE WHERE THE CHARACTERS ARRAY IS USED
//            currentDialogue = npc.dialogues[npc.dialogueSet][npc.dialogueIndex];
            char characters[] = npc.dialogues[npc.dialogueChapter][npc.dialogueIndex].toCharArray();
            if (charIndex < characters.length) {
                p.playEffect(14);
                String s = String.valueOf(characters[charIndex]);
                combinedText = combinedText + s;
                currentDialogue = combinedText;
                charIndex++;
            }
            if (p.hk.pressEnter == true) {
                
                charIndex = 0;
                combinedText = "";
                
                if (p.gameState == p.dialogueState || p.gameState == p.cutsceneState) {
                    npc.dialogueIndex++;
                    p.hk.pressEnter = false;
                }
            }
        }
        else { //IF NO TEXT IS IN THE ARRAY
            npc.dialogueIndex = 0;
            
            if (p.gameState == p.dialogueState) {
                p.gameState = p.playState;
            }
            if (p.gameState == p.cutsceneState) {
            	p.cs.phase++;
            }
        }
        
        
        
        for (String line : currentDialogue.split("\n"))
        {
            graphics2.drawString(line, x, y);
            y += 40;
        }
       
    }
    
    public void drawInventoryScreen(Entity en, boolean cursor)
    {
        int x = 0;
        int y=0;
        int width=0;
        int height=0;
        int slotColumn=0;
        int slotRow =0;
        //FRAMES
        if (en == p.player) {
            x = p.tileSize * 9;
            y = p.tileSize;
            width = p.tileSize * 6;
            height = p.tileSize * 5;
            slotColumn = playerSlotCol;
            slotRow = playerSlotRow;
        }
        else {
            x = p.tileSize * 2;
            y = p.tileSize;
            width = p.tileSize * 6;
            height = p.tileSize * 5;
            slotColumn = npcSlotCol;
            slotRow = npcSlotRow;
        }
        
        drawSubWindow(x, y, width, height);
        
        //SLOTS
        final int startX = x + 20;
        final int startY = y + 20;
        int slotX = startX;
        int slotY = startY;
        int size = p.tileSize + 3;
        
        //DRAW PLAYER ITEMS
        for (int i = 0; i < en.inventory.size(); i++)
        {
            
            //EQUIP CURSOR
            if (en.inventory.get(i) == en.currentWeapon || en.inventory.get(i) == en.currentShield ||
                    en.inventory.get(i) == en.lightSource)
            {
                graphics2.setColor(new Color(240, 190, 80, 120));
                graphics2.fillRoundRect(slotX, slotY, p.tileSize, p.tileSize, 10, 10);
            }
            
            graphics2.drawImage(en.inventory.get(i).down1, slotX, slotY, null);
            
            //DISPLAY ITEM AMOUNT
            if (en == p.player && en.inventory.get(i).amount > 1) {
                graphics2.setFont(graphics2.getFont().deriveFont(15f));
                int itemX;
                int itemY;
                
                String amount = "" + en.inventory.get(i).amount;
                itemX = getRightAlignedX(amount, slotX+48);
                itemY = slotY + p.tileSize;
                
                graphics2.setColor(new Color(60,60,60));
                graphics2.drawString(amount, itemX, itemY);
                graphics2.setColor(Color.white);
                graphics2.drawString(amount, itemX-3, itemY-3);
            }
            
            slotX += size;
            
            if (i == 4 || i == 9 || i == 14 || i == 19)
            {
                slotX = startX;
                slotY += size;
            }
        }
        
        //CURSOR
        if (cursor == true) {
            int cursorX = startX + (size * slotColumn);
            int cursorY = startY + (size * slotRow);
            int cursorWidth = p.tileSize;
            int cursorHeight = p.tileSize;
            
            //DRAW CURSOR
            graphics2.setColor(Color.white);
            graphics2.setStroke(new BasicStroke(3));
            graphics2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
            
            //DESCRIPTION FRAME
            int descFrameX = x; 
            int descFrameY = y + height;
            int descFrameWidth = width;
            int descFrameHeight = p.tileSize * 3;
            
            
            
            //DESCRIPTION TEXT
            int textX = descFrameX + 20;
            int textY = descFrameY + p.tileSize;
            graphics2.setFont(graphics2.getFont().deriveFont(18F));
            
            int index = getItemSlotIndex(slotColumn, slotRow);
            
            if (index < en.inventory.size())
            {
                drawSubWindow(descFrameX, descFrameY, descFrameWidth, descFrameHeight);
                for (String line : en.inventory.get(index).description.split("\n"))
                {
                    graphics2.drawString(line, textX, textY);
                    textY += 32;
                }
            }
            
        }
        
        
        
        
    }
    
    public int getItemSlotIndex(int slotCol, int slotRow)
    {
        //5 = NUMBER OF ITEMS IN EACH ROW
        int itemSlotIndex = slotCol + (slotRow * 5);
        return itemSlotIndex;
    }
    public void drawOptionsScreen() {
        graphics2.setColor(Color.white);
        graphics2.setFont(graphics2.getFont().deriveFont(21F));
        
        //SUBWINDOW
        int x = p.tileSize * 6;
        int y = p.tileSize;
        int width = p.tileSize*8;
        int height = p.tileSize*10;
        drawSubWindow(x, y, width, height);
        
        switch(subState) {
            case 0: options_top(x, y); break;
            case 1: options_control(x, y); break;
            case 2: options_quitConfirm(x, y); break;
        }
        
        
        
    }
    public void options_top(int xFrame, int yFrame) {
    	int x;
        int y;
        
        
        //TITLE
        String title = "Options";
        x = getCenterX(title);
        y = yFrame + p.tileSize;
        graphics2.drawString(title, x, y);
        
        
        //MUSIC
        x = xFrame + p.tileSize;
        y += p.tileSize * 2;
        graphics2.drawString("Music", x, y);
        if (commandChoice == 0) {
            graphics2.drawString(">", x-25, y);
        }
        
        //SOUND EFFECTS
        y += p.tileSize * 1.2;
        graphics2.drawString("Effects", x, y);
        if (commandChoice == 1) {
            graphics2.drawString(">", x-25, y);
        }
        
        //CONTROLS
        y += p.tileSize * 1.2;
        graphics2.drawString("Controls", x, y);
        if (commandChoice == 2) {
            graphics2.drawString(">", x-25, y);
            if (p.hk.pressEnter == true) {
                p.playEffect(4);
                subState = 1;
                commandChoice = 0;
            }
        }
        
        //QUIT GAME
        y += p.tileSize * 1.2;
        graphics2.drawString("Quit Game", x, y);
        if (commandChoice == 3) {
            graphics2.drawString(">", x-25, y);
            if(p.hk.pressEnter == true) {
                subState = 2;
                commandChoice = 0;
            }
        }
        
        //GO BACK
        y += p.tileSize * 2.2;
        graphics2.drawString("Exit", x, y);
        if (commandChoice == 4) {
            graphics2.drawString(">", x-25, y);
            if (p.hk.pressEnter == true) {
                p.gameState = p.playState;
            }
        }
        
        //MUSIC BAR
        x = xFrame + (int)(p.tileSize * 4.5);
        y = yFrame + p.tileSize * 2 + 26;
        graphics2.setStroke(new BasicStroke(3));
        graphics2.drawRect(x, y, 120, 24); //120 / 5 = 24
        int volumeWidth = 24 * p.music.vScale;
        graphics2.fillRect(x,y,volumeWidth,24);
        
        //SOUND EFFECT BAR
        y += p.tileSize*1.2;
        graphics2.drawRect(x, y, 120, 24);
        volumeWidth = 24 * p.soundEffect.vScale;
        graphics2.fillRect(x,y,volumeWidth,24);
        
        //MAKE SURE TO SAVE SETTINGS EACH TIME YOU GO TO OPTIONS
        p.config.saveConfig();
        
    }
    public void options_control(int xFrame, int yFrame) {
        int yText;
        int xText;
        graphics2.setFont(graphics2.getFont().deriveFont(17F));
        //TITLE
        String title = "Controls";
        xText = getCenterX(title);
        yText = yFrame + p.tileSize;
        graphics2.drawString(title, xText, yText);
        
        //NAMES
        xText = xFrame + p.tileSize;
        yText += p.tileSize;
        graphics2.drawString("Move", xText, yText);
        yText += p.tileSize;
        graphics2.drawString("Interact/Attack", xText, yText);
        yText += p.tileSize;
        graphics2.drawString("Guard/Parry", xText, yText);
        yText += p.tileSize;
        graphics2.drawString("Shoot", xText, yText);
        yText += p.tileSize;
        graphics2.drawString("Character Select", xText, yText);
        yText += p.tileSize;
        graphics2.drawString("Pause", xText, yText);
        yText += p.tileSize;
        graphics2.drawString("Options", xText, yText);
        
        
        xText = xFrame + p.tileSize * 6;
        yText = yFrame + p.tileSize * 2;
        //KEYS
        graphics2.drawString("WASD", xText, yText); yText += p.tileSize;
        graphics2.drawString("ENTER", xText, yText); yText += p.tileSize;
        graphics2.drawString("SPACE", xText, yText); yText += p.tileSize;
        graphics2.drawString("H", xText, yText); yText += p.tileSize;
        graphics2.drawString("C", xText, yText); yText += p.tileSize;
        graphics2.drawString("P", xText, yText); yText += p.tileSize;
        graphics2.drawString("ESC", xText, yText); yText += p.tileSize;
        
        //BACK BUTTON
        xText = xFrame + p.tileSize;
        yFrame = yFrame + p.tileSize * 10;
        graphics2.drawString("Press B to go back.", xText, yText);
        if (commandChoice == 0) {
            graphics2.drawString(">", xText-25, yText);
            
        }
        graphics2.setFont(graphics2.getFont().deriveFont(21F));
    }
    
    public void options_quitConfirm(int xFrame, int yFrame) {
        //EXIT MENU SCREEN
        int x = xFrame + p.tileSize;
        int y = yFrame + p.tileSize * 3;
        
        currentDialogue = "Quit the game and \nreturn to the title screen?";
        
        for(String line: currentDialogue.split("\n")) {
            graphics2.drawString(line, x, y);
            y += 40;
        }
        
        //YES 
        String text = "Press Y for Yes";
        x = getCenterX(text);
        y += p.tileSize * 3;
        graphics2.drawString(text, x, y);
        
        
        //NO
        text = "Press N for No";
        x = getCenterX(text);
        y += p.tileSize*1.5;
        graphics2.drawString(text, x, y);
        
    }
    
    public void drawGameOverScreen() {
        graphics2.setColor(new Color(0,0,0,150));
        graphics2.fillRect(0, 0, p.screenWidth, p.screenHeight);
        
        int x;
        int y;
        String message;
        graphics2.setFont(graphics2.getFont().deriveFont(Font.BOLD, 80f));
        
        message = "You have died.";
        
        //TEXT SHADOWING
        graphics2.setColor(Color.black);
        x = getCenterX(message);
        y = p.tileSize * 4;
        graphics2.drawString(message, x, y);
        
        //ACTUAL TEXT
        graphics2.setColor(Color.white);
        graphics2.drawString(message, x-4, y-4);
        
        //RETRY TEXT
        graphics2.setFont(graphics2.getFont().deriveFont(40f));
        message = "Retry";
        x = getCenterX(message);
        y += p.tileSize*4;
        graphics2.drawString(message, x, y);
        if (commandChoice == 0) {
            graphics2.drawString(">", x-40, y);
        }
        
        //RETURN TO TITLE TEXT
        message = "Return to Title Screen";
        x = getCenterX(message);
        y += 80;
        graphics2.drawString(message, x, y);
        if (commandChoice == 1) {
            graphics2.drawString(">", x-40, y);
        }
    }
    
    public void drawCharacterScreen()
    {
        //CREATE A FRAME FOR STATS
        final int x = p.tileSize * 2;
        final int y = p.tileSize;
        final int width = p.tileSize * 5;
        final int height = p.tileSize * 10;
        drawSubWindow(x, y, width, height);
        
        //TEXT
        graphics2.setColor(Color.white);
        graphics2.setFont(graphics2.getFont().deriveFont(20F));
        int textX = x + 20;
        int textY = y + p.tileSize;
        //SAME AS FONT SIZE
        final int lineHeight = 38;
        
        //NAMES
        graphics2.drawString("Level", textX, textY);
        textY += lineHeight;
        graphics2.drawString("Health", textX, textY);
        textY += lineHeight;
        graphics2.drawString("Projectile", textX, textY);
        textY += lineHeight;
        graphics2.drawString("Strength", textX, textY);
        textY += lineHeight;
        graphics2.drawString("Attack", textX, textY);
        textY += lineHeight;
        graphics2.drawString("Defense", textX, textY);
        textY += lineHeight;
        graphics2.drawString("Exp", textX, textY);
        textY += lineHeight;
        graphics2.drawString("Coins", textX, textY);
        textY += lineHeight + 10;
        graphics2.drawString("Weapon", textX, textY);
        textY += lineHeight + 15;
        graphics2.drawString("Shield", textX, textY);
        textY += lineHeight;
        
        //DISPLAYING VALUES
        int endX = (x + width) - 30;
        
        //RESET textY
        textY = y + p.tileSize;
        String attributeValue;
        
        attributeValue = String.valueOf(p.player.level);
        textX = getRightAlignedX(attributeValue, endX);
        graphics2.drawString(attributeValue, textX, textY);
        textY += lineHeight;
        
        attributeValue = String.valueOf(p.player.health + "/" + p.player.maxHealth);
        textX = getRightAlignedX(attributeValue, endX);
        graphics2.drawString(attributeValue, textX, textY);
        textY += lineHeight;
        
        attributeValue = String.valueOf(p.player.projectileAmount + "/" + p.player.maxProjectileAmount);
        textX = getRightAlignedX(attributeValue, endX);
        graphics2.drawString(attributeValue, textX, textY);
        textY += lineHeight;
        
        attributeValue = String.valueOf(p.player.strength);
        textX = getRightAlignedX(attributeValue, endX);
        graphics2.drawString(attributeValue, textX, textY);
        textY += lineHeight;
        
        attributeValue = String.valueOf(p.player.attack);
        textX = getRightAlignedX(attributeValue, endX);
        graphics2.drawString(attributeValue, textX, textY);
        textY += lineHeight;
        
        attributeValue = String.valueOf(p.player.defense);
        textX = getRightAlignedX(attributeValue, endX);
        graphics2.drawString(attributeValue, textX, textY);
        textY += lineHeight;
        
        attributeValue = String.valueOf(p.player.exp + "/" + p.player.nextLevelExp);
        textX = getRightAlignedX(attributeValue, endX);
        graphics2.drawString(attributeValue, textX, textY);
        textY += lineHeight;
        
        attributeValue = String.valueOf(p.player.coin);
        textX = getRightAlignedX(attributeValue, endX);
        graphics2.drawString(attributeValue, textX, textY);
        textY += lineHeight;

        graphics2.drawImage(p.player.currentWeapon.down1, endX - p.tileSize, textY - 24, null);
        textY += p.tileSize;
        graphics2.drawImage(p.player.currentShield.down1, endX - p.tileSize, textY - 24, null);
        
        
        
        
    }
    public void drawStoreScreen() {
        switch(subState) {
            case 0: store_select(); break;
            case 1: store_buy(); break;
            case 2: store_sell(); break;
        }
        p.hk.pressEnter = false;
    }
    public void store_select() {
        
        npc.dialogueChapter = 0;
        drawDialogueScreen();
        
        //DRAW OPTIONS WINDOW
        int x = p.tileSize * 13;
        int y = p.tileSize * 5;
        int width = p.tileSize * 3;
        int height = (int)(p.tileSize * 3.5);
        drawSubWindow(x,y,width,height);
        
        //TEXT
        x += p.tileSize;
        y += p.tileSize;
        graphics2.drawString("Buy", x, y);
        if (commandChoice == 0) {
            graphics2.drawString(">", x-24, y);
            if (p.hk.pressEnter == true) {
                subState = 1;
            }
        }
        y += p.tileSize;
        graphics2.drawString("Sell", x, y);
        if (commandChoice == 1) {
            graphics2.drawString(">", x-24, y);
            if (p.hk.pressEnter == true) {
                subState = 2;
            }
        }
        y += p.tileSize;
        graphics2.drawString("Exit", x, y);
        if (commandChoice == 2) {
            graphics2.drawString(">", x-24, y);
            if (p.hk.pressEnter == true) {
                commandChoice = 0;
                npc.startDialogue(npc, 1);
                
            }
        }
        
        
    }
    public void store_buy() {
        //DRAW THE PLAYER'S INVENTORY
        drawInventoryScreen(p.player, false);
        //NPC INVENTORY
        drawInventoryScreen(npc,true);
        
        //DRAW INFO WINDOW
        int x = p.tileSize*2;
        int y = p.tileSize*9;
        int width = p.tileSize*6;
        int height = p.tileSize*2;
        
        drawSubWindow(x,y,width,height);
        graphics2.drawString("[ESC] Back", x+24, y+60);
        
        //DRAW PLAYER COINS WINDOW
        x = p.tileSize*9;
        y = p.tileSize*9;
        width = p.tileSize*6;
        height = p.tileSize*2;
        
        drawSubWindow(x,y,width,height);
        graphics2.drawString("Coins: " + p.player.coin, x+24, y+60);
        
        //DRAW PRICE WINDOW
        int index = getItemSlotIndex(npcSlotCol, npcSlotRow);
        if(index < npc.inventory.size()) {
            x = (int)(p.tileSize*5.5);
            y = (int)(p.tileSize*5.5);
            width = (int)(p.tileSize*2.5);
            height = p.tileSize;
            drawSubWindow(x,y,width,height);
            graphics2.drawImage(coin, x+10,y+8,30,30,null);
            
            int price = npc.inventory.get(index).price;
            String message = "" + price;
            x = getRightAlignedX(message, p.tileSize*8-20);
            graphics2.drawString(message, x, y+32);
            
            //BUYING ITEMS
            if(p.hk.pressEnter == true) {
                
                p.playEffect(4);
                if (npc.inventory.get(index).price > p.player.coin) {
                    subState = 0;
                    npc.startDialogue(npc, 2);
                }
                else {
                    if (p.player.canPickup(npc.inventory.get(index)) == true) {
                        p.player.coin -= npc.inventory.get(index).price;
                    }
                    else {
                        subState = 0;
                        npc.startDialogue(npc, 3);
                    }
                }
            }
        }
    }
    public void store_sell() {
        
        drawInventoryScreen(p.player, true);
        
        int x;
        int y;
        int width;
        int height;
        //DRAW INFO WINDOW
        x = p.tileSize*2;
        y = p.tileSize*9;
        width = p.tileSize*6;
        height = p.tileSize*2;
        
        drawSubWindow(x,y,width,height);
        graphics2.drawString("[ESC] Back", x+24, y+60);
        
        //DRAW PLAYER COINS WINDOW
        x = p.tileSize*9;
        y = p.tileSize*9;
        width = p.tileSize*6;
        height = p.tileSize*2;
        
        drawSubWindow(x,y,width,height);
        graphics2.drawString("Coins: " + p.player.coin, x+24, y+60);
        
        //DRAW PRICE WINDOW
        int index = getItemSlotIndex(playerSlotCol, playerSlotRow);
        if(index < p.player.inventory.size()) {
            x = (int)(p.tileSize*12.5);
            y = (int)(p.tileSize*5.5);
            width = (int)(p.tileSize*2.5);
            height = p.tileSize;
            drawSubWindow(x,y,width,height);
            graphics2.drawImage(coin, x+10,y+8,30,30,null);
            
            int price = p.player.inventory.get(index).price;
            String text = "" + price;
            x = getRightAlignedX(text, p.tileSize*15-20);
            graphics2.drawString(text, x, y+32);
            
            //SELLING ITEMS
            if(p.hk.pressEnter == true) {
                
                p.playEffect(4);
                if (p.player.inventory.get(index) == p.player.currentWeapon ||
                        p.player.inventory.get(index) == p.player.currentShield) {
                    commandChoice = 0;
                    subState = 0;
                    npc.startDialogue(npc, 4);
                    
                }
                else {
                    if (p.player.inventory.get(index).amount > 1) {
                        p.player.inventory.get(index).amount--;
                    }
                    else {
                        p.player.inventory.remove(index);
                    }
                    
                    p.player.coin += price * .75;
                }
            }
        }
    }
    public void drawMonsterHealth() {
    	//MONSTER HEALTH BAR
    	
    	
    	for (int i = 0; i < p.mobs[1].length; i++) {
    		Entity m = p.mobs[p.mapCurrent][i];
    		if (m != null && m.inView()) {
    			if (m.healthBarOn == true && m.isBoss == false){
    	            
    	            //KNOWING THE LENGTH OF THE HEALTH BAR TO REDUCE PER ATTACK
    	            double healthScale = (double)p.tileSize/m.maxHealth;
    	            double healthValue = healthScale * m.health;
    	            
    	            if (healthValue < 0) {
    	            	healthValue = 0;
    	            }
    	            //DISPLAYING IT ABOVE THE MONSTER
    	            graphics2.setColor(Color.black);
    	            graphics2.fillRect(m.getXForScreen() - 1, m.getYForScreen() - 16, p.tileSize + 2, 12);
    	            graphics2.setColor(new Color(255,0,30));
    	            graphics2.fillRect(m.getXForScreen(), m.getYForScreen() - 15, (int)healthValue, 10);
    	            
    	            
    	            //TURNS ON/OFF HEALTH BAR IF 10 SECONDS PASS AND YOU DONT HIT THE MONSTER
    	            m.healthCount++;
    	            if (m.healthCount > 600){
    	            	m.healthCount = 0;
    	            	m.healthBarOn = false;
    	            }
    	        }
    			else if (m.isBoss == true) {
    				int x = (p.screenWidth/2) - (p.tileSize*4);
    				int y = p.tileSize * 11;
    				double healthScale = (double)p.tileSize*8/m.maxHealth;
    	            double healthValue = healthScale * m.health;
    	            
    	           
    	            
    	            
    	            graphics2.setColor(Color.BLACK);
    	            graphics2.fillRect(x-2,y-2, p.tileSize*8 + 2, 24);
    	            if (m instanceof MON_DemonLord) {
    	            	graphics2.setColor(new Color(255,69,0));
        	            graphics2.fillRect(x,y, (int)healthValue, 18);
    	            }
    	            else if (m instanceof MON_GrassLord){
    	            	graphics2.setColor(new Color(0,255,127));
        	            graphics2.fillRect(x,y, (int)healthValue, 18);
    	            }
    	            else if (m instanceof MON_IceLord){
    	            	graphics2.setColor(new Color (135,206,250));
        	            graphics2.fillRect(x,y, (int)healthValue, 18);
    	            }
    	            else if (m instanceof MON_ThunderLord){
    	            	graphics2.setColor(new Color(255,255,0));
        	            graphics2.fillRect(x,y, (int)healthValue, 18);
    	            }
    	            
    	            
    	            
    	            graphics2.setFont(graphics2.getFont().deriveFont(Font.BOLD, 23f));
    	            graphics2.setColor(Color.WHITE);
    	            graphics2.drawString(m.name,x+5, y-10);
    	       
    			}
    		}
    	}
        
    }
    public void drawSubWindow(int x, int y, int width, int height)
    {
        //Using rgb code to create new color black
        //4th number indicates transparency
        Color c = new Color(0, 0, 0, 175);
        graphics2.setColor(c);
        //round rectangle for nice looking dialogue box
        graphics2.fillRoundRect(x, y, width, height, 35, 35);
        
        //Frame for window
        //RGB for white
        c = new Color(176,196,222);
        graphics2.setColor(c);
        //Defines the width of the outlines of graphics rendered with Graphics2D
        //5 Pixels
        graphics2.setStroke(new BasicStroke(5));
        graphics2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }
    
    //METHOD TO GET X VALUE FOR CENTER OF THE SCREEN
    public int getCenterX(String text)
    {
        int width = (int) graphics2.getFontMetrics().getStringBounds(text, graphics2).getWidth();
        int centerX = p.screenWidth / 2 - width/2;
        return centerX;
    }
  //METHOD TO GET X VALUE FOR Right OF THE SCREEN
    public int getRightAlignedX(String text, int endX)
    {
        int width = (int) graphics2.getFontMetrics().getStringBounds(text, graphics2).getWidth();
        int rightX = endX - width;
        return rightX;
    }
}
