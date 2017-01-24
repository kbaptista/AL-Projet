package notreJeu;

import java.awt.Canvas;
import java.util.LinkedList;
import java.util.Queue;

import notreJeu.entities.Army;
import soldier.core.AgeAbstractFactory;
import soldier.core.UnitGroup;

public class ArmyFactory implements Cloneable{
	
	public AgeAbstractFactory _ageFactory=null;
	public Queue<GetAgeFactory> _getAgeFactory=null;
	
	public ArmyFactory(Queue<GetAgeFactory> queueAgeFactory) throws Exception {
		_getAgeFactory = queueAgeFactory;
		if(!_getAgeFactory.isEmpty()){
			_ageFactory = _getAgeFactory.poll().getAgeFactory();
		}
		else
			throw new Exception("ArmyFactory constructor need a queue with at least a value.");
			
	}
	
	public Army getArmy(Canvas canvas,int nb_horseman, int nb_infantry, String name){
		UnitGroup group = new UnitGroup(name);
		for (int i = 0; i < nb_horseman; i++)
			group.addUnit(_ageFactory.riderUnit("horse"+i));
		for (int i = 0; i < nb_infantry; i++)
			group.addUnit(_ageFactory.infantryUnit("infantry"+i));
		return new Army(canvas, group , null);
	}
	
	public Army getArmy(Canvas canvas,int nb_horseman, int nb_infantry, Team side, String name){
		Army a = getArmy(canvas, nb_horseman, nb_infantry, name);
		a.setTeam(side);
		return a;
	}

	public void evolveAge(){
		if(!_getAgeFactory.isEmpty())
			_ageFactory = _getAgeFactory.poll().getAgeFactory();
	}
	
	@Override
    protected Object clone() throws CloneNotSupportedException {
		Queue<GetAgeFactory> getAgeFactory = new LinkedList<GetAgeFactory>();
		//Références multiples vers les memes instances. 
		//Pas gênant dans notre usage ici, au contraire, plus léger.
		getAgeFactory.addAll(_getAgeFactory);
		try{
			return new ArmyFactory(getAgeFactory);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
    }
	
}
