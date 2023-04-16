package entity;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import game.Panel;
import game.Toolbox;

public class Entity
{
    //PANEL
    Panel p;
    
    //IMAGES FOR SPRITES
    public BufferedImage image, image2, image3;
    public String name;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2, right3, checkmark;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1,
        attackLeft2, attackRight1, attackRight2, guardUp, guardDown, guardLeft, guardRight;
    public String direction = "down";
    public int spriteNumber = 1;
    
    //BOOLEANS
    public boolean isBoss;
    public boolean alive = true;
    public boolean death = false;
    public boolean collision = false;
    public boolean collisionState = false;
    public boolean attacking = false;
    public boolean enraged = false;
    public boolean stackable = false;
    public boolean kb = false;
    public boolean parry = false;
    public boolean transparent = false;
    public boolean guarding = false;
    public boolean invincible = false;
    public boolean onPath = false;
    public boolean healthBarOn = false;
    public boolean stasis = false;
    public boolean temporary = false;
    public boolean draw = true;
    
    //TYPE OF ENTITY
    public int type; 
    public final int type_player = 0;
    public final int type_npc = 1;
    public final int type_monster = 2;
    public final int type_sword = 3;
    public final int type_shield = 4;
    public final int type_consumable = 6;
    public final int type_pickup = 7;
    public final int type_light = 8;
    public final int type_door = 9;
    
    //COUNTERS
    public int healthCount = 0;
    int deathCount = 0;
    int kbCounter = 0;
    public int guardCount = 0;
    int parryCount = 0;
    public int shotCount = 0;
    public int spriteCount = 0;
    public int actionCount = 0;
    public int invincibleCount = 0;
    
    //COMBAT
    public Entity attacker;
    public String knockbackDirection;
    public int criticalRate, attackDuration1, attackDuration2;

    //INVENTORY
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;
    public int amount = 1;
    
    //ITEM ATTRIBUTES
    public int attackValue, defenseValue, useCost, value;
    public String description = "";
    
    //CHARACTER STATUS
    public int speed, maxHealth, health, level, strength, attack, defense, exp, nextLevelExp,
    	coin, defaultSpeed, maxProjectileAmount, projectileAmount, price, lightRadius;
    public int knockbackPower = 0;
    public Entity currentWeapon, currentShield, lightSource;
    public Projectile projectile;
    
    
    //POSITIONS, AREA, AND COLLISIONS FOR CHARACTERS
    public int worldX, worldY;
    public Rectangle solid = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public int solidAreaDefaultX, solidAreaDefaultY;

    //DIALOGUE
    public String dialogues[][] = new String[20][20];
    public int dialogueIndex = 0;
    public int dialogueChapter = 0;
    
    
    public Entity(Panel p)
    {
        this.p = p;
    }
    
    
    public BufferedImage setup(String imagePath, int width, int height)
    {
        
        BufferedImage image = null;
        Toolbox t = new Toolbox();
        
        try
        {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = t.scaleImage(image, width, height);
            
        }catch (IOException e){e.printStackTrace();}
        
        return image;
    }
    
