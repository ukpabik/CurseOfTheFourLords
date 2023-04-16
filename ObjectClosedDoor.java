package object;


import entity.Entity;
import game.Panel;

public class ObjectClosedDoor extends Entity
{
    public static final String objName = "Closed Door";
    Panel p;
    public ObjectClosedDoor(Panel p)
    {
        super(p);
        this.p = p;
        name = objName;
        down1 = setup("/objects/closedDoor", p.tileSize, p.tileSize);
        type = type_door;
        collision = true;
        
        solid.x = 0;
        solid.y = 16;
        solid.width = 48;
        solid.height = 32;
        solidAreaDefaultX = solid.x;
        solidAreaDefaultY= solid.y;
        
    }
}
