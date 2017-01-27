package notreJeu;

import java.awt.Canvas;
import java.util.LinkedList;
import java.util.Queue;

import gameframework.moves_rules.MoveBlockerChecker;
import gameframework.moves_rules.MoveStrategy;
import gameframework.moves_rules.MoveStrategyKeyboard;
import notreJeu.entities.Army;
import soldier.core.AgeAbstractFactory;
import soldier.core.UnitGroup;

public class ArmyFactory implements Cloneable{
	
	public AgeAbstractFactory _ageFactory=null;
	public Queue<GetAgeFactory> _getAgeFactory=null;
	private Canvas _canvas;
	
	public ArmyFactory(Queue<GetAgeFactory> queueAgeFactory, Canvas canvas) throws Exception {
		_canvas = canvas;
		_getAgeFactory = queueAgeFactory;
		if(!_getAgeFactory.isEmpty()){
			_ageFactory = _getAgeFactory.poll().getAgeFactory();
		}
		else
			throw new Exception("ArmyFactory constructor need a queue with at least a value.");
	}
	
	public Army getArmy(int nb_riders, int nb_infantry, String name){
		//TODO : retablir encapsulation 
		UnitGroup group = initArmy(nb_riders, nb_infantry, name);
		return new Army(_canvas, group , null, nb_riders, nb_infantry);
	}
	
	public Army getArmy(int nb_riders, int nb_infantry, Team side, String name, MoveStrategy move){
		UnitGroup group = initArmy(nb_riders, nb_infantry, name);
		
		if(move instanceof MoveStrategyKeyboard)
			_canvas.addKeyListener((MoveStrategyKeyboard)move);
		
		return new Army(_canvas, group, side, nb_riders, nb_infantry);
	}
	
	private UnitGroup initArmy(int nb_riders, int nb_infantry, String name){
		UnitGroup group = new UnitGroup(name);
		for (int i = 0; i < nb_riders; i++)
			group.addUnit(_ageFactory.riderUnit("rider"+i));
		for (int i = 0; i < nb_infantry; i++)
			group.addUnit(_ageFactory.infantryUnit("infantry"+i));
		return group;
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
			return new ArmyFactory(getAgeFactory, _canvas);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
    }
	
}
