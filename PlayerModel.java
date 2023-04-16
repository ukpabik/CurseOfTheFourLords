package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import game.HandleKey;
import game.Panel;
import object.ObjectAmuletOfLight;
import object.ObjectArrow;
import object.ObjectStartSword;
import object.ObjectWoodShield;

public class PlayerModel extends Entity
{
    Panel p;
    HandleKey hk;
    
    
    //SCREEN POSITION
    public final int screenX, screenY;
    
    //MAKE SURE PLAYER DOESNT ATTACK WHEN INTERACTING WITH OBJECTS, ETC
    public boolean attackCancel = false;
    
    //UPDATING LIGHT SOURCE
    public boolean lightUpdate = false;
    
    //FOR CRITICAL HITS
    int critIndicator;
    
    
    
    
    
    public PlayerModel(Panel p, HandleKey key)
    {
        
        //calling the constructor of the Entity class
        //the entity cannot receive the panel if you don't pass the panel through super
        super(p);
        
        this.p = p;
        this.hk = key;
        //returns the midpoint of the screen
        screenX = p.screenWidth / 2 - (p.tileSize / 2); //subtract half tile size for perfect center
        screenY = p.screenHeight / 2 - (p.tileSize / 2);
        
        //for collision detection on player model
        solid = new Rectangle();
        solid.x = 8;
        solid.y = 16;
        solidAreaDefaultX = solid.x;
        solidAreaDefaultY = solid.y;
        solid.width = 32;
        solid.height = 32;
        
        //CHANGING ATTACK RANGE USING THESE VALUES
        attackArea.width = 40;
        attackArea.height = 40;
        
        setDefault();
        
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
           
           
           
           checkmark = setup("/player/checkmark", p.tileSize, p.tileSize);
           
       
    }
    public void getAttackSprite()
    {
        //SETUP IMAGES SAME WAY AS GETPLAYERSPRITE METHOD
        
        //WIDTH AND HEIGHT WILL BE 32 or 16 DEPENDING ON IF PLAYER IS ATTACKING VERTICAL OR HORIZONTAL
        
        //p.TILESIZE2 * 2 = 32, SO WIDTH = 32 WHEN PLAYER ATTACKING HORIZONTAL, VICE VERSA FOR VERTICAL
        if (currentWeapon.type == type_sword)
        {
            attackUp1 = setup("/player_attacks/bob-swordup1", p.tileSize, p.tileSize * 2);
            attackUp2 = setup("/player_attacks/bob-swordup2", p.tileSize, p.tileSize * 2);
            attackDown1 = setup("/player_attacks/bob-sworddown1", p.tileSize, p.tileSize * 2);
            attackDown2 = setup("/player_attacks/bob-sworddown2", p.tileSize, p.tileSize * 2);
            attackLeft1 = setup("/player_attacks/bob-swordleft1", p.tileSize * 2, p.tileSize);
            attackLeft2 = setup("/player_attacks/bob-swordleft2", p.tileSize * 2, p.tileSize);
            attackRight1 = setup("/player_attacks/bob-swordright1", p.tileSize * 2, p.tileSize);
            attackRight2 = setup("/player_attacks/bob-swordright2", p.tileSize * 2, p.tileSize);
        }
        
        
    }
    public void getGuardSprite()
    {
      
           guardUp = setup("/player/bob-guard-u1", p.tileSize, p.tileSize);
           guardDown = setup("/player/bob-guard-d1", p.tileSize, p.tileSize);
           guardLeft = setup("/player/bob-guard-l1", p.tileSize, p.tileSize);
           guardRight = setup("/player/bob-guard-r1", p.tileSize, p.tileSize);
           
       
    }
       
