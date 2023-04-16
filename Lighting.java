package environment;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import game.Panel;

public class Lighting
{
    Panel p;
    
    //DARKNESS
    BufferedImage darknessFilter;
    float filterAlpha = 0f;
    
    
    //TIME OF DAY
    int dayCounter;
    final int day = 0;
    final int dusk = 1;
    final int night = 2;
    final int dawn = 3;
    int dayState = day;
    
    
    public Lighting(Panel p) {
        this.p = p;
        setLightSource();
    }
    public void setLightSource() {
      //CREATE IMAGE
        darknessFilter = new BufferedImage(p.screenWidth, p.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graph2 = (Graphics2D) darknessFilter.getGraphics();
        
        if (p.player.lightSource == null) {
            //NO LIGHT SOURCE MAKES THE SCREEN VERY DARK
            graph2.setColor(new Color(0,0,0.1f,0.83f));
            
        }
        
        else {
          //CIRCLE DATA
            int centerX = p.player.screenX + (p.tileSize)/2;
            int centerY = p.player.screenY + (p.tileSize)/2;
            

            //CREATE GRADIENT EFFECT WITH THE VIEW CIRCLE
            Color color[] = new Color[5];
            float fraction[] = new float[5];
            
            color[0] = new Color(0,0,0.1f,0f);
            color[1] = new Color(0,0,0.1f,0.25f);
            color[2] = new Color(0,0,0.1f,0.5f);
            color[3] = new Color(0,0,0.1f,0.75f);
            color[4] = new Color(0,0,0.1f,.95f);
            
            //DISTANCE FROM PLAYER TO EDGE OF CIRCLE
            fraction[0] = 0f;
            fraction[1] = 0.25f;
            fraction[2] = 0.5f;
            fraction[3] = 0.75f;
            fraction[4] = 1f;
            
            //CREATE SETTINGS TO PAINT THE SCREEN
            
            RadialGradientPaint rPaint = new RadialGradientPaint(centerX, centerY, 
                p.player.lightSource.lightRadius, fraction, color);
            
            //APPLYING THE GRADIENT DATA
            graph2.setPaint(rPaint);
        }
        
        graph2.fillRect(0,0,p.screenWidth, p.screenHeight);
        graph2.dispose();
    }
    
    public void update() {
        if (p.player.lightUpdate == true) {
            setLightSource();
            p.player.lightUpdate = false;
        }
        
        
        updateTime();
    }
    public void updateTime() {
    	//CHECKING THE TIME OF DAY
        //DAYTIME
        if (dayState == day) {
            dayCounter++;
            if (dayCounter > 1500) {
                dayState = dusk;
                dayCounter = 0;
            }
        }
        //DUSK
        if (dayState == dusk) {
            //ALPHA FILTER OF 0 means that it is transparent, increasing it decreases transparency
            filterAlpha += 0.0001f;
            
            if (filterAlpha > 1f) {
                filterAlpha = 1f;
                dayState = night;
            }
        }
        //NIGHT
        if (dayState == night) {
            dayCounter++;
            
            if (dayCounter > 1500) {
                dayState = dawn;
                dayCounter = 0;
            }
        }
        
        //DAWN
        if (dayState == dawn) {
            filterAlpha -= 0.0001f;
            
            if (filterAlpha < 0f) {
                filterAlpha = 0f;
                dayState = day;
            }
        }
		
	}
	public void resetTime() {
        dayState = day;
        filterAlpha = 0f;
    }
    public void draw(Graphics2D graph2) {
        if (p.currentArea == p.outside) {
            graph2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, filterAlpha));
        }
        if (p.currentArea == p.outside || p.currentArea == p.dungeon) {
            graph2.drawImage(darknessFilter, 0,0,null);
        }
        
        graph2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        
        //DEBUGGING
        String timePeriod = "";
        switch(dayState) {
            case day: timePeriod = "Day"; break;
            case dusk: timePeriod = "Dusk"; break;
            case night: timePeriod = "Night"; break;
            case dawn: timePeriod = "Dawn"; break;
        }
        graph2.setColor(Color.white);
        graph2.setFont(graph2.getFont().deriveFont(30f));
        graph2.drawString(timePeriod, 830, 500);
    }
}
