package quiz;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import unit3.Circle;
import unit3.Circle2;

public class GraphicsMain {
	public static void main(String[] args) {
		new GraphicsMain();
	}

	ArrayList<Shape> shapes = new ArrayList<Shape>();
	final static int panW = 800;
	final static int panH = 800;

	GrPanel panel;
	Shape shape;

	GraphicsMain(){
		panel = new GrPanel();

		JFrame window = new JFrame("Shape");
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		for (int i=0; i<5;i++) {
			shapes.add(new Shape());
		}
		window.add(panel);

		window.pack();
		window.setLocationRelativeTo(null); //always in middle
		window.setVisible(true); 

		runGame(); //instead of a timer.

		window.dispose(); //close window when runGame() exits.

	}


	void runGame() {
		int sleeptime = 10;

		while(true) {
			for (Shape c : shapes) {
				c.move();
			}

			try { Thread.sleep(sleeptime); }
			catch (InterruptedException e) {}
			//remove circles when they bounce 4 times		
			for (int i = 0; i < shapes.size(); i++) {
				Shape c = shapes.get(i);	
				if (c.bounceCheck == 4) {
					shapes.remove(i);
					i--;	//  Important! Add this in.
				}
			}
		}
		
	}

	private class GrPanel extends JPanel {
		GrPanel(){	
			this.setPreferredSize(new Dimension(panW, panH)); //the JPanel is the exact dimensions specified and the JFrame adjusts around it due to pack()
			this.setBackground(Color.BLACK);
			//this.setBackground(new Color(0,0,0,10));
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D)g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			for(Shape c : shapes) {
				c.paint(g2);
			}
			panel.repaint();
		}
	}





}
