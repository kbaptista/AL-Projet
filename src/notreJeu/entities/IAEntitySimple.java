package notreJeu.entities;

import java.awt.Rectangle;

import notreJeu.Team;
import notreJeu.levels.AbstractLevelCTF;
import gameframework.core.GameEntity;
import gameframework.core.GameMovable;
import gameframework.moves_rules.MoveStrategy;

public class IAEntitySimple extends GameMovable implements GameEntity{

	protected int _timer;
	protected AbstractLevelCTF _level;
	protected Team _team;
	protected MoveStrategy _movestrategy;
	protected int _nb_horse, _nb_infant;
	public static final int RENDERING_SIZE = 32;
	
	public IAEntitySimple(AbstractLevelCTF level, Team t, MoveStrategy movestrategy, int nb_horseman, int nb_infantryman) {
		_timer = 0;
		_level = level;
		_team = t;
		_movestrategy = movestrategy;
		_nb_horse = nb_horseman;
		_nb_infant = nb_infantryman;
	}
	
	@Override
	public Rectangle getBoundingBox() {return new Rectangle(0, 0, RENDERING_SIZE, RENDERING_SIZE);}

	@Override
	public void oneStepMoveAddedBehavior() {
		_timer++;
		if(_timer%30 == 0 ){//&& _level.getArmy(_team)== null){
			_level.addArmy(_team, _movestrategy, _nb_horse, _nb_infant,"IA");
		}
	}
}
