package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class HandleKey implements KeyListener
{
    Panel p;
    
    //KEYS
    public boolean pressUp, pressDown, pressRight, 
        pressLeft, pressEnter, pressShootKey, pressSpace;
    
    //DIALOGUE
    public int dialogueCount = 0;
    
    //DEBUGGING
    public boolean checkDrawTime = false;
    public boolean godModeOn = false;
    
    
    
    public HandleKey(Panel p)
    {
        this.p = p;
    }
    @Override
    public void keyTyped(KeyEvent e)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        int code = e.getKeyCode();
        //TITLESTATE
        if (p.gameState == p.titleState) 
        {
            titleState(code);
            
        }
        //PLAYSTATE
        else if (p.gameState == p.playState)
        {
            playState(code);
        }
        //PAUSE STATE
        else if (p.gameState == p.pauseState)
        {
            pauseState(code);
        }
        //DIALOGUE STATE
        else if (p.gameState == p.dialogueState || p.gameState == p.cutsceneState) 
        {
            dialogueState(code);
        }
        
        //CHARACTER STATE
        else if (p.gameState == p.characterState)
        {
            characterState(code);
        }
        //OPTIONS STATE
        else if (p.gameState == p.optionsState)
        {
            optionsState(code);
        }
        
        //GAME OVER STATE
        else if (p.gameState == p.gameOverState)
        {
            gameOverState(code);
        }
        //STORE STATE
        else if (p.gameState == p.storeState)
        {
            storeState(code);
        }
        
        else if (p.gameState == p.mapState)
        {
            mapState(code);
        }
        
        
        
        
        
        
        
    }
    public void storeState(int code) {
        if (code == KeyEvent.VK_ENTER) {
            pressEnter = true;
        }
        if (p.ui.subState == 0) {
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                p.ui.commandChoice--;
                if (p.ui.commandChoice < 0) {
                    p.ui.commandChoice = 2;
                }
                p.playEffect(4);
            }
            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                p.ui.commandChoice++;
                if (p.ui.commandChoice > 2) {
                    p.ui.commandChoice = 0;
                }
                p.playEffect(4);
            }
        }
        
        if (p.ui.subState == 1) {
            npcInventory(code);
            if (code == KeyEvent.VK_ESCAPE) {
                p.ui.subState = 0;
            }
        }
        if (p.ui.subState == 2) {
            playerInventory(code);
            if (code == KeyEvent.VK_ESCAPE) {
                p.ui.subState = 0;
            }
        }
    }
    public void titleState(int code)
    {
        if (p.ui.titleScreenState == 0)
        {
            if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W)
            {
                p.playEffect(4);
                p.ui.commandChoice--;
                if (p.ui.commandChoice < 0)
                {
                    p.ui.commandChoice = 2;
                }
            }
            if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S)
            {
                p.playEffect(4);
                p.ui.commandChoice++;
                if (p.ui.commandChoice > 2)
                {
                    p.ui.commandChoice = 0;
                }
            }
            if (code == KeyEvent.VK_ENTER) 
            {
                p.playEffect(4);
                if (p.ui.commandChoice == 0)
                {
                    p.gameState = p.playState;
                    p.playMusic(0);
                }
                if (p.ui.commandChoice == 1)
                {
                    p.saveLoad.load();
                    p.gameState = p.playState;
                    p.playMusic(0);
                }
                if (p.ui.commandChoice == 2)
                {
                    System.exit(0);                
                    
                }
            }
        
        
            }
        }
    public void gameOverState(int code) {
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            p.ui.commandChoice--;
            if (p.ui.commandChoice < 0) {
                p.ui.commandChoice = 1;
            }
            p.playEffect(4);
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            p.ui.commandChoice++;
            if (p.ui.commandChoice > 1) {
                p.ui.commandChoice = 0;
            }
            p.playEffect(4);
        }
        if (code == KeyEvent.VK_ENTER) {
            if (p.ui.commandChoice == 0) {
                p.playEffect(4);
                p.gameState = p.playState;
                p.resetGame(false);
                p.playMusic(0);
            }
            if (p.ui.commandChoice == 1) {
                p.playEffect(4);
                p.gameState = p.titleState;
                p.resetGame(true);
                p.ui.commandChoice = 0;
            }
        }
    }
    
    public void optionsState(int code) {
        if(code == KeyEvent.VK_ESCAPE) {
            p.gameState = p.playState;
        }
        if(code == KeyEvent.VK_ENTER) {
            pressEnter = true;
        }
        
        int maxChoice = 0;
        switch(p.ui.subState) {
            case 0: maxChoice = 4; break;
            case 1: maxChoice = 0; break;
            case 2: maxChoice = 1; break;
        }
        //BACK IN CONTROL MENU
        if (p.ui.subState == 1) {
            if (code == KeyEvent.VK_B) {
                p.ui.subState = 0;
                p.ui.commandChoice = 0;
            }
        }
        //EXIT MENU
        if(p.ui.subState == 2) {
            if (code == KeyEvent.VK_N) {
                p.ui.subState = 0;
                p.ui.commandChoice = 3;
            }
            if (code == KeyEvent.VK_Y) {
                p.ui.subState = 0;
                p.gameState = p.titleState;
                p.resetGame(true);
            }
            
            
        }
        if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W)
        {
            p.ui.commandChoice--;
            p.playEffect(4);
            if(p.ui.commandChoice < 0) {
                p.ui.commandChoice = maxChoice;
            }
        }
        if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S)
        {
            p.ui.commandChoice++;
            p.playEffect(4);
            if(p.ui.commandChoice > maxChoice) {
                p.ui.commandChoice = 0;
            }
        }
        
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            if (p.ui.subState == 0) {
                if (p.ui.commandChoice == 0 && p.music.vScale > 0) {
                    p.music.vScale--;
                    p.music.checkVolume();
                    p.playEffect(4);
                }
                if (p.ui.commandChoice == 1 && p.soundEffect.vScale > 0) {
                    p.soundEffect.vScale--;
                    p.playEffect(4);
                }
            }
        }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            if (p.ui.subState == 0) {
                if (p.ui.commandChoice == 0 && p.music.vScale < 5) {
                    p.music.vScale++;
                    p.music.checkVolume();
                    p.playEffect(4);
                }
                if (p.ui.commandChoice == 1 && p.soundEffect.vScale < 5) {
                    p.soundEffect.vScale++;
                    p.playEffect(4);
                }
            }
        }
    }
    public void playState(int code)
    {
        if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D)
        {
            pressRight = true;
        }
        if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A)
        {
            pressLeft = true;
        }
        if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W)
        {
            pressUp = true;
        }
        if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S)
        {
            pressDown = true;
        }
        //PAUSING THE GAME
        if (code == KeyEvent.VK_P)
        {
            p.gameState = p.pauseState;
        }
        if (code == KeyEvent.VK_C)
        {
            p.gameState = p.characterState;
        }
        if (code == KeyEvent.VK_M)
        {
            p.gameState = p.mapState;
        }
        if (code == KeyEvent.VK_N)
        {
            if (p.map.minimapOn == false) {
                p.map.minimapOn = true;
            }
            else {
                p.map.minimapOn = false;
            }
           
        }
        if (code == KeyEvent.VK_ENTER)
        {
            pressEnter = true;
        }
        
        if (code == KeyEvent.VK_SPACE)
        {
            pressSpace = true;
        }
        
        if (code == KeyEvent.VK_H)
        {
            pressShootKey = true;
        }
        if (code == KeyEvent.VK_ESCAPE)
        {
            p.gameState = p.optionsState;
        }
        
        //DEBUGGING, SHOWS DRAWTIME
