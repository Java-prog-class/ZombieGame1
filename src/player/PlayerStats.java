package player;

public class PlayerStats 
{
	
//Variables
	int x = 0;
	int y = 0;
	
	boolean alive;
	
	String armor, weapon, name;
	
	int HP, maxHP;
	int speed;
	
	public PlayerStats(String name){
		this.alive = true;
		this.name = name;
		this.maxHP = 20;
		this.HP = 20;
		this.speed = 5;
		this.armor = "None";
		this.weapon = "Fist";
	}
}
