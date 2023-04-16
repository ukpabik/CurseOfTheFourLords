package game;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

import entity.AfterImage;
import entity.NPC_EndingPig;
import monster.MON_DemonLord;
import monster.MON_GrassLord;
import monster.MON_IceLord;
import monster.MON_ThunderLord;
import object.ObjectClosedDoor;

public class Cutscenes {
	Panel p;
	Graphics2D graphics2;
	public int scene, phase;
	int counter = 0;
	float fl = 0f;
	int y;
	String credits;
	
	
	public final int starting = 0;
	public final int demonLord = 1;
	public final int grassLord = 2;
	public final int thunderLord = 3;
	public final int iceLord = 4;
	public final int end = 5;
	
	
	public Cutscenes(Panel p) {
		this.p = p;
		credits ="This is my first project,\nand I hope you all enjoyed.\n"
				+ "This took me about 4 months\nand I am happy with\nhow it came out."
				+ "\nThank you for playing!";
				
	}
	public void scenes() {
		switch(scene) {
		case demonLord: scene_demonLord(); break;
		case thunderLord: scene_thunderLord(); break;
		case iceLord: scene_iceLord(); break;
		case grassLord: scene_grassLord(); break;
		case end: scene_end(); break;
		}
	}
	public void draw(Graphics2D graphics2) {
		this.graphics2 = graphics2;
		scenes();
		
	}
	public void scene_end() {
		String message = "Finally, the warrior has overcome the threat \nto our world. "
				+ "Now, the pigs may live in\n peace without the threat of being turned "
				+ "\ninto a fresh meal for the Lords.\nAs the narrator pig, I give my thanks as well.";
		if (phase == 0) {
			p.stopMusic();
			p.ui.npc = new NPC_EndingPig(p);
			phase++;
		}
		if (phase == 1) {
			p.ui.drawDialogueScreen();
			
		}
		if (phase == 2) {
			p.playEffect(6);
			phase++;
		}
		if (phase == 3) {
			if (waitTime(250) == true) {
				phase++;
			}
		}
		if (phase == 4) {
			fl += 0.004f;
			if (fl > 1f) {
				fl = 1f;
			}
			background(fl);
			if (fl == 1f) {
				fl = 0;
				phase++;
			}
		}
		if (phase == 5) {
			background(1f);
			fl+= 0.004f;
			if (fl > 1f) {
				fl = 1f;
			}
			winMessage(fl, 35f, 200, message, 50);
			
			if (waitTime(500) == true) {
				p.playMusic(0);
				phase++;
			}
		}
		if (phase == 6) {
			background(1f);
			winMessage(1f, 90f, p.screenHeight/2, "Curse of the \nFour Lords", 80);
			if (waitTime(430) == true) {
				phase++;
			}
		}
		if (phase == 7) {
			background(1f);
			
			winMessage(1f, 36f, p.screenHeight/3, credits, 45);
			if (waitTime(430) == true) {
				phase++;
			}
		}
		if (phase == 8) {
			p.gameState = p.titleState;
		}
		
	}
	public void winMessage(float fl, float fontSize, int y, String message, int height) {
		graphics2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fl));
		graphics2.setColor(Color.WHITE);
		graphics2.setFont(graphics2.getFont().deriveFont(fontSize));
		
		for (String line : message.split("\n")) {
			int x = p.ui.getCenterX(line);
			graphics2.drawString(line, x, y);
			y += height;
		}
		graphics2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}
	public void background(float fl) {
		graphics2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fl));
		graphics2.setColor(Color.BLACK);
		graphics2.fillRect(0, 0, p.screenWidth, p.screenHeight);
		graphics2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}
	public boolean waitTime(int time) {
		boolean waitTime = false;
		counter++;
		if (counter > time) {
			waitTime = true;
			counter = 0;
		}
		return waitTime;
	}
	public void scene_demonLord() {
		if (phase == 0) {
			p.battleStart = true;
			
			for (int i = 0; i < p.object[1].length; i++) {
				if (p.object[p.mapCurrent][i] == null) {
					p.object[p.mapCurrent][i] = new ObjectClosedDoor(p);
					p.object[p.mapCurrent][i].worldX = p.tileSize * 27;
					p.object[p.mapCurrent][i].worldY = p.tileSize*17;
					p.object[p.mapCurrent][i].temporary = true;
					p.playEffect(3);
					break;
				}
			}
			
			for (int i = 0; i < p.npc[1].length; i++) {
				if (p.npc[p.mapCurrent][i] == null) {
					p.npc[p.mapCurrent][i] = new AfterImage(p);
					setDirections(p.mapCurrent, i);
					break;
				}
			}
			p.player.draw = false;
			phase++;
			
		}
		if (phase == 1) {
			p.player.worldY += 2;
			if (p.player.worldY >= p.tileSize*34) {
				phase++;
			}
		}
		if (phase == 2) {
			
			for (int i = 0; i < p.mobs[1].length; i++) {
				if (p.mobs[p.mapCurrent][i] != null && 
						p.mobs[p.mapCurrent][i].name.equals(MON_DemonLord.monName)) {
					p.mobs[p.mapCurrent][i].stasis = false;
					p.ui.npc = p.mobs[p.mapCurrent][i];
					phase++;
					break;
				}
			}
		}
		if (phase == 3) {p.ui.drawDialogueScreen();}
		if (phase == 4) {
			for (int i = 0; i < p.npc[1].length; i++) {
				if (p.npc[p.mapCurrent][i] != null && p.npc[p.mapCurrent][i].name.equals(AfterImage.npcName)) {
					p.player.worldX = p.npc[p.mapCurrent][i].worldX;
					p.player.worldY = p.npc[p.mapCurrent][i].worldY;
					
					p.npc[p.mapCurrent][i] = null;
					break;
				}
			}
			p.player.draw = true;
			scene = starting;
			phase = 0;
			p.gameState = p.playState;
		}
		
	}
	public void setDirections(int currentMap, int i) {
		p.npc[p.mapCurrent][i].worldX = p.player.worldX;
		p.npc[p.mapCurrent][i].worldY = p.player.worldY;
		p.npc[p.mapCurrent][i].direction = p.player.direction;
	}
	public void scene_thunderLord() {
		if (phase == 0) {
			p.battleStart = true;
			
			for (int i = 0; i < p.object[1].length; i++) {
				if (p.object[p.mapCurrent][i] == null) {
					p.object[p.mapCurrent][i] = new ObjectClosedDoor(p);
					p.object[p.mapCurrent][i].worldX = p.tileSize * 26;
					p.object[p.mapCurrent][i].worldY = p.tileSize*21;
					p.object[p.mapCurrent][i].temporary = true;
					p.playEffect(3);
					break;
				}
			}

			for (int i = 0; i < p.npc[1].length; i++) {
				if (p.npc[p.mapCurrent][i] == null) {
					p.npc[p.mapCurrent][i] = new AfterImage(p);
					setDirections(p.mapCurrent, i);
					break;
				}
			}
			p.player.draw = false;
			phase++;
			
		}
		if (phase == 1) {
			p.player.worldY += 2;
			if (p.player.worldY >= p.tileSize*34) {
				phase++;
			}
		}
		if (phase == 2) {
			
			for (int i = 0; i < p.mobs[1].length; i++) {
				if (p.mobs[p.mapCurrent][i] != null && 
						p.mobs[p.mapCurrent][i].name.equals(MON_ThunderLord.monName)) {
					p.mobs[p.mapCurrent][i].stasis = false;
					p.ui.npc = p.mobs[p.mapCurrent][i];
					phase++;
					break;
				}
			}
		}
		if (phase == 3) {p.ui.drawDialogueScreen();}
		if (phase == 4) {
			for (int i = 0; i < p.npc[1].length; i++) {
				if (p.npc[p.mapCurrent][i] != null && p.npc[p.mapCurrent][i].name.equals(AfterImage.npcName)) {
					p.player.worldX = p.npc[p.mapCurrent][i].worldX;
					p.player.worldY = p.npc[p.mapCurrent][i].worldY;
					
					p.npc[p.mapCurrent][i] = null;
					break;
				}
			}
			p.player.draw = true;
			scene = starting;
			phase = 0;
			p.gameState = p.playState;
		}
		
		
	}
	public void scene_iceLord() {
		if (phase == 0) {
			p.battleStart = true;
			
			for (int i = 0; i < p.object[1].length; i++) {
				if (p.object[p.mapCurrent][i] == null) {
					p.object[p.mapCurrent][i] = new ObjectClosedDoor(p);
					p.object[p.mapCurrent][i].worldX = p.tileSize*27;
					p.object[p.mapCurrent][i].worldY = p.tileSize*19;
					p.object[p.mapCurrent][i].temporary = true;
					p.playEffect(3);
					break;
				}
			}
		

		for (int i = 0; i < p.npc[1].length; i++) {
			if (p.npc[p.mapCurrent][i] == null) {
				p.npc[p.mapCurrent][i] = new AfterImage(p);
				setDirections(p.mapCurrent, i);
				break;
			}
		}
			p.player.draw = false;
			phase++;
		}
		
	
		if (phase == 1) {
			p.player.worldY += 2;
			if (p.player.worldY > p.tileSize*33) {
				phase++;
			}
		}
		if (phase == 2) {
			
			for (int i = 0; i < p.mobs[1].length; i++) {
				if (p.mobs[p.mapCurrent][i] != null && 
						p.mobs[p.mapCurrent][i].name.equals(MON_IceLord.monName)) {
					p.mobs[p.mapCurrent][i].stasis = false;
					p.ui.npc = p.mobs[p.mapCurrent][i];
					phase++;
					break;
				}
			}
		}
		if (phase == 3) {p.ui.drawDialogueScreen();}
		if (phase == 4) {
			for (int i = 0; i < p.npc[1].length; i++) {
				if (p.npc[p.mapCurrent][i] != null && p.npc[p.mapCurrent][i].name.equals(AfterImage.npcName)) {
					p.player.worldX = p.npc[p.mapCurrent][i].worldX;
					p.player.worldY = p.npc[p.mapCurrent][i].worldY;
					
					p.npc[p.mapCurrent][i] = null;
					break;
				}
			}
			p.player.draw = true;
			scene = starting;
			phase = 0;
			p.gameState = p.playState;
		}
	
		
	}
	public void scene_grassLord() {
		if (phase == 0) {
			p.battleStart = true;
			
			for (int i = 0; i < p.object[1].length; i++) {
				if (p.object[p.mapCurrent][i] == null) {
					p.object[p.mapCurrent][i] = new ObjectClosedDoor(p);
					p.object[p.mapCurrent][i].worldX = p.tileSize * 26;
					p.object[p.mapCurrent][i].worldY = p.tileSize*20;
					p.object[p.mapCurrent][i].temporary = true;
					p.playEffect(3);
					break;
				}
			}

			for (int i = 0; i < p.npc[1].length; i++) {
				if (p.npc[p.mapCurrent][i] == null) {
					p.npc[p.mapCurrent][i] = new AfterImage(p);
					setDirections(p.mapCurrent, i);
					break;
				}
			}
			p.player.draw = false;
			phase++;
			
		}
		if (phase == 1) {
			p.player.worldY += 2;
			if (p.player.worldY >= p.tileSize*34) {
				phase++;
			}
		}
		if (phase == 2) {
			
			for (int i = 0; i < p.mobs[1].length; i++) {
				if (p.mobs[p.mapCurrent][i] != null && 
						p.mobs[p.mapCurrent][i].name.equals(MON_GrassLord.monName)) {
					p.mobs[p.mapCurrent][i].stasis = false;
					p.ui.npc = p.mobs[p.mapCurrent][i];
					phase++;
					break;
				}
			}
		}
		if (phase == 3) {p.ui.drawDialogueScreen();}
		if (phase == 4) {
			for (int i = 0; i < p.npc[1].length; i++) {
				if (p.npc[p.mapCurrent][i] != null && p.npc[p.mapCurrent][i].name.equals(AfterImage.npcName)) {
					p.player.worldX = p.npc[p.mapCurrent][i].worldX;
					p.player.worldY = p.npc[p.mapCurrent][i].worldY;
					
					p.npc[p.mapCurrent][i] = null;
					break;
				}
			}
			p.player.draw = true;
			scene = starting;
			phase = 0;
			p.gameState = p.playState;
		}
		
	}
}
