package me.zombies;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

@SuppressWarnings("serial")
public class Bullet extends Rectangle {
	
	double cx,cy;			//Center Coordinates
	int r = Main.WIN/325;
	double speed = Main.WIN/217;	//Speeds
	double vx, vy;
	int dx, dy;
	double angle;

	Bullet(PlayerStats player) {
		
		dx = Main.mouseX - (player.x+player.width/2);
		dy = Main.mouseY - (player.y+player.height/2);
		angle = Math.atan2(dy,dx);
		 		  
		vx = (speed*Math.cos(angle));
		vy = (speed*Math.sin(angle));
				 
		cx = player.x+player.width/2;
		cy = player.y+player.height/2;
		
		width = r*2;
		height = width;
		
		x = (int) cx;
		y = (int) cy;
	}

	void move() {
		this.cx += vx;
		this.cy += vy;

		x = (int) cx;
		y = (int) cy;
	}

	void paint(Graphics g) {		
		g.setColor(Color.black);
		g.fillOval(x, y, width, height);		
	}
}
