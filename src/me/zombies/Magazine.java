package me.zombies;

import java.awt.Color;
import java.awt.Graphics;
//import java.awt.Rectangle;

public class Magazine {
	int x,y;				//int versions;
	int ammo = 6;
	
	

	Magazine() {	
	}

	void paint(Graphics g) {		
		g.setColor(Color.black);
		g.fillOval(200, 200, 10, 10);		
	}
}