    public boolean inView() {
    	boolean inView = false;
    	if (worldX + p.tileSize * 5 > p.player.worldX - p.player.screenX - p.tileSize && 
                worldX - p.tileSize< p.player.worldX + p.player.screenX + p.tileSize &&
                worldY + p.tileSize * 5> p.player.worldY - p.player.screenY - p.tileSize &&
                worldY - p.tileSize< p.player.worldY + p.player.screenY + p.tileSize) {
    		inView = true;
    	}
    	return inView;
    }
    public int getScreenX() {
    	int screenX = worldX - p.player.worldX + p.player.screenX;
    	return screenX;
    }
    public int getScreenY() {
    	int screenY = worldY - p.player.worldY + p.player.screenY;
    	return screenY;
    }
    //Drawing the entities
    public void draw(Graphics2D graphics2)
    {
        BufferedImage image = null;
        
        int tempX = getScreenX();
        int tempY = getScreenY();
        //this if statement makes the game not draw tiles that arent on the screen
        //improves game performance/map rendering speed
        if (inView())
            //add tile size to make sure black bars don't appear
        {
            
            switch(direction)
            {
                case "up":
                    if (attacking == false){
                        if (spriteNumber == 1){image = up1;}
                        if (spriteNumber == 2){image = up2;}
                    }
                    if (attacking == true){
                        //ADJUSTING IMAGES FOR ATTACKING
                        tempY = getScreenY() - up1.getHeight();
                        if (spriteNumber == 1){image = attackUp1;}
                        if (spriteNumber == 2){image = attackUp2;}
                    }
                    
                    break;
                    
                case "down":
                    if (attacking == false){
                        if (spriteNumber == 1){image = down1;}
                        if (spriteNumber == 2){image = down2;}
                    }
                    if (attacking == true){
                        if (spriteNumber == 1){image = attackDown1;}
                        if (spriteNumber == 2){image = attackDown2;}
                    }
                    
                break;
                
                case "left":
                    if (attacking == false){
                        if (spriteNumber == 1){image = left1;}
                        if (spriteNumber == 2){image = left2;}
                    }
                    if (attacking == true){
                        tempX = getScreenX() - left1.getWidth();
                        if (spriteNumber == 1){image = attackLeft1;}
                        if (spriteNumber == 2){image = attackLeft2;}
                    }
                break;
                
                case "right":
                    if (attacking == false){
                        if (spriteNumber == 1){image = right1;}
                        if (spriteNumber == 2){image = right2;}
                    }
                    if (attacking == true){
                        if (spriteNumber == 1){image = attackRight1;}
                        if (spriteNumber == 2){image = attackRight2;}
                    }
                    
                    break;
                }
            }
            
            
            //INVINCIBLE INDICATOR
            if (invincible == true){
                healthBarOn = true;
                healthCount = 0;
                //SETS BLINKING OPACITY INDICATOR
                changeAlpha(graphics2, 0.4f);
                if (invincibleCount > 15){changeAlpha(graphics2, 1f);
                    if (invincibleCount > 35){changeAlpha(graphics2, 0.4f);
                        if (invincibleCount > 50){changeAlpha(graphics2, 1f);}
                    }
                }
            }
            //CHECK TO SEE IF MONSTER IS DYING
            if (death == true){deathAnimation(graphics2);}
            //draws each row and column
            graphics2.drawImage(image, tempX, tempY, null);
        }
    
    //DRAWING DEATH ANIMATION 
    public void deathAnimation(Graphics2D graph2)
    {
        deathCount++;
        //INTERVAL VALUE
        int i = 5;
        if (deathCount <= i){changeAlpha(graph2, 0f);}
        if (deathCount > i && deathCount <= i*2){changeAlpha(graph2, 1f);}
        if (deathCount > i*2 && deathCount <= i*3){changeAlpha(graph2, 0f);}
        if (deathCount > i*3 && deathCount <= i*4){changeAlpha(graph2, 1f);}
        if (deathCount > i*4 && deathCount <= i*5){changeAlpha(graph2, 0f);}
        if (deathCount > i*5 && deathCount <= i*6){changeAlpha(graph2, 1f);}
        if (deathCount > i*6 && deathCount <= i*7){changeAlpha(graph2, 0f);}
        if (deathCount > i*7 && deathCount <= i*8){changeAlpha(graph2, 1f);}
        if (deathCount > i*8){alive = false;}
    }
    //CHANGES TRANSPARENCY OF IMAGE
    public void changeAlpha(Graphics2D graph2, float alphaValue)
    {
        graph2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }
    public void setAction(){}
    public void checkDrop(){}
    //METHOD THAT DETERMINES WHAT ITEMS ARE DROPPED
    //DIFFERENT DEPENDING ON MONSTER
    public void dropItem(Entity droppedItem)
    {
        for (int i = 0; i < p.object[1].length; i++)
        {
            if (p.object[p.mapCurrent][i] == null)
            {
                p.object[p.mapCurrent][i] = droppedItem;
                p.object[p.mapCurrent][i].worldX = worldX; // the dead monster's world X value
                p.object[p.mapCurrent][i].worldY = worldY; // the dead monster's world Y value
                break;
            }
        }
    }
    
