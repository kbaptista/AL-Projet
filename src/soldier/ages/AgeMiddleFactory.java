/**
 * D. Auber & P. Narbel
 * Solution TD Architecture Logicielle 2016 Universit� Bordeaux.
 */
package soldier.ages;

import soldier.core.AgeAbstractFactory;
import soldier.core.Unit;
import soldier.core.UnitGroup;
import soldier.core.Weapon;
import soldier.units.UnitCenturion;
import soldier.units.UnitHorseMan;
import soldier.weapon.WeaponShield;
import soldier.weapon.WeaponSword;

public class AgeMiddleFactory implements AgeAbstractFactory {

	@Override
	public Unit infantryUnit(String name) {
		return new UnitCenturion(name);
	}

	@Override
	public Unit riderUnit(String name) {
		return new UnitHorseMan(name);
	}

	//ADDED
	@Override
	public Unit groupUnit(String name) {
		return new UnitGroup(name);
	}
	
	@Override
	public Weapon attackWeapon() {
		return new WeaponSword();
	}

	@Override
	public Weapon defenseWeapon() {
		return new WeaponShield();
	}
}