    public void update()
    {
        if (kb == true) {
          //check tile collision state
            collisionState = false;
            p.cd.detectCollision(this);
            
            //check object collision state
            p.cd.checkObject(this, true);
            
            
            //check npc collision state
            p.cd.checkEntity(this, p.npc);
            
            
            //check monster collision
            p.cd.checkEntity(this, p.mobs);
             
            
            if (collisionState == true) {
                kbCounter = 0;
                kb = false;
                speed = defaultSpeed;
            }
            else if(collisionState == false) {
                switch(knockbackDirection) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }
            kbCounter++;
            //KNOCKBACK COUNTER INDICATES DISTANCE, INCREASING IT WILL INCREASE KB DISTANCE
            if (kbCounter == 8) {
                kbCounter = 0;
                kb = false;
                speed = defaultSpeed;
            }
        }
        else if (attacking == true){attacking();}
        
        else if(hk.pressSpace == true) {
            guarding = true;
            guardCount++;
        }
        //this if statement makes it so that the image is only updated when the player is pressing a key
        //ADD THE ENTER CONDITION TO ENSURE THE PLAYER IS ABLE TO INTERACT WITHOUT MOVEMENT KEYS
        else if (hk.pressUp == true || hk.pressDown == true ||
                hk.pressLeft == true || hk.pressRight == true || hk.pressEnter == true)  
        {
            if (hk.pressUp == true){direction = "up";}
            else if (hk.pressDown == true){direction = "down";}
            else if (hk.pressLeft == true){direction = "left";}
            else if (hk.pressRight == true){direction = "right";}
            
            
            //check tile collision state
            collisionState = false;
            p.cd.detectCollision(this);
            
            //check object collision state
            int objectIndex = p.cd.checkObject(this, true);
            pickUpObject(objectIndex);
            
            //check npc collision state
            int npcIndex = p.cd.checkEntity(this, p.npc);
            interactNPC(npcIndex);
            
            //check monster collision
            int monsterIndex = p.cd.checkEntity(this, p.mobs);
            monsterContact(monsterIndex);
            
            //CHECK EVENT COLLISION
            p.eh.checkEvent();
            
            //if collision is false, player can move
            if (collisionState == false && hk.pressEnter == false){
                switch(direction)
                {
                    
                    case "up":worldY -= speed;break;
                    case "down":worldY += speed;break;
                    case "left":worldX -= speed;break;
                    case "right":worldX += speed;break;
                        
                }
            }
            //MAKES SURE PLAYER DOESNT ATTACK AND INTERACT AT THE SAME TIME
            if (hk.pressEnter == true && attackCancel == false)
            {
                attacking = true;
                spriteCount = 0;
            }
            
            
            attackCancel = false;
            p.hk.pressEnter = false;
            guarding = false;
            guardCount = 0;
            
            //used to change image direction
            spriteCount++;
            if (spriteCount > 15){ //changes the speed of direction change
                if (spriteNumber == 1){spriteNumber = 2;}
                else if (spriteNumber == 2){spriteNumber = 1;}
                
                spriteCount = 0;
                
            }
        }
        
        //MAKES IT SO YOU CAN ONLY SHOOT 1 PROJECTILE AT A TIME
        if (p.hk.pressShootKey == true && projectile.alive == false 
                && shotCount == 30 && projectile.haveProjectile(this) == true)
        {
            
            //SETTING DEFAULT VALUES
            projectile.set(worldX, worldY, direction, true, this);
            
            //SUBTRAT RESOURCE
            projectile.subtractProjectile(this);
            
            
            for (int i = 0; i < p.projectile[1].length; i++) {
                if (p.projectile[p.mapCurrent][i] == null) { 
                    p.projectile[p.mapCurrent][i] = projectile;
                    break;
                }
            }
            
            shotCount = 0;
            
            //ADD SOUND EFFECT
        }
        //NEEDS TO BE OUTSIDE KEY PRESSED IF STATEMENT SO IT HAPPENS WHILE YOU ARENT MOVING ASWELL
        if (invincible == true)
        {
            invincibleCount++;
            //TIME IT TAKES TO BE HIT AGAIN (60 = 1 second)
            if (invincibleCount > 60)
            {
                invincible = false;
                transparent = false;
                invincibleCount = 0;
            }
        }
        //TIMER FOR PROJECTILES, PREVENTING FROM SHOOTING MULTIPLE TIMES IN A SECOND
        if (shotCount < 30)
        {
            shotCount++;
        }
        if (health > maxHealth)
        {
            health = maxHealth;
        }
        if (projectileAmount > maxProjectileAmount)
        {
            projectileAmount = maxProjectileAmount;
        }
        if (hk.godModeOn == false) {
            if (health <= 0) {
                p.gameState = p.gameOverState;
                p.stopMusic();
                p.ui.commandChoice = -1;
                p.playEffect(10);
            }
        }
        
    }
    