    public void damageReaction(){}
    public void speak(){
    	p.playEffect(9);
    }
    public void startDialogue(Entity en, int chapter) {
        
        p.gameState = p.dialogueState;
        p.ui.npc = en;
        dialogueChapter = chapter;
        
    }
    public void facePlayer() {
      //MAKES THE NPC FACE THE PLAYER WHEN DIALOGUE IS USED
        switch(p.player.direction){
            case "up":direction = "down";break;
            case "down":direction = "up";break;
            case "left":direction = "right";break;
            case "right":direction = "left";break;
        }
    }
    public boolean use(Entity en){
        //OVERRIDE FOR SPECIFIC ITEMS
        return false;
    }
    
    public void attacking(){
        spriteCount++;
        //DIFFERENTIATES ATTACKING ANIMATIONS
        if (spriteCount <= attackDuration1){spriteNumber = 1;}
        
        //ADJUST SPRITECOUNT RANGE TO INCREASE DIFFICULTY WITH TIMING BLOCKING PROJECTILES
        if (spriteCount > attackDuration1 && spriteCount <= attackDuration2)
        {
            spriteNumber = 2;
            //TEMPORARILY MODIFY PLAYER POSITION TO DETERMINE HIT DETECTION
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidWidth = solid.width;
            int solidHeight = solid.height;
            
            //ADJUSTING PLAYER X WORLD AND Y WORLD FOR ATTACKAREA
            switch(direction){
                case "up":worldY -= attackArea.height; break;
                case "down":worldY += attackArea.height; break;
                case "left":worldX -= attackArea.width; break;
                case "right":worldX += attackArea.width; break;
            }
            
            //CHANGE SOLID DIMENSIONS TO ATTACK AREA
            solid.width = attackArea.width;
            solid.height = attackArea.height;
            if (type == type_monster) {
                if (p.cd.checkPlayer(this) == true) {damagePlayer(attack);}
            }
            else {
              //CHECK MONSTER COLLISION WITH UPDATED WORLD X, Y, AND SOLID RECT
                int monsterIndex = p.cd.checkEntity(this, p.mobs);
                p.player.damageMonster(monsterIndex,this, attack, currentWeapon.knockbackPower);
                
                
                int projectileIndex = p.cd.checkEntity(this, p.projectile);
                p.player.damageProjectile(projectileIndex);
            }
            
            
            //AFTER CHECKING COLLISION, RESTORE THE ORIGINAL DATA AFTER HITTING MONSTER
            worldX = currentWorldX;
            worldY = currentWorldY;
            solid.width = solidWidth;
            solid.height = solidHeight;
            
            
        }
        if (spriteCount > attackDuration2){
            spriteNumber = 1;
            spriteCount = 0;
            attacking = false;
        }
    }
    
