package notreJeu;

import java.awt.Canvas;

import notreJeu.entities.Army;
import soldier.core.AgeAbstractFactory;
import soldier.core.UnitGroup;

public class ArmyFactory {
	
	public ArmyFactory() {}
	
	
	public Army getArmy(Canvas canvas, AgeAbstractFactory aaf ,int nb_horseman, int nb_infantry, String name){
		UnitGroup group = new UnitGroup(name);
		for (int i = 0; i < nb_horseman; i++)
			group.addUnit(aaf.riderUnit("horse"+i));
		for (int i = 0; i < nb_infantry; i++)
			group.addUnit(aaf.infantryUnit("infantry"+i));
		return new Army(canvas, group , null);
	}
	
	public Army getArmy(Canvas canvas, AgeAbstractFactory aaf ,int nb_horseman, int nb_infantry, Team side, String name){
		Army a = this.getArmy(canvas, aaf, nb_horseman, nb_infantry, name);
		a.setTeam(side);
		return a;
	}

	
}
