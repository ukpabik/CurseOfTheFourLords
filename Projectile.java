package entity;

import game.Panel;

public class Projectile extends Entity
{
    Entity user;
    
    public Projectile(Panel p)
    {
        super(p);
        
    }

    public void set(int worldX, int worldY, String direction, boolean alive, Entity user)
    {
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive = alive;
        this.user = user;
        //RESETS HEALTH EVERYTIME YOU SHOOT
        this.health = this.maxHealth;
    }
    public void update()
    {
        
        if (user == p.player)
        {
            int monsterIndex = p.cd.checkEntity(this, p.mobs);
            if (monsterIndex != 999)
            {
                //USES PROJECTILE ATTACK INSTEAD OF MELEE ATTACK
                p.player.damageMonster(monsterIndex, this, attack*(p.player.level/2), knockbackPower);
                //PARTICLE SPAWNS FROM THE PROJECTILE HITTING THE MONSTER
                generateParticle(user.projectile, p.mobs[p.mapCurrent][monsterIndex]);
                //IF PROJECTILE HITS A MONSTER, IT DISAPPEARS
                alive = false;
            }
        }
        else if (user != p.player)
        {
            boolean contactPlayer = p.cd.checkPlayer(this);
            if (p.player.invincible == false && contactPlayer == true)
            {
                damagePlayer(attack);
                generateParticle(user.projectile, user.projectile);
                alive = false;
            }
        }
        
        
        
        switch(direction)
        {
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
        
        health--;
        //DISAPPEARS AFTER HEALTH IS 0, DEPLETES BY 1 EACH FRAME, BASED ON HEALTH VALUE
        if (health <= 0)
        {
            alive = false;
        }
        
        
        spriteCount++;
        if (spriteCount > 12)
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
    public boolean haveProjectile(Entity user)
    {
        boolean haveProjectile = false;
        return haveProjectile;
    }
    public void subtractProjectile(Entity user)
    {
        
    }
}