    public void moveTowardPlayer(int interval) {
        actionCount++;
        if (actionCount > interval) {
            if (getXDistance(p.player) > getYDistance(p.player)) {
                if (p.player.getCenterX() < getCenterX()) {direction = "left";}
                else {direction = "right";}
            }
            else if (getXDistance(p.player) < getYDistance(p.player)) {
                if (p.player.getCenterY() < getCenterY()) {direction = "up";}
                else {direction = "down";}}
            
            actionCount = 0;
        }
    }
    public int getCenterX() {
        int centerX = worldX + left1.getWidth()/2;
        return centerX;
    }
    public int getCenterY() {
        int centerY = worldY + up1.getHeight()/2;
        return centerY;
    }
    //CAN ONLY ATTACK IF WITHIN A CERTAIN DISTANCE
    public void checkAttacking(int rate, int vertical, int horizontal) {
        boolean inRange = false;
        int xDistance = getXDistance(p.player);
        int yDistance = getYDistance(p.player);
        
        switch(direction) {
            case "up": 
                if (p.player.getCenterY() < getCenterY() && 
                		yDistance < vertical && xDistance < horizontal) {
                    inRange = true;
                }
                break;
            case "down": 
                if (p.player.getCenterY() > getCenterY() && 
                		yDistance < vertical && xDistance < horizontal) {
                    inRange = true;
                }
                break;
            case "left": 
                if (p.player.getCenterX() < getCenterX() && 
                		xDistance < vertical && yDistance < horizontal) {
                    inRange = true;
                }
                break;
            case "right": 
                if (p.player.getCenterX() > getCenterX() && 
                		xDistance < vertical && yDistance < horizontal) {
                    inRange = true;
                }
                break;
        }
        
        if (inRange == true) {
            //CHECK IF THE MONSTER ATTACKS
            int i = new Random().nextInt(rate);
            if (i == 0) {
                attacking = true;
                spriteNumber = 1;
                spriteCount = 0;
                shotCount = 0;
            }
        }
    }
    public void damagePlayer(int attack)

    {
        if (p.player.invincible == false){
            //ONLY TAKE DMG IF PLAYER ISNT INVINCIBLE
            int damage = attack - p.player.defense;
            //GET OPPOSITE DIRECTION
            String canGuardDirection = getOpposite(direction);
            if (p.player.guarding == true && p.player.direction.equals(canGuardDirection)){
                
                //PARRY
                //10 frame window to parry
                if (p.player.parryCount < 10) {
                    damage = 0;
                    p.playEffect(3);
                    //KNOCKS BACK ENEMY
                    setKnockback(this, p.player, p.player.currentShield.knockbackPower);
                    parry = true;
                    //FREEZES ENEMY WHEN PARRY HAPPENS BY PUTTING BACK SP
                    spriteCount -= 100;
                }
                else {
                    damage /= 1.5;
                    p.playEffect(13);
                }
                
            }
            else {
                p.playEffect(12);
                if (damage < 1)
                {
                    damage = 1;
                }
            }
            
            
            if (damage != 0) {
                p.player.transparent = true;
                setKnockback(p.player, this, knockbackPower);
            }
            
            p.player.health -= damage;
            p.player.invincible = true;
        }
    }
    
    public void resetCounter() {
        healthCount = 0;
        deathCount = 0;
        kbCounter = 0;
        guardCount = 0;
        parryCount = 0;
        shotCount = 0;
        spriteCount = 0;
        actionCount = 0;
        invincibleCount = 0;
    }
    public String getOpposite(String direction) {
        String opposite = "";
        switch(direction) {
            case "up": opposite = "down"; break;
            case "down": opposite = "up"; break;
            case "left": opposite = "right"; break;
            case "right": opposite = "left"; break;
            
        }
        return opposite;
    }
    
    //OVERRIDE THESE METHODS IN SUBCLASSES
    public Color getParticleColor()
    {
        Color color = null;
        return color;
    }
    public int getParticleSize()
    {
        //SIZE IN PIXELS
        int size = 0;
        return size;
    }
    public int getParticleSpeed()
    {
        //HOW FAST PARTICLE FLIES
        int speed = 0;
        return speed;
    }

    public int getParticleMaxHealth()
    {
        //HOW LONG THE PARTICLE LASTS ON THE SCREEN
        int maxHealth = 0;
        return maxHealth;
    }
    
    public void generateParticle(Entity generator, Entity target)
    {
        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxHealth = generator.getParticleMaxHealth();
        
        //XD AND YD VALUES INDICATE WHERE THE PARTICLE WILL MOVE
        Particle p1 = new Particle(p, target, color, size, speed, maxHealth, -2, -1);
        Particle p2 = new Particle(p, target, color, size, speed, maxHealth, -2, 1);
        Particle p3 = new Particle(p, target, color, size, speed, maxHealth, 2, -1);
        Particle p4 = new Particle(p, target, color, size, speed, maxHealth, 2, 1);
        p.particleList.add(p1);
        p.particleList.add(p2);
        p.particleList.add(p3);
        p.particleList.add(p4);
    }
    
