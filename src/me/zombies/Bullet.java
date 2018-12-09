package me.zombies;

import java.awt.Color;
import java.awt.Graphics;
//import java.awt.Rectangle;

public class Bullet {
	double cx,cy;			//Center Coordinates
	int x,y;				//int versions;
	int r = 2;
	double speed = 3;		//Speeds
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
	}

	void move() {
		this.cx += vx;
		this.cy += vy;

		x = (int) cx;
		y = (int) cy;
	}

	void paint(Graphics g) {		
		g.setColor(Color.black);
		g.fillOval(x, y, r*2, r*2);		
	}
}