    public void pickUpObject(int i)
    {
        if (i != 999) 
        {
            if (p.object[p.mapCurrent][i].type == type_door) {
            	
            }
            //PICKUP ONLY ITEMS
            
            else if (p.object[p.mapCurrent][i].type == type_pickup)
            {
                p.object[p.mapCurrent][i].use(this);
                p.object[p.mapCurrent][i] = null;
            }
            else
            {
                //ITEMS STORED IN INVENTORY
                
                String message;
                
                //CAN ONLY PICKUP IF INVENTORY ISN'T FULL
                if (canPickup(p.object[p.mapCurrent][i]))
                {
                    p.playEffect(2);
                    message = "Got a " + p.object[p.mapCurrent][i].name + "!";
                }
                else
                {
                    message = "You cannot carry anymore items!";
                }
                p.ui.addMessage(message);
                //MAKE SURE TO CHANGE THIS BECAUSE IT DOESNT THROW ERROR --> FIXED
                p.object[p.mapCurrent][i] = null;
            }
                
       }
           
    }
   
    public void interactNPC(int i)
    {
      //PLAYER ONLY INTERACTS WHEN ENTER IS PRESSED ON THEM
        if (p.hk.pressEnter == true)
        {
            if (i != 999) 
            {
                attackCancel = true;
                p.npc[p.mapCurrent][i].speak();
            }
            else
            {
                p.playEffect(8);
                attacking = true;
            }
        }
       
    }
    public void monsterContact(int i)
    {
        if (i != 999) 
        {
            if (invincible == false && p.mobs[p.mapCurrent][i].death == false)
            {
                p.playEffect(7);
                
                int damage = p.mobs[p.mapCurrent][i].attack - defense;
                if (damage < 1)
                {
                    damage = 1;
                }
                health -= damage;
                invincible = true;
                transparent = true;
            }
            
        }
    }
    
    public void damageMonster(int i, Entity attacker, int attack, int knockbackPower)
    {
        if (i != 999)
        {
            if (p.mobs[p.mapCurrent][i].invincible == false)
            {
                p.playEffect(7);
                
                if (knockbackPower > 0) {
                    setKnockback(p.mobs[p.mapCurrent][i], attacker, knockbackPower);
                }
                
                if (p.mobs[p.mapCurrent][i].parry == true) {
                    attack *= 2;
                }
                int damage = attack - p.mobs[p.mapCurrent][i].defense;
                critIndicator = new Random().nextInt(100) + 1;
                
                if (damage < 0)
                {
                    damage = 0;
                }
                if (critIndicator > 0 && critIndicator <= currentWeapon.criticalRate) {
                    damage += (int) damage * 1.5;
                }
                p.mobs[p.mapCurrent][i].health -= damage;
                
                p.ui.addMessage(damage + "damage!");
                
                p.mobs[p.mapCurrent][i].invincible = true;
                p.mobs[p.mapCurrent][i].damageReaction();
                if (p.mobs[p.mapCurrent][i].health <= 0)
                {
                    p.mobs[p.mapCurrent][i].death = true;
                    p.ui.addMessage("Killed the " + p.mobs[p.mapCurrent][i].name + "!");
                    p.ui.addMessage("Gained " + p.mobs[p.mapCurrent][i].exp + " Exp!");
                    exp += p.mobs[p.mapCurrent][i].exp;
                    checkLevelUp();
                }
            }
            
        }
    }
    public void checkLevelUp()
    {
        if (exp >= nextLevelExp)
        {
        	while (exp >= nextLevelExp) {
        		level++;
                nextLevelExp = nextLevelExp * 2;
                maxHealth += 2;
                strength++;
                speed += .1;
                maxProjectileAmount += .5;
                attack = getAttack();
                defense = getDefense();
                
                p.playEffect(6);
                
                p.gameState = p.dialogueState;
                setDialogue();
                startDialogue(this, 0);
        	}
        	
            
            
        }
    }
    