    public void checkCollision() {
      //checks collision for entity to entity, entity to player, object to player, object to entity
        collisionState = false;
        p.cd.detectCollision(this);
        p.cd.checkObject(this, false);
        p.cd.checkEntity(this, p.mobs);
        p.cd.checkEntity(this, p.npc);
        boolean playerContact = p.cd.checkPlayer(this);
        
        
        if (this.type == type_monster && playerContact == true)
        {
            damagePlayer(attack);
        }
    }
    public void update()
    {
    	if (stasis == false) {
    		if (kb == true) {
                checkCollision();
                
                if (collisionState == true) {
                    kbCounter = 0;
                    kb = false;
                    speed = defaultSpeed;
                }
                else if(collisionState == false) {
                    switch(knockbackDirection) {
                        case "up":worldY -= speed;break;
                        case "down":worldY += speed;break;
                        case "left":worldX -= speed;break;
                        case "right":worldX += speed;break;
                            
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
            
            else if (attacking == true) {
                attacking();
            }
            else {
              //If subclass has the same method, it takes priority
                setAction();
                checkCollision();
              //if collision is false, player can move
                if (collisionState == false)
                {
                    switch(direction)
                    {
                        case "up":worldY -= speed;break;
                        case "down":worldY += speed;break;
                        case "left":worldX -= speed;break;
                        case "right":worldX += speed;break;   
                    }
                }
                spriteCount++;
                if (spriteCount > 45) //changes the speed of direction change
                {
                    if (spriteNumber == 1)
                    {
                        spriteNumber = 2;
                    }
                    else if (spriteNumber == 2)
                    {
                        spriteNumber = 1;
                    }
                    spriteCount = 0;
                }
            }
            
            //used to change image direction
            
            //NEEDS TO BE OUTSIDE KEY PRESSED IF STATEMENT SO IT HAPPENS WHILE YOU ARENT MOVING ASWELL
            if (invincible == true)
            {
                invincibleCount++;
                //TIME IT TAKES TO BE HIT AGAIN (60 = 1 second)
                if (invincibleCount > 30)
                {
                    invincible = false;
                    invincibleCount = 0;
                }
            }
            //TIMER FOR MONSTER PROJECTILES
            if (shotCount < 30)
            {
                shotCount++;
            }
            
            if (parry == true) {
                parryCount++;
                //PARRY STATE CONTINUES FOR 1 SECOND
                if (parryCount > 100) {
                    parry = false;
                    parryCount = 0;
                }
            }
    	}
        
    }
    public int getXDistance(Entity target) {
        int xDistance = Math.abs(getCenterX() - target.getCenterX());
        return xDistance;
    }
    public int getYDistance(Entity target) {
        int yDistance = Math.abs(getCenterY() - target.getCenterY());
        return yDistance;
    }
    public int getTileDistance(Entity target) {
        int tileDistance = (getXDistance(target) + getYDistance(target))/p.tileSize;
        return tileDistance;
    }
    public int getGoalCol(Entity target) {
        int goalCol = (target.worldX + target.solid.x)/p.tileSize;
        return goalCol;
    }
    public int getGoalRow(Entity target) {
        int goalRow = (target.worldY + target.solid.y)/p.tileSize;
        return goalRow;
    }
    
    //CHECKS TO SEE IF ENTITY IS STILL CHASING
    //1% CHANCE TO STOP EVERY SECOND
    public void checkChasing(Entity target, int distance, int chance) {
        if (getTileDistance(target) > distance) {
            int i = new Random().nextInt(chance);
            if (i >= 25) {
                onPath = false;
            }
        }
    }
    public void checkStartChasing(Entity target, int distance, int chance) {
        if (getTileDistance(target) < distance) {
            int i = new Random().nextInt(chance);
            if (i >= 25) {
                onPath = true;
            }
        }
    }
    public void getRandomDirection(int interval) {
        actionCount++;
        
        //Interval for picking direction
        //Wont change for the next 150 frames
        if (actionCount > interval)
        {
            Random rand = new Random();
            
            //gets a random number from 1-100
            int i = rand.nextInt(100) + 1;
            
            //Simple ai for npc movement
            if (i <= 25){direction = "up";}
            if (i > 25 && i <= 50){direction = "down";}
            if (i > 50 && i <= 75){direction = "left";}
            if (i > 75 && i <= 100){direction = "right";}
          //resetting the counter
            actionCount = 0;
        }  
    }
    public void setKnockback(Entity target, Entity attacker, int knockbackPower) {
        this.attacker = attacker;
        target.knockbackDirection = attacker.direction;
        target.speed += knockbackPower;
        target.kb = true;
    }
    public void checkShooting(int rate, int interval) {
        int i = new Random().nextInt(rate);
        if (i == 0 && projectile.alive == false && shotCount == interval)
        {
            projectile.set(worldX, worldY, direction, true, this);
            for (int ii = 0; ii < p.projectile[1].length; ii++) {
                if (p.projectile[p.mapCurrent][ii] == null) {
                    p.projectile[p.mapCurrent][ii] = projectile;
                    break;
                }
            }
            shotCount = 0;
        }
    }
    public void pathSearch(int goalCol, int goalRow) {
        int startColumn = (worldX + solid.x)/p.tileSize;
        int startRow = (worldY + solid.y)/p.tileSize;
        
        p.pf.setNodes(startColumn, startRow, goalCol, goalRow);
        
        if (p.pf.search() == true) {
            //THE NEXT WORLDX AND WORLDY TO MOVE TO
            int xNext = p.pf.path.get(0).col * p.tileSize;
            int yNext = p.pf.path.get(0).row * p.tileSize;
            
            //SOLID AREA OF THE ENTITY MOVING
            int leftX = worldX + solid.x;
            int rightX = worldX + solid.x + solid.width;
            int topY = worldY + solid.y;
            int bottomY = worldY + solid.y + solid.height;
            
            //COMPARE TO THE NEXT POSITION AND DECIDE THE DIRECTION OF THE ENTITY
            //NEEDS TO MAKE SURE THE ENTITY DOESN'T GET STUCK
            
            if (topY > yNext && leftX >= xNext && rightX < xNext + p.tileSize) {
                direction = "up";
            }
            if (topY < yNext && leftX >= xNext && rightX < xNext + p.tileSize) {
                direction = "down";
            }
            else if(topY >= yNext && bottomY < yNext + p.tileSize) {
                //EITHER LEFT OR RIGHT
                if (leftX > xNext) {
                    direction = "left";
                }
                if (leftX < xNext) {
                    direction = "right";
                }
            }
            else if (topY > yNext && leftX > xNext) {
                //UP OR LEFT DIRECTION
                //Y POSITION IS BELOW NEXT TILE AND HE IS ON THE RIGHT SIDE
                direction = "up";
                //CHECK COLLISION TO CHECK IF THERE IS A TILE ABOVE THE ENTITY
                checkCollision();
                if (collisionState == true) {
                    direction = "left";
                    
                }
                
            }
            else if (topY > yNext && leftX < xNext) {
                //UP OR RIGHT
                direction = "up";
                //CHECK COLLISION TO CHECK IF THERE IS A TILE ABOVE THE ENTITY
                checkCollision();
                if (collisionState == true) {
                    direction = "right";
                }
            }
            else if (topY < yNext && leftX > xNext) {
                //DOWN OR LEFT
                direction = "down";
                checkCollision();
                if (collisionState == true) {
                    direction = "left";
                }
            }
            else if (topY < yNext && leftX < xNext) {
                //DOWN OR RIGHT
                direction = "down";
                checkCollision();
                if (collisionState == true) {
                    direction = "right";
                }
            }
        }
    }
        
}


