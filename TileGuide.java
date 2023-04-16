package tile;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import game.Panel;
import game.Toolbox;
public class TileGuide
{
    Panel p;
    
    
    //made public to be used in collision detection
    public Tile[] tiles;
    public int mapTile[][][];
    
    //PATHFINDING
    boolean drawPath = true;
    String line;
    //CONTAINERS
    ArrayList<String> files = new ArrayList<>();
    ArrayList<String> collisionStatus = new ArrayList<>();
    
    public TileGuide(Panel p)
    {
        this.p = p;
        
        InputStream is = getClass().getResourceAsStream("/maps/tiledata.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        
        
        String brLine;
        
        try
        {
            while((brLine = br.readLine()) != null) {
                files.add(brLine);
                collisionStatus.add(br.readLine());
            }
            br.close();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        tiles = new Tile[files.size()];
        getTile();
        is = getClass().getResourceAsStream("/maps/mainMap.txt");
        br = new BufferedReader(new InputStreamReader(is));
        try {
            String brLine2 = br.readLine();
            String maximumTiles[] = brLine2.split(" ");
             
            p.maximumWCol = maximumTiles.length;
            p.maximumWRow = maximumTiles.length;
            mapTile = new int[p.maxMap][p.maximumWCol][p.maximumWRow];
            br.close();
            
        }
        catch(IOException e) {
            System.out.println("Exception");
        }
        loadAllMaps();
        
    }
    public void loadAllMaps() {
    	loadMap("/maps/mainMap.txt", 0);
        loadMap("/maps/grassmap.txt", 1);
        loadMap("/maps/lavamap.txt", 2);
        loadMap("/maps/icemap.txt", 3);
        loadMap("/maps/thundermap.txt", 4);
        loadMap("/maps/grassdungeon.txt", 5);
        loadMap("/maps/thunderdungeon.txt", 6);
        loadMap("/maps/lavadungeon.txt", 7);
        loadMap("/maps/icedungeon.txt", 8);
    }
    
    public void loadMap(String path, int map)
    {
        try
        {
            
            //gets input from text file and reads it
            InputStream input = getClass().getResourceAsStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            
            int column = 0;
            int row = 0;
            
            while(column < p.maximumWCol && row < p.maximumWRow) 
            {
                //readLine method reads the next line of text
                String readerLine = reader.readLine();
                
                while(column < p.maximumWCol) 
                {
                    
                    //splits the file by spaces to get numbers individually
                    String[] fileInts = readerLine.split(" ");
                    //changes numbers in file to ints
                    int number = Integer.parseInt(fileInts[column]);
                    
                    mapTile[map][column][row] = number;
                    column++;
                }
                if (column == p.maximumWCol) 
                {
                    column = 0;
                    row++;
                }
            }
            //close the buffered reader to stop getting input
            reader.close();
        }catch (Exception e)
        {
            
        }
    }
    
    
    public void getTile()
    {
        for (int i = 0; i < files.size(); i++) {
            String name;
            boolean collision;
            
            //Get file name
            name = files.get(i);
            
            //Get collision info
            if (collisionStatus.get(i).equals("true")) {
                collision = true;
            }
            else {
                collision = false;
            }
            setup(i, name, collision);
            
        }
        
    }
    
    public void setup(int index, String imageName, boolean collision)
    {
        Toolbox t = new Toolbox();
        
        try
        {
            //DEBUGGING TO SCALE IMAGE QUICKER
            tiles[index] = new Tile();
            tiles[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName));
            tiles[index].image = t.scaleImage(tiles[index].image, p.tileSize, p.tileSize);
            tiles[index].collision = collision;
            
            
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public void draw(Graphics2D graph2)
    {
        //automate the drawing process using while loop
        
        int worldColumn = 0, worldRow = 0;
        
        
        while(worldColumn < p.maximumWCol && worldRow < p.maximumWRow)
        {
            //extracts tile number from map file
            int tileNumber = mapTile[p.mapCurrent][worldColumn][worldRow];
            
            //camera function using playermodel made in panel class
            int x = worldColumn * p.tileSize; //checks x and y position of tile in world
            int y = worldRow * p.tileSize;
            int xScreen = x - p.player.worldX + p.player.screenX; //places character in middle of screen
            int yScreen = y - p.player.worldY + p.player.screenY;
            
            //this if statement makes the game not draw tiles that arent on the screen
            //improves game performance/map rendering speed
            if (x > p.player.worldX - p.player.screenX - p.tileSize && 
                x < p.player.worldX + p.player.screenX + p.tileSize &&
                y > p.player.worldY - p.player.screenY - p.tileSize &&
                y < p.player.worldY + p.player.screenY + p.tileSize)
                //add tile size to make sure black bars don't appear
            {
                //draws each row and column
                graph2.drawImage(tiles[tileNumber].image, xScreen, yScreen, null);
            }
            
            worldColumn++;
            
            if (worldColumn == p.maximumWCol)
            {
                worldColumn = 0;
                
                worldRow++;
               
            }
        }
        
        //FOR TRACING THE PATH 
        
//        if (drawPath == true) {
//            graph2.setColor(new Color(255,0,0, 40));
//            for (int i = 0; i < p.pf.pathList.size(); i++) {
//                
//                int xWorld = p.pf.pathList.get(i).col * p.TILESIZE2;
//                int yWorld = p.pf.pathList.get(i).row * p.TILESIZE2;
//                int xScreen = xWorld - p.player.xWorld + p.player.xScreen;
//                int yScreen = yWorld - p.player.yWorld + p.player.yScreen;
//                
//                graph2.fillRect(xScreen, yScreen, p.TILESIZE2, p.TILESIZE2);
//            }
//        }
    }
}
