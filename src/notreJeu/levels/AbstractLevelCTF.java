package notreJeu.levels;

import java.awt.Canvas;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JButton;

import gameframework.core.Game;
import gameframework.core.GameEntity;
import gameframework.core.GameLevelDefaultImpl;
import gameframework.core.GameMovableDriverDefaultImpl;
import gameframework.moves_rules.MoveBlockerChecker;
import gameframework.moves_rules.MoveStrategy;
import gameframework.moves_rules.MoveStrategyKeyboard;
import gameframework.moves_rules.MoveStrategyRandom;

import notreJeu.Team;
import notreJeu.ArmyFactory;
import notreJeu.entities.Army;
import notreJeu.buttons.ActionRecruitSoldiers;
import notreJeu.buttons.ActionReleaseArmy;

public abstract class AbstractLevelCTF extends GameLevelDefaultImpl{
	
	Canvas _canvas;
	public String _background_path = "images/ctf_grass.png";

	protected int _width;
	protected int _height;
	protected int[][] _map;
	
	protected MoveBlockerChecker moveBlockerChecker;
	protected Set<Team> _teams_played;
	protected Set<Team> _teams_ia;
	
	protected static int SPRITE_SIZE = 32;
	
	public AbstractLevelCTF(Game g) {
		super(g);
		_teams_played = new HashSet<Team>();
		_teams_ia = new HashSet<Team>();
	}
	
	protected abstract int[][] generateMap();
	
	public ActionRecruitSoldiers getAddSoldierButtonAction(JButton but, String type, int cost){
		return new ActionRecruitSoldiers(but, type, cost);
	}
	
	public ActionReleaseArmy getReleaseArmyButtonAction(MouseListener[] list, JButton button){
		
		return new ActionReleaseArmy(button);
	}
	
	public void addArmy(Army army){
		GameMovableDriverDefaultImpl armyDriver = new GameMovableDriverDefaultImpl();
		MoveStrategyKeyboard move = new MoveStrategyKeyboard();
		armyDriver.setStrategy(move);
		armyDriver.setmoveBlockerChecker(moveBlockerChecker);
		_canvas.addKeyListener(move);
		army.setDriver(armyDriver);
		army.setTeam(_teams_played.iterator().next()); // get(0) parce que la première équipe intégrée est le joueur.
		Point pos = army.getTeam().getPosition();
		pos.setLocation(pos.getX(), pos.getY());
		army.setPosition(pos);
		universe.addGameEntity(army);
	}
		
	public void addArmy(Team t, MoveStrategy move_strat, int nb_riders, int nb_infantryman){
		Army army = t.createArmy(nb_riders, nb_infantryman, moveBlockerChecker);
		universe.addGameEntity(army);
	}
	
	public Army getArmy(Team t){
		Iterator<GameEntity> it = universe.gameEntities();
		GameEntity ge;
		while(it.hasNext()){
			ge = it.next();
			if(ge instanceof Army){
				if(((Army)ge).getTeam().getSide() == t.getSide())
					return (Army)ge;
			}	
		}
		return null;
	}
	
	public void cleanUniverse(){
		Iterator<GameEntity> entities = universe.gameEntities();
		while(entities.hasNext()){
			entities.next();
			entities.remove();
		}
	}
	

	public void setSpriteSize(int size){
		SPRITE_SIZE = size;
	}
}
