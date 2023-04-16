package game;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.JPanel;

import ai.PathFinder;
import data.SaveAndLoad;
import entity.Entity;
import entity.PlayerModel;
import environment.EnvironmentGuide;
import tile.Map;
import tile.TileGuide;

public class Panel extends JPanel implements Runnable
{
    //Making the screen settings
    
    public final int oldTileSize = 16; //16 x 16 size of tiles/characters
    final int SCALE = 3; // scaling by 3 making 16 x 16 into 48 x 48
    public final int tileSize = oldTileSize * SCALE;
    public final int MAXSCREENCOL = 20;
    public final int MAXSCREENROW = 12;
    public final int screenWidth = tileSize * MAXSCREENCOL; //960 pixels
    public final int screenHeight = tileSize * MAXSCREENROW; //576 pixels
    public int maximumWCol;
    public int maximumWRow;
    
    
    //MAP SETTINGS
    Map map = new Map(this);
    public final int maxMap = 10;
    public int mapCurrent = 0;
    public int currentArea;
    public final int outside = 50;
    public final int dungeon = 51;
    public int nextArea;
    
    //Graphics settings
    Graphics2D graph2;
    int FPS = 60;
    
    //GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int optionsState = 5;
    public final int gameOverState = 6;
    public final int transitionState = 7;
    public final int storeState = 8;
    public final int mapState = 9;
    public final int cutsceneState = 10;
    public boolean battleStart = false;
    
    //CONTAINER CLASSES
    public Entity mobs[][] = new Entity[maxMap][30];
    public Entity npc[][] = new Entity[maxMap][10];
    public Entity object[][] = new Entity[maxMap][20];
    public Entity projectile[][] = new Entity[maxMap][20];
    ArrayList<Entity> entityList = new ArrayList<>();
    public ArrayList<Entity> particleList = new ArrayList<>();
    
    //OTHER CLASSES
    public Cutscenes cs = new Cutscenes(this);
    public PathFinder pf = new PathFinder(this);
    public ObjectPlacer op = new ObjectPlacer(this);
    public SaveAndLoad saveLoad = new SaveAndLoad(this);
    public EntityGenerator generator = new EntityGenerator(this);
    public Sounds music = new Sounds();
    public Sounds soundEffect = new Sounds();
    public UI ui = new UI(this);
    public EventHandler eh = new EventHandler(this);
    public CollisionDetection cd = new CollisionDetection(this);
    public HandleKey hk = new HandleKey(this);
    public PlayerModel player = new PlayerModel(this, hk);
    public TileGuide tileGuide = new TileGuide(this);
    EnvironmentGuide eGuide = new EnvironmentGuide(this);
    Config config = new Config(this);
    Thread thread;
    
    public Panel()
    {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(hk);
        this.setFocusable(true);
        Toolkit.getDefaultToolkit().sync();
    }
    
    public void setGame() //make sure to call this before starting the game thread
    {
        
        op.placeObject();
        op.setNPC();
        op.setMonster();
        op.setInteractiveTile();
        eGuide.setup();
        
        //for the ambient background music
        //playMusic(0);
        currentArea = outside;
        gameState = titleState;
        
    }
    
    //starts thread for FPS and constant running of game loop
    public void startThread()
    {
        thread = new Thread(this);
        thread.start();
    }

