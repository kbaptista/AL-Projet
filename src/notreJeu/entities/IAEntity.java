package notreJeu.entities;

import java.awt.Rectangle;

import notreJeu.Team;
import notreJeu.levels.AbstractLevelCTF;
import gameframework.core.GameEntity;
import gameframework.core.GameMovable;
import gameframework.moves_rules.MoveStrategy;

public class IAEntity extends GameMovable implements GameEntity{

	private int _timer;
	private AbstractLevelCTF _level;
	private Team _team;
	private MoveStrategy _movestrategy;
	public static final int RENDERING_SIZE = 32;
	
	public IAEntity(AbstractLevelCTF level, Team t, MoveStrategy movestrategy) {
		_timer = 0;
		_level = level;
		_team = t;
		_movestrategy = movestrategy;
	}
	
	@Override
	public Rectangle getBoundingBox() {return new Rectangle(0, 0, RENDERING_SIZE, RENDERING_SIZE);}

	@Override
	public void oneStepMoveAddedBehavior() {
		_timer++;
		if(_timer%50 == 0 && _level.getArmy(_team)== null){
			_level.addArmy(_team, _movestrategy, 2, 2,"IA");
		}
	}
}
