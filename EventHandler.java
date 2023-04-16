package game;

import data.BattleProgress;

public class EventHandler
{
    Panel p;
    EventRect[][][] eventRect;
    int previousEventX, previousEventY, tempMap, tempCol, tempRow;
    boolean canTouchEvent = true;
    
    
    
    public EventHandler(Panel p)
    {
        this.p = p;
        //MAKES EVENT TILE ON EVERY TILE ON THE MAP
        eventRect = new EventRect[p.maxMap][p.maximumWCol][p.maximumWRow];
        
        int map = 0;
        int col = 0;
        int row = 0;
        
        while(map < p.maxMap && col < p.maximumWCol && row < p.maximumWRow) 
        {
          //MAKING EVENT RECT SMALLER SO THAT IT DOESNT TRIGGER IF EDGE OF TILE HITS IT
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height = 2;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;
            
            col++;
            if(col == p.maximumWCol) 
            {
                col = 0;
                row++;
                
                if (row == p.maximumWRow) {
                    row = 0;
                    map++;
                }
            }
        }
    }
    
    public void checkEvent()
    {
        //POSITION OF EVENT ON MAP
        
        //CHECK IF THE PLAYER IS MORE THAN ONE TILE AWAY FROM LAST EVENT
        
        //CHECKS ABS VALUE OF DISTANCE
        int xDistance = Math.abs(p.player.worldX - previousEventX);
        int yDistance = Math.abs(p.player.worldY - previousEventY);
        
        //MATH.MAX RETURNS HIGHER VALUE OF THE TWO
        int distance = Math.max(xDistance, yDistance);
        
        if (distance > p.tileSize) 
        {
            canTouchEvent = true;
        }
        
        //CHECK EVENTS AFTER CHECKING CAN TOUCH EVENT
        if (canTouchEvent = true)
        {
            //ICE PORTAL
            if (hit(0,1,1,"any") == true){teleport(3, 26, 26, p.outside);}
            else if (hit(3,1,1,"any") == true || hit(2,1,1,"any") == true ||
                hit(1,1,1,"any") == true || hit(4,1,1,"any") == true)
            {teleport(0, 26, 26,p.outside);}
            
            //ICE DUNGEON
            else if (hit(3,31,19,"any")) {teleport(8,30,14,p.dungeon);}
            else if (hit(8,28,13,"any")) {teleport(3,26,26,p.outside);}
            
            //LAVA PORTAL
            if (hit(0,1,48,"any") == true){teleport(2, 26, 26,p.outside);}
            else if (hit(2,1,48,"any") == true || hit(1,1,48,"any") == true || 
                hit(4,1,48,"any") == true || hit(3,1,48,"any") == true)
            {teleport(0, 26, 26,p.outside);}
            //LAVA DUNGEON
            else if (hit(2,31,19,"any")) {teleport(7,30,14,p.dungeon);}
            else if (hit(7,28,13,"any")) {teleport(2,26,26,p.outside);}
            
            //GRASS PORTAL
            if (hit(0,48,1,"any") == true){teleport(1, 26, 26,p.outside);}
            else if (hit(1,48,1,"any") == true || hit(2,48,1,"any") == true || 
                hit(3,48,1,"any") == true || hit(4,48,1,"any") == true)
            {teleport(0, 26, 26,p.outside);}
            //GRASS DUNGEON
            else if(hit(5,28,13,"any")) {teleport(1,26,26,p.outside);}
            else if (hit(1,31,20, "any")) {teleport(5,30,14,p.dungeon);}
            
            //THUNDER PORTAL
            if (hit(0,48,48,"any") == true){teleport(4, 26, 26,p.outside);}
            else if (hit(4,48,48,"any") == true || hit(3,48,48,"any") == true ||
                hit(2,48,48,"any") == true || hit(1,48,48,"any") == true)
            {teleport(0, 26, 26,p.outside);}
            //THUNDER DUNGEON
            else if (hit(4,31,19,"any")) {teleport(6,30,14,p.dungeon);}
            else if (hit(6,28,13,"any")) {teleport(4,26,26,p.outside);}
            
            //CUTSCENES
            else if(hit(7,27,18,"any")) {demonLord();}
            else if(hit(8,27,20,"any")) {iceLord();}
            else if(hit(6,26,22,"any")) {thunderLord();}
            else if(hit(5,26,21,"any")) {grassLord();}
            //EVENTUALLY ADD MORE EVENTS
        }
        
    }
    public void demonLord() {
    	if (p.battleStart == false && BattleProgress.demonLordKilled == false) {
    		p.gameState = p.cutsceneState;
    		p.cs.scene = p.cs.demonLord;
    	}
    }
    public void grassLord() {
    	if (p.battleStart == false && BattleProgress.grassLordKilled == false) {
    		p.gameState = p.cutsceneState;
    		p.cs.scene = p.cs.grassLord;
    	}
		
	}
	public void thunderLord() {
		if (p.battleStart == false && BattleProgress.thunderLordKilled == false) {
    		p.gameState = p.cutsceneState;
    		p.cs.scene = p.cs.thunderLord;
    	}
		
	}
	public void iceLord() {
		if (p.battleStart == false && BattleProgress.iceLordKilled == false) {
    		p.gameState = p.cutsceneState;
    		p.cs.scene = p.cs.iceLord;
    	}
		
	}
	public boolean hit(int map, int col, int row, String requiredDirection)
    {
        boolean hit = false;
        
        if (map == p.mapCurrent) {
          //getting players current solid area positions
            p.player.solid.x = p.player.worldX + p.player.solid.x;
            p.player.solid.y = p.player.worldY + p.player.solid.y;
            eventRect[map][col][row].x = col * p.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row * p.tileSize + eventRect[map][col][row].y;
            
            //CHECKING EVENT.DONE FOR FALSE FOR ONE TIME EVENTS
            if (p.player.solid.intersects(eventRect[map][col][row]) && eventRect[map][col][row].eventComplete == false)
            {
                if (p.player.direction.contentEquals(requiredDirection) || requiredDirection.contentEquals("any"))
                {
                    hit = true;
                    
                    previousEventX = p.player.worldX;
                    previousEventY = p.player.worldY;
                }
            }
            p.player.solid.x = p.player.solidAreaDefaultX;
            p.player.solid.y = p.player.solidAreaDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
            
        }
        return hit;
        
        
    }
	public void changeLocation(int map, int col, int row) {
		tempMap = map;
        tempCol = col;
        tempRow = row;
	}
    public void teleport(int map, int col, int row, int area) {
        p.gameState = p.transitionState;
        p.nextArea = area;
        
        changeLocation(map, col, row);
        canTouchEvent = false;
        p.playEffect(11);
    }
}
