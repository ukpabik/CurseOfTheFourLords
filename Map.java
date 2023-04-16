package tile;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.Panel;

public class Map extends TileGuide
{
    Panel p;
    
    //MAP
    BufferedImage world[];
    public boolean minimapOn = false;
    
    public Map(Panel p) {
        super(p);
        this.p = p;
        createWorldMap();
    }
    
    public void createWorldMap() {
        world = new BufferedImage[p.maxMap];
        int mapWidth = p.tileSize * p.maximumWCol;
        int mapHeight = p.tileSize * p.maximumWRow;
        
        for(int i = 0; i < p.maxMap; i++) {
            //ATTACHED GRAPHICS2D TO WORLDMAP BUFFERED IMAGE
            world[i] = new BufferedImage(mapWidth, mapHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graph2 = (Graphics2D)world[i].createGraphics();
            
            int column = 0;
            int row = 0;
            
            while(column < p.maximumWCol && row < p.maximumWRow) {
                int tileNum = mapTile[i][column][row];
                int x = p.tileSize * column;
                int y = p.tileSize * row;
                graph2.drawImage(tiles[tileNum].image, x, y, null);
                
                column++;
                if (column == p.maximumWCol) {
                    column = 0;
                    row++;
                }
            }
            graph2.dispose();
        }
        
    }
    public void drawMapScreen(Graphics2D graph2) {
        //BACKGROUND OF THE MAP
        graph2.setColor(Color.black);
        graph2.fillRect(0, 0, p.screenWidth, p.screenHeight);
        
        //MAP SIZE
        int width = 500;
        int height = 500;
        int x = p.screenWidth/2 - width/2;
        int y = p.screenHeight/2 - height/2;
        
        graph2.drawImage(world[p.mapCurrent], x, y, width, height, null);
        
        //DRAW THE PLAYER ON THE MAP
        double scale = (double)(p.tileSize * p.maximumWCol)/width;
        int px = (int)(x + p.player.worldX/scale);
        int py = (int)(y + p.player.worldY/scale);
        int size = (int)(p.tileSize/scale);
        graph2.drawImage(p.player.down1, px, py, size, size, null);
        
        graph2.setFont(p.ui.pixeloid.deriveFont(15f));
        graph2.setColor(Color.white);
        graph2.drawString("Press M to exit", 750, 550);
    }
    public void drawMinimap(Graphics2D graph2) {
        if (minimapOn == true) {
            //MAP SIZE
            int width = 200;
            int height = 200;
            int x = p.screenWidth - width - 50;
            int y = 50;
            
            //SET TRANSPARENCY OF MAP
            graph2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
            graph2.drawImage(world[p.mapCurrent], x, y, width, height, null);
            //DRAW THE PLAYER ON THE MAP
            double scale = (double)(p.tileSize * p.maximumWCol)/width;
            int px = (int)(x + p.player.worldX/scale);
            int py = (int)(y + p.player.worldY/scale);
            int size = (int)(p.tileSize/4);
            graph2.drawImage(p.player.down1, px, py, size, size, null);
            
            graph2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
        
    }
}
