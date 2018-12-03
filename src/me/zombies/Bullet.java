package me.zombies;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import unit2.LoadingIcon;

public class Bullet {
	double cx,cy;//Centre Coordinates
	int x,y;	//int versions;
	int r = 25;
	double vx,vy,vx2,vy2;//Speeds
	
	int red = (int)(Math.random()*250);
	int green = (int)(Math.random()*250);
	int blue = (int)(Math.random()*250);

	Bullet(){
	
		cx = Main.Player.x+14;
		cy= Main.Player.y;
		
		vx = -8.0;
		vy = -0.0;
	}

	void paint(Graphics g) {		
		g.setColor(Black);
		g.fillOval(x-r, y-r, r*2, r*2);		
	}
	
	void move() {
		this.cy += this.vy;
		this.vy += LoadingIcon.g2;
		
		x = (int) cx;
		y = (int) cy;
	}
	
	boolean intersects(Rectangle rect, Rectangle rect2, Rectangle rect3) {
		if (rect.contains(cx, cy-r)) return true;
		if (rect.contains(cx, cy+r)) return true;
		if (rect2.contains(cx, cy-r)) return true;
		if (rect2.contains(cx, cy+r)) return true;
		if (rect3.contains(cx, cy-r)) return true;
		if (rect3.contains(cx, cy+r)) return true;
		
		
		return false;
	}
}
