package entity;

import game.Panel;

public class AfterImage extends Entity {
	public static final String npcName = "AfterImage";
	
	public AfterImage(Panel p) {
		super(p);
		name = npcName;
		getSprite();
	}
	
	public void getSprite()
    {
      
           up1 = setup("/player/bob-u1", p.tileSize, p.tileSize);
           up2 = setup("/player/bob-u2", p.tileSize, p.tileSize);
           down1 = setup("/player/bob-d1", p.tileSize, p.tileSize);
           down2 = setup("/player/bob-d2", p.tileSize, p.tileSize);
           left1 = setup("/player/bob-l1", p.tileSize, p.tileSize);
           left2 = setup("/player/bob-l2", p.tileSize, p.tileSize);
           right1 = setup("/player/bob-r1", p.tileSize, p.tileSize);
           right2 = setup("/player/bob-r2", p.tileSize, p.tileSize);
           
       
    }
}
