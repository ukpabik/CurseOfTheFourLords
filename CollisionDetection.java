package game;

import entity.Entity;

public class CollisionDetection
{
    
    Panel p;
    
    public CollisionDetection(Panel p)
    {
        this.p = p;
    }
    
    //need to check the world X and world Y on the solid area of the character
    public void detectCollision(Entity en)
    {
        int enLeftX = en.worldX + en.solid.x, enRightX = en.worldX + en.solid.x + en.solid.width;
        int enUpY = en.worldY + en.solid.y, enDownY = en.worldY + en.solid.y + en.solid.height;
        //based on these coordinates, determine the column and row
        
        int enLeftCol = enLeftX / p.tileSize, enRightCol = enRightX / p.tileSize,
        		enUpRow = enUpY / p.tileSize, enDownRow = enDownY / p.tileSize;
        
        
        //used for checking the two possible tiles interfering with collision
        int tile1, tile2;
        
        String direction = en.direction;
        if (en.kb == true) {
            direction = en.knockbackDirection;
        }
        
        switch(direction)
        {
            case "up":
                //adding speed to predict where player will be after movement
                enUpRow = (enUpY - en.speed) / p.tileSize;
                
                tile1 = p.tileGuide.mapTile[p.mapCurrent][enLeftCol][enUpRow];
                tile2 = p.tileGuide.mapTile[p.mapCurrent][enRightCol][enUpRow];
                
                if (p.tileGuide.tiles[tile1].collision == true || p.tileGuide.tiles[tile2].collision == true)
                {
                    en.collisionState = true;
                }
                break;
            case "down":
                enDownRow = (enDownY + en.speed) / p.tileSize;
                
                tile1 = p.tileGuide.mapTile[p.mapCurrent][enLeftCol][enDownRow];
                tile2 = p.tileGuide.mapTile[p.mapCurrent][enRightCol][enDownRow];
                
                if (p.tileGuide.tiles[tile1].collision == true || p.tileGuide.tiles[tile2].collision == true)
                {
                    en.collisionState = true;
                }
                break;
            case "left":
                enLeftCol = (enLeftX - en.speed) / p.tileSize;
                
                tile1 = p.tileGuide.mapTile[p.mapCurrent][enLeftCol][enUpRow];
                tile2 = p.tileGuide.mapTile[p.mapCurrent][enLeftCol][enDownRow];
                
                if (p.tileGuide.tiles[tile1].collision == true || p.tileGuide.tiles[tile2].collision == true)
                {
                    en.collisionState = true;
                }
                break;
            case "right":
                enRightCol = (enRightX + en.speed) / p.tileSize;
                
                tile1 = p.tileGuide.mapTile[p.mapCurrent][enRightCol][enUpRow];
                tile2 = p.tileGuide.mapTile[p.mapCurrent][enRightCol][enDownRow];
                
                if (p.tileGuide.tiles[tile1].collision == true || p.tileGuide.tiles[tile2].collision == true)
                {
                    en.collisionState = true;
                }
                break;
                
        }
        
        
        
        
    }
    public int checkObject(Entity en, boolean player)
    {
        int index = 999;
        
        String direction = en.direction;
        if (en.kb == true) {
            direction = en.knockbackDirection;
        }
        
        for (int i = 0; i < p.object[1].length; i++)
        {
            if (p.object[p.mapCurrent][i] != null)
            {
                //get entity's solid area positional values
                
                en.solid.x = en.worldX + en.solid.x; // need to reset these values at end of switch 
                en.solid.y = en.worldY + en.solid.y;
                
                //get the object's solid area positional values
                
                p.object[p.mapCurrent][i].solid.x = p.object[p.mapCurrent][i].worldX + p.object[p.mapCurrent][i].solid.x;
                p.object[p.mapCurrent][i].solid.y = p.object[p.mapCurrent][i].worldY + p.object[p.mapCurrent][i].solid.y;
                
                switch(direction) 
                {
                    case "up":
                        //check where the entity will be after it moved
                        en.solid.y -= en.speed;
                        break;
                    case "down":
                        en.solid.y += en.speed;
                        break;
                    case "left":
                        en.solid.x -= en.speed;
                        break;
                    case "right":
                        en.solid.x += en.speed;
                        break;
                }
                //checks to see if the two rectangles are colliding
                if (en.solid.intersects(p.object[p.mapCurrent][i].solid))
                {
                    if (p.object[p.mapCurrent][i].collision == true)
                    {
                        en.collisionState = true;
                    }
                    if (player == true)
                    {
                        index = i;
                    }
                }
                //resetting collision values to default
                en.solid.x = en.solidAreaDefaultX;
                en.solid.y = en.solidAreaDefaultY;
                p.object[p.mapCurrent][i].solid.x = en.solidAreaDefaultX;
                p.object[p.mapCurrent][i].solid.y = en.solidAreaDefaultY;
                
            }
        }
        
        //return the index of the object the player is hitting
        return index;
    }
    public int checkEntity(Entity en, Entity[][] target)
    {
        int index = 999;
        
        String direction = en.direction;
        if (en.kb == true) {
            direction = en.knockbackDirection;
        }
        
        for (int i = 0; i < target[1].length; i++)
        {
            if (target[p.mapCurrent][i] != null)
            {
                //get entity's solid area positional values
                
                en.solid.x = en.worldX + en.solid.x; // need to reset these values at end of switch 
                en.solid.y = en.worldY + en.solid.y;
                
                //get the object's solid area positional values
                
                target[p.mapCurrent][i].solid.x = target[p.mapCurrent][i].worldX + target[p.mapCurrent][i].solid.x;
                target[p.mapCurrent][i].solid.y = target[p.mapCurrent][i].worldY + target[p.mapCurrent][i].solid.y;
                
                switch(direction) 
                {
                    case "up":
                        //check where the entity will be after it moved
                        en.solid.y -= en.speed;
                        break;
                    case "down":
                        en.solid.y += en.speed;
                        
                        break;
                    case "left":
                        en.solid.x -= en.speed;
                        
                        break;
                    case "right":
                        en.solid.x += en.speed;
                        
                        break;
                }
                //checks to see if the two rectangles are colliding
                if (en.solid.intersects(target[p.mapCurrent][i].solid))
                {
                    if (target[p.mapCurrent][i] != en)
                    {
                        en.collisionState = true;
                        index = i;
                    }
                }
                //resetting collision values to default
                en.solid.x = en.solidAreaDefaultX;
                en.solid.y = en.solidAreaDefaultY;
                target[p.mapCurrent][i].solid.x = en.solidAreaDefaultX;
                target[p.mapCurrent][i].solid.y = en.solidAreaDefaultY;
                
            }
        }
        
        //return the index of the object the player is hitting
        return index;
    }
    
    public boolean checkPlayer(Entity en)
    {
        boolean playerContact = false;
        
      //get entity's solid area positional values
        
        en.solid.x = en.worldX + en.solid.x; // need to reset these values at end of switch 
        en.solid.y = en.worldY + en.solid.y;
        
        //get the object's solid area positional values
        
        p.player.solid.x = p.player.worldX + p.player.solid.x;
        p.player.solid.y = p.player.worldY + p.player.solid.y;
        
        switch(en.direction) 
        {
            case "up":
                //check where the entity will be after it moved
                en.solid.y -= en.speed;
                
                break;
            case "down":
                en.solid.y += en.speed;
                
                break;
            case "left":
                en.solid.x -= en.speed;
                
                break;
            case "right":
                en.solid.x += en.speed;
                
                break;
        }
        //checks to see if the two rectangles are colliding
        if (en.solid.intersects(p.player.solid))
        {
            en.collisionState = true;
            playerContact = true;
        }
        //resetting collision values to default
        en.solid.x = en.solidAreaDefaultX;
        en.solid.y = en.solidAreaDefaultY;
        p.player.solid.x = en.solidAreaDefaultX;
        p.player.solid.y = en.solidAreaDefaultY;
        
        return playerContact;
    }
}
    
    


