package me.zombies;

class PlayerStats {
	
//Static Variables
	static boolean alive = true;
	static int x = 100;
	static int y = 100;
	
//Instance varaibles
	private String name;
	private int HP;
	private int maxHP;
	private int speed;
	
	
	
	PlayerStats(String name){
		this.name = name;
		this.maxHP = 20;
		this.HP = 20;
		this.speed = 50;
		
	}
	
	void up() {
		if (y<0) y = 0;
		else y-=speed;
		PlayerMove.window.repaint();
	}
	
	void down() {
		if (y>PlayerMove.WIN) y = PlayerMove.WIN;
		else y+=speed;
		PlayerMove.window.repaint();
	}
	
	void left() {
		if (x<0) x = 0;
		else x-=speed;
		PlayerMove.window.repaint();
	}
	
	void right() {
		if (x>PlayerMove.WIN) x = PlayerMove.WIN;
		else x+=speed;
		PlayerMove.window.repaint();
	}
}
