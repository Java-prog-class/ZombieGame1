package me.zombies;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;



public class Bullet {
	double cx,cy;//Centre Coordinates
	int x,y;	//int versions;
	int r = 2;
	double vx,vy,vx2,vy2;//Speeds
	
	int deltaX = Main.mouseX-Main.Player.x; 									// <---- Subtracting the Player location from the Mouse Location
	int deltaY = Main.mouseY-Main.Player.y;
	int angle = (int) Math.toDegrees(Math.atan2(deltaX, -deltaY)); 	// <---- The angle of rotation
	
	Bullet(){
		cx = angle;
		cy= angle;

		vx = -100.0;
		vy = -100.0;
	}

	void move() {
		this.cx += 1;
		this.cy += 1;

		x = (int) cx;
		y = (int) cy;
	}

	void paint(Graphics g) {		
		g.setColor(Color.black);
		g.fillOval(x-r, y-r, r*2, r*2);		
	}
}
