package notreJeu.entities;

import java.util.Random;

import notreJeu.Team;
import notreJeu.levels.AbstractLevelCTF;
import gameframework.moves_rules.MoveStrategy;

public class IAEntityUnitsVariable extends IAEntitySimple{
	protected int _ecart;
	
	public IAEntityUnitsVariable(AbstractLevelCTF level, Team t, MoveStrategy movestrategy, int nb_riders, int nb_infantryman, int ecart) {
		super(level,t,movestrategy, nb_riders, nb_infantryman);
		_ecart = ecart;
	}

	//retourne une valeur comprise entre nb_man-ecart et nb_man+ecart
	private int getRandom(int nb_man){
		Random rand = new Random();
		return rand.nextInt((nb_man+_ecart) - (nb_man -_ecart) +1)+(nb_man -_ecart);//(nb_man -_ecart) + (Math.random() * ((nb_man+_ecart) - (nb_man -_ecart) +1));
	}
	
	@Override
	public void oneStepMoveAddedBehavior() {
		_timer++;

		if(_timer%50 == 0 ){//&& _level.getArmy(_team)== null){
			_level.addArmy(_team, _movestrategy, getRandom(_nb_riders), getRandom(_nb_infantryman),"IA");
		}
	}
}
