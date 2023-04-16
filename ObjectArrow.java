package object;

import java.awt.Color;

import entity.Entity;
import entity.Projectile;
import game.Panel;

public class ObjectArrow extends Projectile
{
    public static final String objName = "Arrow";
    
    Panel p;

    public ObjectArrow(Panel p)
    {
        super(p);
        this.p = p;
        
        //STATS FOR ARROW
        name = objName;
        speed = 8;
        maxHealth = 120;
        health = maxHealth;
        attack = 3;
        useCost = 1;
        alive = false;
        getImage();
        
    }
    
    public void getImage()
    {
        //CREATE ALL IMAGES FOR PROJECTILE
        up1 = setup("/projectile/arrowup1", p.tileSize, p.tileSize);
        up2 = setup("/projectile/arrowup2", p.tileSize, p.tileSize);
        down1 = setup("/projectile/arrowdown1", p.tileSize, p.tileSize);
        down2 = setup("/projectile/arrowdown2", p.tileSize, p.tileSize);
        left1 = setup("/projectile/arrowleft1", p.tileSize, p.tileSize);
        left2 = setup("/projectile/arrowleft2", p.tileSize, p.tileSize);
        right1 = setup("/projectile/arrowright1", p.tileSize, p.tileSize);
        right2 = setup("/projectile/arrowright2", p.tileSize, p.tileSize);
    }
    
    public boolean haveProjectile(Entity user)
    {
        boolean haveProjectile = false;
        if (user.projectileAmount >= useCost)
        {
            haveProjectile = true;
        }
        return haveProjectile;
    }
    public void subtractProjectile(Entity user)
    {
        user.projectileAmount -= useCost;
    }
    public Color getParticleColor()
    {
        Color color = new Color(150, 75, 0);
        return color;
    }
    public int getParticleSize()
    {
        //SIZE IN PIXELS
        int size = 10;
        return size;
    }
    public int getParticleSpeed()
    {
        //HOW FAST PARTICLE FLIES
        int speed = 1;
        return speed;
    }

    public int getParticleMaxHealth()
    {
        //HOW LONG THE PARTICLE LASTS ON THE SCREEN
        int maxHealth = 20;
        return maxHealth;
    }
}
