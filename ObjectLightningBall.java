package object;

import java.awt.Color;

import entity.Entity;
import entity.Projectile;
import game.Panel;

public class ObjectLightningBall extends Projectile
{
    public static final String objName = "Lightning Ball";
    Panel p;

    public ObjectLightningBall(Panel p)
    {
        super(p);
        this.p = p;
        
        //STATS FOR FIREBALL
        name = objName;
        speed = 8;
        maxHealth = 120;
        health = maxHealth;
        attack = 2;
        useCost = 1;
        alive = false;
        getImage();
        
    }
    
    public void getImage()
    {
        //CREATE ALL IMAGES FOR PROJECTILE
        up1 = setup("/projectile/lightning-u1", p.tileSize, p.tileSize);
        up2 = setup("/projectile/lightning-u2", p.tileSize, p.tileSize);
        down1 = setup("/projectile/lightning-d1", p.tileSize, p.tileSize);
        down2 = setup("/projectile/lightning-d2", p.tileSize, p.tileSize);
        left1 = setup("/projectile/lightning-l1", p.tileSize, p.tileSize);
        left2 = setup("/projectile/lightning-l2", p.tileSize, p.tileSize);
        right1 = setup("/projectile/lightning-r1", p.tileSize, p.tileSize);
        right2 = setup("/projectile/lightning-r2", p.tileSize, p.tileSize);
    }
    
    public boolean haveProjectile(Entity user)
    {
        boolean haveProjectile = true;
        return haveProjectile;
    }
    public void subtractProjectile(Entity user)
    {
        
    }
    public Color getParticleColor()
    {
        Color color = new Color(255,255,0);
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
