package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import entity.Entity;
import game.Panel;
import object.ObjectDevilsBlade;
import object.ObjectIronShield;
import object.ObjectRefiller;
import object.ObjectStartSword;
import object.ObjectTitaniumSword;
import object.ObjectWoodShield;

public class SaveAndLoad
{
    Panel p;
    
    public SaveAndLoad(Panel p) {
        this.p = p;
    }
    
    public void saveStats(Storage store) {
    	Storage s = store;
    	//STORE STATS IN STORAGE OBJECT
        s.level = p.player.level;
        s.projectileAmount = p.player.projectileAmount;
        s.maxProjectileAmount = p.player.maxProjectileAmount;
        s.maxHealth = p.player.maxHealth;
        s.health = p.player.health;
        s.strength = p.player.strength;
        s.exp = p.player.exp;
        s.nextLevelExp = p.player.nextLevelExp;
        s.coin = p.player.coin;
    }
    public void saveInvAndEquips(Storage store) {
    	Storage s = store;
    	//PLAYER INVENTORY
        for (int i = 0; i < p.player.inventory.size(); i++) {
            s.itemNames.add(p.player.inventory.get(i).name);
            s.itemAmounts.add(p.player.inventory.get(i).amount);
        }
        
        //EQUIPPABLES
        s.currentWeaponSlot = p.player.getCurrentWeaponSlot();
        s.currentShieldSlot = p.player.getCurrentShieldSlot();
        
    }
    public void save() {
        try
        {
        	Storage s = new Storage();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("save.dat")));
            saveStats(s);
            saveInvAndEquips(s);
            //WRITING THE STORAGE OBJECT TO THE OUTPUT STREAM
            oos.writeObject(s);
            
        }
        catch (Exception e){System.out.println("Save error.");}
    }
    public void loadStats(Storage store) {
    	Storage s = store;
    	//STATS
        p.player.level = s.level;
        p.player.projectileAmount = s.projectileAmount;
        p.player.maxProjectileAmount = s.maxProjectileAmount;
        p.player.maxHealth = s.maxHealth;
        p.player.health = s.health;
        p.player.strength = s.strength;
        p.player.exp = s.exp;
        p.player.nextLevelExp = s.nextLevelExp;
        p.player.coin = s.coin;
    }
    public void loadInventoryAndEquips(Storage store) {
    	Storage s = store;
    	//INVENTORY
        p.player.inventory.clear();
        for(int i = 0; i < s.itemNames.size(); i++) {
            p.player.inventory.add(p.generator.getObject(s.itemNames.get(i)));
            p.player.inventory.get(i).amount = s.itemAmounts.get(i);
        }
        
        //EQUIPPABLES
        p.player.currentWeapon = p.player.inventory.get(s.currentWeaponSlot);
        p.player.currentShield = p.player.inventory.get(s.currentShieldSlot);
        p.player.getAttack();
        p.player.getDefense();
        p.player.getAttackSprite();
    }
    public void load() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("save.dat")));
            
            //READ WHAT IS IN THE FILE AND GET IT AS A STORAGE OBJECT
            Storage s = (Storage)ois.readObject();
            
            //STATS
            loadStats(s);
            loadInventoryAndEquips(s);
            
        }
        catch(Exception e) {System.out.println("Load error.");}
    }
}