     public void run()
     {
        double interval = 1000000000/FPS;
        double da = 0;
        long before = System.nanoTime();
        long time;
        int count = 0;
        int timePassed = 0;
        
        while (thread != null)
        {
            
            
            time = System.nanoTime();
            
            da += (time - before) / interval;
            timePassed += (time - before);
            before = time;
            
            if (da >= 1)
            {
                //UPDATE Information
                //Character position, drawing the screen with updated info
                
                update();
                repaint();
                da--;
                count++;
            }
            
            if (timePassed >= 1000000000)
            {
//                System.out.println("FPS: " + counter);
                count = 0;
                timePassed = 0;
            }
        }
     }
        
    
    //updates character position
    public void update()
    {
        
        if (gameState == playState)
        {
            //Player Update
            player.update();
            
            //NPC Update
            for (int i = 0; i < npc[1].length; i++)
            {
                if (npc[mapCurrent][i] != null)
                {
                    npc[mapCurrent][i].update();
                }
            }
            //MONSTER UPDATE
            for (int i = 0; i < mobs[1].length; i++)
            {
                if (mobs[mapCurrent][i] != null)
                {
                    if (mobs[mapCurrent][i].alive == true && mobs[mapCurrent][i].death == false)
                    {
                        mobs[mapCurrent][i].update();
                    }
                    if (mobs[mapCurrent][i].alive == false)
                    {
                        mobs[mapCurrent][i].checkDrop();
                        mobs[mapCurrent][i] = null;
                    }
                }
            }
            //PROJECTILE UPDATE
            for (int i = 0; i < projectile[1].length; i++)
            {
                if (projectile[mapCurrent][i] != null)
                {
                    if (projectile[mapCurrent][i].alive == true)
                    {
                        projectile[mapCurrent][i].update();
                    }
                    if (projectile[mapCurrent][i].alive == false)
                    {
                        projectile[mapCurrent][i] = null;
                    }
                }
            }
            //PARTICLE UPDATES
            for (int i = 0; i < particleList.size(); i++)
            {
                if (particleList.get(i) != null)
                {
                    if (particleList.get(i).alive == true)
                    {
                        particleList.get(i).update();
                    }
                    if (particleList.get(i).alive == false)
                    {
                        particleList.remove(i);
                    }
                }
            }
            
            
            eGuide.update();
            
        }
        else if(gameState == pauseState)
        {
           
            //dont update player info while paused
        }
        
    }
    public void resetGame(boolean restart) {
        currentArea = outside;
        stopMusic();
        player.setDefaultPositions();
        player.restoreStatus();
        op.setNPC();
        op.setMonster();
        player.resetCounter();
        removeTemporary();
        battleStart = false;
        
        
        if (restart == true) {
            player.setDefault();
            player.setItems();
            op.placeObject();
            op.setInteractiveTile();
            eGuide.lighting.resetTime();
        }
        
    }
    //paints character/objects on screen
    public void paintComponent(Graphics graph)
    {
        super.paintComponent(graph);
        Graphics2D graph2 = (Graphics2D)graph;
        
        //DEBUGGING
//        long drawStart = 0;
//        if (hk.checkDrawTime == true)
//        {
//            drawStart = System.nanoTime();
//        }
        
        //TITLE SCREEN
        if (gameState == titleState)
        {
            ui.draw(graph2);
        }
        else if(gameState == mapState) {
            map.drawMapScreen(graph2);
        }
        
        else 
        {
            //TILE
            tileGuide.draw(graph2); //draw the tiles first because it acts as a layer
            
            
            entityList.add(player);
            
            //ADD ENTITIES TO LIST
            for (int i = 0; i < npc[1].length; i++)
            {
                if (npc[mapCurrent][i] != null)
                {
                    entityList.add(npc[mapCurrent][i]);
                }
                
            }
            
            for (int i = 0; i < mobs[1].length; i++)
            {
                if (mobs[mapCurrent][i] != null)
                {
                    entityList.add(mobs[mapCurrent][i]);
                }
                
            }
            
            for (int i = 0; i < object[1].length; i++)
            {
                if (object[mapCurrent][i] != null)
                {
                    entityList.add(object[mapCurrent][i]);
                }
            }
            
            for (int i = 0; i < projectile[1].length; i++)
            {
                if (projectile[mapCurrent][i] != null)
                {
                    entityList.add(projectile[mapCurrent][i]);
                }
            }
            
            for (int i = 0; i < particleList.size(); i++)
            {
                if (particleList.get(i) != null)
                {
                    entityList.add(particleList.get(i));
                }
            }
            
            //SORT
            entityList.sort(Comparator.comparingInt(o -> o.worldY));
            
            
            for (int i = 0; i < entityList.size(); i++)
            {
                entityList.get(i).draw(graph2);
                
            }
            //AFTER DRAWING EVERYTHING, CLEAR LIST
            
            entityList.clear();
            
            //ENVIRONMENT
            eGuide.draw(graph2);
            
            //MINIMAP
            map.drawMinimap(graph2);
            
            
            //UI
            ui.draw(graph2);
            
            //CUTSCENES
            cs.draw(graph2);
            
        }
      //MORE DEBUGGING
//        if (hk.checkDrawTime == true)
//        {
//            long drawEnd = System.nanoTime();
//            long passed = drawEnd - drawStart;
//            graph2.setColor(Color.white);
//            graph2.drawString("DrawTime: " + passed, 10, 400);
//            System.out.println("DrawTime: " + passed);
//            graph2.drawString("God Mode: " + hk.godModeOn, 10, 420);
//        }
        
        
        graph2.dispose();
    }
    public void removeTemporary() {
    	for (int i = 0; i < maxMap; i++) {
    		for (int ii = 0; ii < object[1].length; ii++) {
    			if (object[i][ii] != null && object[i][ii].temporary == true) {
    				object[i][ii] = null;
    			}
    		}
    	}
    }
    public void playMusic(int i)
    {
        music.setFile(i);
        music.play();
        music.loop();
        
    }
    
    public void stopMusic()
    {
        music.stop();
    }
    //usually dont call loop for sound effect because they are short
    public void playEffect(int i)
    {
        soundEffect.setFile(i);
        soundEffect.play();
    }
    
    public void changeArea() {
        if (nextArea != currentArea) {
            stopMusic();
            
            if (nextArea == outside) {
                playMusic(0);
            }
            if (nextArea == dungeon) {
                playMusic(1);
            }
        }
        currentArea = nextArea;
        //MAKES MONSTERS RESPAWN WHEN YOU GO TO NEXT AREA
        op.setMonster();
    }
    
    
    
    
    
    
    
    
    
}
