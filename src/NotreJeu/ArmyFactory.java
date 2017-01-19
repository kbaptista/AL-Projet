package NotreJeu;

import java.awt.Canvas;

import soldier.core.AgeAbstractFactory;
import soldier.core.UnitGroup;
import NotreJeu.FirstStep.Equip;
import NotreJeu.entities.Army;

public class ArmyFactory {
	
	public ArmyFactory() {}
	
	public Army getArmy(Canvas canvas, AgeAbstractFactory aaf ,int nb_horseman, int nb_infantry, Equip side, String name){
		UnitGroup group = new UnitGroup(name);
		for (int i = 0; i < nb_horseman; i++)
			group.addUnit(aaf.riderUnit("horse"+i));
		for (int i = 0; i < nb_infantry; i++)
			group.addUnit(aaf.infantryUnit("infantry"+i));
		return new Army(canvas, group , side);
	}
	
	
	
}
