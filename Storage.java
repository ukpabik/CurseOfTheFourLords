package data;

import java.io.Serializable;
import java.util.ArrayList;

public class Storage implements Serializable
{
    //SERIALIZABLE INTERFACE ALLOWS CLASS TO BE READ AND WRITTEN FROM
    
    
    //PLAYER STATS
    int level,maxHealth,health,maxProjectileAmount,
    projectileAmount,strength,exp,nextLevelExp,coin;
    
    //PLAYER INVENTORY
    ArrayList<String> itemNames = new ArrayList<>();
    ArrayList<Integer> itemAmounts = new ArrayList<>();
    int currentWeaponSlot,currentShieldSlot;
    
}