//        if (code == KeyEvent.VK_T) 
//        {
//            if (checkDrawTime == false)
//            {
//                checkDrawTime = true;
//            }
//            else if(checkDrawTime == true)
//            {
//                checkDrawTime = false;
//            }
//        }
        
        //FOR GOD MODE, LEAVE OFF WHEN UPLOADING GAME
//        if (code == KeyEvent.VK_G) 
//        {
//            if (godModeOn == false)
//            {
//                godModeOn = true;
//            }
//            else if(godModeOn == true)
//            {
//                godModeOn = false;
//            }
//        }
        if (code == KeyEvent.VK_R) {
            switch(p.mapCurrent) {
                case 0: p.tileGuide.loadMap("/maps/mainMap.txt",0); break;
                case 1: p.tileGuide.loadMap("/maps/grassmap.txt",1); break;
                case 2: p.tileGuide.loadMap("/maps/lavamap.txt",2); break;
                case 3: p.tileGuide.loadMap("/maps/icemap.txt",3); break;
                case 4: p.tileGuide.loadMap("/maps/thundermap.txt",4); break;
            }
        }
    }
    public void pauseState(int code)
    {
        if (code == KeyEvent.VK_P)
        {
            p.gameState = p.playState;
        }
    }
    public void dialogueState(int code)
    {
        if (code == KeyEvent.VK_ENTER) 
        {
            pressEnter = true;
        }
    }
    public void playerInventory(int code) {
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP)
        {
            if (p.ui.playerSlotRow != 0)
            {
                p.playEffect(4);
                p.ui.playerSlotRow--;
            }
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN)
        {
            if (p.ui.playerSlotRow != 3)
            {
                p.playEffect(4);
                p.ui.playerSlotRow++;
            }
        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT)
        {
            if (p.ui.playerSlotCol != 0)
            {
                p.playEffect(4);
                p.ui.playerSlotCol--;
            }
        }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT)
        {
            if (p.ui.playerSlotCol != 4)
            {
                p.playEffect(4);
                p.ui.playerSlotCol++;
            }
        }
        
    }
    public void mapState(int code) {
        if (code == KeyEvent.VK_M) {
            p.gameState = p.playState;
        }
    }
    public void npcInventory(int code) {
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP)
        {
            if (p.ui.npcSlotRow != 0)
            {
                p.playEffect(4);
                p.ui.npcSlotRow--;
            }
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN)
        {
            if (p.ui.npcSlotRow != 3)
            {
                p.playEffect(4);
                p.ui.npcSlotRow++;
            }
        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT)
        {
            if (p.ui.npcSlotCol != 0)
            {
                p.playEffect(4);
                p.ui.npcSlotCol--;
            }
        }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT)
        {
            if (p.ui.npcSlotCol != 4)
            {
                p.playEffect(4);
                p.ui.npcSlotCol++;
            }
        }
        
    }
    public void characterState(int code)
    {
        if (code == KeyEvent.VK_C)
        {
            p.gameState = p.playState;
        }
        if (code == KeyEvent.VK_ENTER) {
            p.playEffect(5);
            p.player.selectItem();
        }
        playerInventory(code);
        
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        // TODO Auto-generated method stub
        int kCode = e.getKeyCode();
        
        if (kCode == KeyEvent.VK_RIGHT || kCode == KeyEvent.VK_D)
        {
            pressRight = false;
        }
        if (kCode == KeyEvent.VK_LEFT || kCode == KeyEvent.VK_A)
        {
            pressLeft = false;
        }
        if (kCode == KeyEvent.VK_UP || kCode == KeyEvent.VK_W)
        {
            pressUp = false;
        }
        if (kCode == KeyEvent.VK_DOWN || kCode == KeyEvent.VK_S)
        {
            pressDown = false;
        }
        if (kCode == KeyEvent.VK_H)
        {
            pressShootKey = false;
        }
        
        if (kCode == KeyEvent.VK_ENTER) {
            pressEnter = false;
        }
        if (kCode == KeyEvent.VK_SPACE) {
            pressSpace = false;
        }
       
    }

}
