package environment;

import java.awt.Graphics2D;

import game.Panel;

public class EnvironmentGuide{
    Panel p;
    public Lighting lighting;
    
    public EnvironmentGuide(Panel p) {
        this.p = p;
    }
    
    public void setup() {
        lighting = new Lighting(p);
        
    }
    public void update() {
        lighting.update();
    }
    public void draw(Graphics2D graph2) {
        
        lighting.draw(graph2);
    }
}
