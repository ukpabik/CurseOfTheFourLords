package entity;

import java.awt.Color;
import java.awt.Graphics2D;

import game.Panel;

public class Particle extends Entity
{
    //ENTITY THAT PRODUCES THE PARTICLE
    Entity generator;
    Color particleColor;
    
    int size, x, y;
    
    
    public Particle(Panel p, Entity generator, Color color, 
            int size, int speed, int maxHealth, int x, int y)
    {
        super(p);
        
        this.generator = generator;
        this.size = size;
        this.particleColor = color;
        this.speed = speed;
        this.maxHealth = maxHealth;
        this.x = x;
        this.y = y;
        
        health = maxHealth;
        //TO GET THE PARTICLES TO APPEAR AT THE CENTER OF THE TILE
        int difference = (p.tileSize/2) - (size/2);
        //THE TILE THAT THE PARTICLES WILL BE PLACED
        worldX = generator.worldX + difference;
        worldY = generator.worldY + difference;
        
    }
    
    public void update() {
        
        health--;
        
        //ADDING GRAVITY ASPECT TO PARTICLES
        if (health < maxHealth/3){y++;}
        
        worldX += x*speed;
        worldY += y*speed;
        if (health == 0)
        {
            alive = false;
        }
    }
    public void draw(Graphics2D graph2) {
        int xScreen = worldX - p.player.worldX + p.player.screenX;
        int yScreen = worldY - p.player.worldY + p.player.screenY;
        
        graph2.setColor(particleColor);
        //WIDTH AND HEIGHT IS SIZE
        graph2.fillRect(xScreen, yScreen, size, size);
    }

}
