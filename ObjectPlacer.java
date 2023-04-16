package game;


import data.BattleProgress;
import entity.NPC_EndingPig;
import entity.NPC_PigMerchant;
import entity.NPC_PiggyBryce;
import entity.NPC_SavePig;
import monster.MON_Demon;
import monster.MON_DemonLord;
import monster.MON_Grass;
import monster.MON_GrassLord;
import monster.MON_Ice;
import monster.MON_IceLord;
import monster.MON_Skeleton;
import monster.MON_Thunder;
import monster.MON_ThunderLord;

public class ObjectPlacer
{
    Panel p;
    
    public ObjectPlacer(Panel p)
    {
        this.p = p;
    }
    
    public void setMonster()
    {
        
        int map = 0;
        int i = 0;
        //MAP 0

        
        //MAP 1
        map = 1;
        i = 0;
        p.mobs[map][i] = new MON_Grass(p);
        p.mobs[map][i].worldX = p.tileSize * 18;
        p.mobs[map][i].worldY = p.tileSize * 19;
        i++;
        p.mobs[map][i] = new MON_Grass(p);
        p.mobs[map][i].worldX = p.tileSize * 5;
        p.mobs[map][i].worldY = p.tileSize * 22;
        i++;
        p.mobs[map][i] = new MON_Grass(p);
        p.mobs[map][i].worldX = p.tileSize * 18;
        p.mobs[map][i].worldY = p.tileSize * 32;
        i++;
        p.mobs[map][i] = new MON_Grass(p);
        p.mobs[map][i].worldX = p.tileSize * 15;
        p.mobs[map][i].worldY = p.tileSize * 19;
        i++;
        p.mobs[map][i] = new MON_Grass(p);
        p.mobs[map][i].worldX = p.tileSize * 34;
        p.mobs[map][i].worldY = p.tileSize * 22;
        i++;
        p.mobs[map][i] = new MON_Skeleton(p);
        p.mobs[map][i].worldX = p.tileSize * 38;
        p.mobs[map][i].worldY = p.tileSize * 32;
        i++;
        p.mobs[map][i] = new MON_Skeleton(p);
        p.mobs[map][i].worldX = p.tileSize * 36;
        p.mobs[map][i].worldY = p.tileSize * 15;
        i++;
        
        
        //MAP 2
        map = 2;
        i = 0;
        p.mobs[map][i] = new MON_Demon(p);
        p.mobs[map][i].worldX = p.tileSize * 30;
        p.mobs[map][i].worldY = p.tileSize * 19;
        i++;
        p.mobs[map][i] = new MON_Demon(p);
        p.mobs[map][i].worldX = p.tileSize * 5;
        p.mobs[map][i].worldY = p.tileSize * 22;
        i++;
        p.mobs[map][i] = new MON_Demon(p);
        p.mobs[map][i].worldX = p.tileSize * 18;
        p.mobs[map][i].worldY = p.tileSize * 32;
        i++;
        p.mobs[map][i] = new MON_Demon(p);
        p.mobs[map][i].worldX = p.tileSize * 11;
        p.mobs[map][i].worldY = p.tileSize * 19;
        i++;
        p.mobs[map][i] = new MON_Demon(p);
        p.mobs[map][i].worldX = p.tileSize * 20;
        p.mobs[map][i].worldY = p.tileSize * 22;
        i++;
        p.mobs[map][i] = new MON_Skeleton(p);
        p.mobs[map][i].worldX = p.tileSize * 25;
        p.mobs[map][i].worldY = p.tileSize * 32;
        i++;
        
        p.mobs[map][i] = new MON_Skeleton(p);
        p.mobs[map][i].worldX = p.tileSize * 23;
        p.mobs[map][i].worldY = p.tileSize * 35;
        
        //MAP 3
        map = 3;
        i = 0;
        p.mobs[map][i] = new MON_Ice(p);
        p.mobs[map][i].worldX = p.tileSize * 35;
        p.mobs[map][i].worldY = p.tileSize * 19;
        i++;
        p.mobs[map][i] = new MON_Ice(p);
        p.mobs[map][i].worldX = p.tileSize * 28;
        p.mobs[map][i].worldY = p.tileSize * 22;
        i++;
        p.mobs[map][i] = new MON_Ice(p);
        p.mobs[map][i].worldX = p.tileSize * 37;
        p.mobs[map][i].worldY = p.tileSize * 32;
        i++;
        p.mobs[map][i] = new MON_Ice(p);
        p.mobs[map][i].worldX = p.tileSize * 15;
        p.mobs[map][i].worldY = p.tileSize * 19;
        i++;
        p.mobs[map][i] = new MON_Ice(p);
        p.mobs[map][i].worldX = p.tileSize * 20;
        p.mobs[map][i].worldY = p.tileSize * 22;
        i++;
        p.mobs[map][i] = new MON_Skeleton(p);
        p.mobs[map][i].worldX = p.tileSize * 18;
        p.mobs[map][i].worldY = p.tileSize * 32;
        i++;
        p.mobs[map][i] = new MON_Skeleton(p);
        p.mobs[map][i].worldX = p.tileSize * 23;
        p.mobs[map][i].worldY = p.tileSize * 35;
        
        //MAP 4
        map = 4;
        i = 0;
        p.mobs[map][i] = new MON_Thunder(p);
        p.mobs[map][i].worldX = p.tileSize * 37;
        p.mobs[map][i].worldY = p.tileSize * 19;
        i++;
        p.mobs[map][i] = new MON_Thunder(p);
        p.mobs[map][i].worldX = p.tileSize * 9;
        p.mobs[map][i].worldY = p.tileSize * 22;
        i++;
        p.mobs[map][i] = new MON_Thunder(p);
        p.mobs[map][i].worldX = p.tileSize * 31;
        p.mobs[map][i].worldY = p.tileSize * 32;
        i++;
        p.mobs[map][i] = new MON_Thunder(p);
        p.mobs[map][i].worldX = p.tileSize * 15;
        p.mobs[map][i].worldY = p.tileSize * 19;
        i++;
        p.mobs[map][i] = new MON_Thunder(p);
        p.mobs[map][i].worldX = p.tileSize * 20;
        p.mobs[map][i].worldY = p.tileSize * 22;
        i++;
        p.mobs[map][i] = new MON_Skeleton(p);
        p.mobs[map][i].worldX = p.tileSize * 30;
        p.mobs[map][i].worldY = p.tileSize * 35;
        i++;
        p.mobs[map][i] = new MON_Skeleton(p);
        p.mobs[map][i].worldX = p.tileSize * 23;
        p.mobs[map][i].worldY = p.tileSize * 35;
        i++;
        
        
        
        //MAP 5
        map = 5;
        i = 0;
        if (BattleProgress.grassLordKilled == false) {
        	p.mobs[map][i] = new MON_GrassLord(p);
            p.mobs[map][i].worldX = p.tileSize * 26;
            p.mobs[map][i].worldY = p.tileSize * 34;
        }
        
        //MAP 6
        map = 6;
        i = 0;
        if (BattleProgress.thunderLordKilled == false) {
        	p.mobs[map][i] = new MON_ThunderLord(p);
            p.mobs[map][i].worldX = p.tileSize * 26;
            p.mobs[map][i].worldY = p.tileSize * 34;
        }
        
        //MAP 7
        map = 7;
        i = 0;
        if (BattleProgress.demonLordKilled == false) {
        	p.mobs[map][i] = new MON_DemonLord(p);
            p.mobs[map][i].worldX = p.tileSize * 27;
            p.mobs[map][i].worldY = p.tileSize * 34;
        }
        
        
        //MAP 8
        map = 8;
        i = 0;
        if (BattleProgress.iceLordKilled == false) {
        	 p.mobs[map][i] = new MON_IceLord(p);
             p.mobs[map][i].worldX = p.tileSize * 25;
             p.mobs[map][i].worldY = p.tileSize * 33;
        }
       
    }
    
    public void placeObject()
    {
        int map = 0;
        int i = 0;
        
        
        
    }
        
    public void setNPC()
    {
        int map = 0;
        int i = 0;
        p.npc[map][i] = new NPC_PiggyBryce(p);
        p.npc[map][i].worldX = p.tileSize * 22;
        p.npc[map][i].worldY = p.tileSize * 25;
        i++;
        p.npc[map][i] = new NPC_PigMerchant(p);
        p.npc[map][i].worldX = p.tileSize * 20;
        p.npc[map][i].worldY = p.tileSize * 25;
        i++;
        p.npc[map][i] = new NPC_SavePig(p);
        p.npc[map][i].worldX = p.tileSize * 18;
        p.npc[map][i].worldY = p.tileSize * 25;
        i++;
        p.npc[map][i] = new NPC_EndingPig(p);
        p.npc[map][i].worldX = p.tileSize * 16;
        p.npc[map][i].worldY = p.tileSize * 25;
        i++;
    }
}
