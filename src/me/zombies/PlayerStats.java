package me.zombies;

class PlayerStats {
	
//Variables
	int x = 100;
	int y = 100;
	
	boolean alive;
	
	String armor, weapon, name;
	
	int HP, maxHP;
	int speed;
	
	PlayerStats(String name){
		this.alive = true;
		this.name = name;
		this.maxHP = 20;
		this.HP = 20;
		this.speed = 5;
		this.armor = "None";
		this.weapon = "Fist";
	}
}