    public void setDialogue() {
        dialogues[0][0] = "You leveled up to " + level + "!\n"
            + "You gained some muscle, didn't you?";
    }
    public void draw(Graphics2D graph2)
    {
        
        BufferedImage playerImage = null;
        
        //FOR CHANGING UP AND LEFT IMAGES FROM MOVING THE CHARACTER
        int tempXScreen = screenX;
        int tempYScreen = screenY;
        //draws the player image on the screen with directional movement
        switch(direction)
        {
            case "up":
                if (attacking == false)
                {
                    if (spriteNumber == 1)
                    {
                        playerImage = up1;
                    }
                    if (spriteNumber == 2)
                    {
                        playerImage = up2;
                    }
                }
                if (attacking == true)
                {
                    //ADJUSTING IMAGES FOR ATTACKING
                    tempYScreen = screenY - p.tileSize;
                    if (spriteNumber == 1)
                    {
                        playerImage = attackUp1;
                    }
                    if (spriteNumber == 2)
                    {
                        playerImage = attackUp2;
                    }
                }
                if (guarding == true) {
                    playerImage = guardUp;
                }
                
                break;
            case "down":
                if (attacking == false)
                {
                    if (spriteNumber == 1)
                    {
                        playerImage = down1;
                    }
                    if (spriteNumber == 2)
                    {
                        playerImage = down2;
                    }
                }
                if (attacking == true)
                {
                    if (spriteNumber == 1)
                    {
                        playerImage = attackDown1;
                    }
                    if (spriteNumber == 2)
                    {
                        playerImage = attackDown2;
                    }
                }
                if (guarding == true) {
                    playerImage = guardDown;
                }
            break;
            case "left":
                if (attacking == false)
                {
                    if (spriteNumber == 1)
                    {
                        playerImage = left1;
                    }
                    if (spriteNumber == 2)
                    {
                        playerImage = left2;
                    }
                }
                if (attacking == true)
                {
                    tempXScreen = screenX - p.tileSize;
                    if (spriteNumber == 1)
                    {
                        playerImage = attackLeft1;
                    }
                    if (spriteNumber == 2)
                    {
                        playerImage = attackLeft2;
                    }
                }
                if (guarding == true) {
                    playerImage = guardLeft;
                }
            break;
            
            case "right":
                if (attacking == false)
                {
                    if (spriteNumber == 1)
                    {
                        playerImage = right1;
                    }
                    if (spriteNumber == 2)
                    {
                        playerImage = right2;
                    }
                }
                if (attacking == true)
                {
                    if (spriteNumber == 1)
                    {
                        playerImage = attackRight1;
                    }
                    if (spriteNumber == 2)
                    {
                        playerImage = attackRight2;
                    }
                }
                if (guarding == true) {
                    playerImage = guardRight;
                }
            break;
        }
        
        if (p.player.attacking == true)
        {
            graph2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
        
        //INVINCIBLE INDICATOR
        if (transparent == true)
        {
            //SETS BLINKING OPACITY INDICATOR
            graph2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
            if (invincibleCount > 15)
            {

                graph2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                if (invincibleCount > 35)
                {
                    graph2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
                    if (invincibleCount > 50)
                    {
                        graph2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                    }
                }
            }
            
        }
        
        //use null for image observer
        if (draw == true) {
        	graph2.drawImage(playerImage, tempXScreen, tempYScreen, null);
        }
        
        //RESET OPACITY
        graph2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        
//        //FOR DISPLAYING COLLISION AREA ON PLAYER
//        graph2.setColor(Color.red);
//        graph2.drawRect(xScreen + solid.x, yScreen + solid.y, solid.width, solid.height);
        
    }
    public int getAttack()
    {
        //SETS ATTACK AREA FOR WEAPON YOU ARE HOLDING
        attackArea = currentWeapon.attackArea;
        attackDuration1 = currentWeapon.attackDuration1;
        attackDuration2 = currentWeapon.attackDuration2;
        //SETS ATTACK TO PLAYER
        return attack = strength * currentWeapon.attackValue;
    }
    public int getDefense()
    {
        //SETS DEFENSE TO PLAYER
        return defense = currentShield.defenseValue;
    }
    
    
    public void selectItem()
    {
        int itemIndex = p.ui.getItemSlotIndex(p.ui.playerSlotCol, p.ui.playerSlotRow);
        
        if (itemIndex < inventory.size())
        {
            Entity selectedItem = inventory.get(itemIndex);
            
            if (selectedItem.type == type_sword)
            {
                currentWeapon = selectedItem;
                attack = getAttack();
                getAttackSprite();
            }
            if (selectedItem.type == type_shield)
            {
                currentShield = selectedItem;
                defense = getDefense();
            }
            //PLAYER EQUIPPING LIGHT SOURCE
            if (selectedItem.type == type_light) {
                if (lightSource == selectedItem) {
                    lightSource = null;
                }
                else {
                    lightSource = selectedItem;
                }
                //ALLOWS FOR THE LIGHTING CLASS TO CALL THE LIGHTSOURCE METHOD TO SET IT
                lightUpdate = true;
            }
            if (selectedItem.type == type_consumable)
            {
                if (selectedItem.use(this) == true) {
                    if (selectedItem.amount > 1) {
                        selectedItem.amount--;
                    }
                    else {
                        inventory.remove(itemIndex);
                    }
                }
                
            }
        }
    }
    
    public void damageProjectile(int i) {
        if (i != 999) {
            Entity projectile  = p.projectile[p.mapCurrent][i];
            projectile.alive = false;
            generateParticle(projectile,projectile);
        }
    }
    //default values for entities
    public void setDefault()
    {
        //player position on world map
        worldX = p.tileSize * 24;
        worldY = p.tileSize * 25;
        defaultSpeed = 6;
        speed = defaultSpeed;
        direction = "down";
        
        
        //PLAYER STATUS
        //2 health = 1 heart
        maxHealth = 6;
        health = maxHealth;
        level = 1;
        strength = 2;
        exp = 0;
        nextLevelExp = 5;
        coin = 0;
        maxProjectileAmount = 4;
        projectileAmount = maxProjectileAmount;
        lightSource = null;
        currentWeapon = new ObjectStartSword(p);
        currentShield = new ObjectWoodShield(p);
        projectile = new ObjectArrow(p);
        
        attack = getAttack(); //TOTAL ATTACK VALUE IS DECIDED BY STRENGTH AND WEAPON
        defense = getDefense(); //TOTAL DEFENSE VALUE DECIDED BY SHIELD
        getSprite();
        getAttackSprite();
        getGuardSprite();
        setItems();
        
        //PLAYER STARTS WITH ARROWS AS PROJECTILES
        
        
    }
    public void setDefaultPositions() {
        p.mapCurrent = 0;
        worldX = p.tileSize * 25;
        worldY = p.tileSize * 25;
        direction = "down";
    }
    public void setItems()
    {
        //FOR RESTARTING THE GAME, CLEAR INVENTORY OF EXTRA ITEMS
        inventory.clear();
        //ADDING DEFAULT ITEMS
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        
    }
    public int getCurrentWeaponSlot() {
        int currentWeaponSlot = 0;
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i) == currentWeapon) {
                currentWeaponSlot = i;
            }
        }
        return currentWeaponSlot;
    }
    public int getCurrentShieldSlot() {
        int currentShieldSlot = 0;
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i) == currentShield) {
                currentShieldSlot = i;
            }
        }
        return currentShieldSlot;
    }
    
    public void restoreStatus() {
        health = maxHealth;
        projectileAmount = maxProjectileAmount;
        invincible = false;
        transparent = false;
        attacking = false;
        kb = false;
        lightUpdate = true;
        guarding = false;
        speed = defaultSpeed;
    }
    
    //SCANS INVENTORY TO CHECK IF THE SAME ITEM IS IN YOUR INVENTORY TO STACK THEM
    public int searchInventory(String itemName) {
        int itemIndex = 999;
        for(int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).name.equals(itemName)) {
                itemIndex = i;
                break;
            }
        }
        return itemIndex;
    }
    
    public boolean canPickup(Entity item) {
        boolean canPickup = false;
        
        Entity newItem = p.generator.getObject(item.name);
        //CHECK IF ITEM IS STACKABLE
        if (newItem.stackable == true) {
            int index = searchInventory(newItem.name);
            if (index != 999) {
                inventory.get(index).amount++;
                canPickup = true;
            }
            else { //UNIQUE ITEM BEING ADDED TO INVENTORY
                if (inventory.size() != maxInventorySize) {
                    inventory.add(newItem);
                    canPickup = true;
                }
            }
        }
        else { //ITEM ISNT STACKABLE SO YOU CHECK FOR AN EMPTY SLOT IN YOUR INVENTORY
            if (inventory.size() != maxInventorySize) {
                inventory.add(newItem);
                canPickup = true;
            }
        }
        return canPickup;
    }
}
