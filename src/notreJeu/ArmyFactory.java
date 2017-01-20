package notreJeu;

import java.awt.Canvas;
import java.util.LinkedList;
import java.util.Queue;

import notreJeu.entities.Army;
import soldier.core.AgeAbstractFactory;
import soldier.core.Unit;
import soldier.core.UnitGroup;
import soldier.core.Weapon;

public class ArmyFactory implements Cloneable, AgeAbstractFactory{
	
	public AgeAbstractFactory _ageFactory;
	public Queue<GetAgeFactory> _getAgeFactory;
	
	public ArmyFactory(Queue<GetAgeFactory> queueAgeFactory) {
		_getAgeFactory = queueAgeFactory;
		_ageFactory = _getAgeFactory.poll().getAgeFactory();
	}
	
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

	public void evolveAge(){
		_ageFactory = _getAgeFactory.poll().getAgeFactory();
	}
	
	@Override
    protected Object clone() throws CloneNotSupportedException {
		Queue<GetAgeFactory> getAgeFactory = new LinkedList<GetAgeFactory>();
		//Références multiples vers les memes instances. 
		//Pas gênant dans notre usage ici, au contraire, plus léger.
		getAgeFactory.addAll(_getAgeFactory);	
		return new ArmyFactory(getAgeFactory);
    }

	@Override
	public Unit infantryUnit(String name) {
		return _ageFactory.infantryUnit(name);
	}

	@Override
	public Unit riderUnit(String name) {
		return _ageFactory.riderUnit(name);
	}

	@Override
	public Weapon attackWeapon() {
		return _ageFactory.attackWeapon();
	}

	@Override
	public Weapon defenseWeapon() {
		return _ageFactory.defenseWeapon();
	}
	
}
