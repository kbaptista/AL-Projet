/**
 * D. Auber & P. Narbel
 * Solution TD Architecture Logicielle 2016 Universit� Bordeaux.
 */
package soldier.ages;

import soldier.core.AgeAbstractFactory;
import soldier.core.Unit;
import soldier.core.UnitGroup;
import soldier.core.Weapon;
import soldier.units.UnitBikerMan;
import soldier.units.UnitRobot;
import soldier.weapon.WeaponGun;
import soldier.weapon.WeaponShield;

public class AgeFutureFactory implements AgeAbstractFactory {

	@Override
	public Unit infantryUnit(String name) {
		return new UnitRobot(name);
	}

	@Override
	public Unit riderUnit(String name) {
		return new UnitBikerMan(name);
	}
/*
	//ADDED
	@Override
	public Unit groupUnit(String name) {
		return new UnitGroup(name);
	}
	*/
	@Override
	public Weapon attackWeapon() {
		return new WeaponGun();
	}

	@Override
	public Weapon defenseWeapon() {
		return new WeaponShield();
	}
}
