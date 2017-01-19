/**
 * D. Auber & P. Narbel
 * Solution TD Architecture Logicielle 2016 Universit� Bordeaux.
 */
package soldier.core;

public interface AgeAbstractFactory {
	Unit infantryUnit(String name);

	Unit riderUnit(String name);
/*
	//ADDED
	Unit groupUnit(String name);
	*/
	Weapon attackWeapon();

	Weapon defenseWeapon();
}
