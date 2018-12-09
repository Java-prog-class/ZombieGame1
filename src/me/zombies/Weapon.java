package me.zombies;

class Weapon {
	int dmg;			// <---- Damage the weapon does
	int ammo;			// <---- How much ammo the weapon has
	String name;		// <---- What the weapon is called

	Weapon(int dmg, int ammo, String name){
		this.dmg = dmg;
		this.ammo = ammo;
		this.name = name;
	}
}

