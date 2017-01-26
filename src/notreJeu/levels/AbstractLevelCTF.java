package notreJeu.levels;

import java.awt.Canvas;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import notreJeu.ArmyFactory;
import notreJeu.Team;
import notreJeu.entities.Army;
import notreJeu.rules.MoveStrategyOnClickStraightLine;

public abstract class AbstractLevelCTF extends GameLevelDefaultImpl{

	Canvas _canvas;
	public String _background_path = "images/ctf_grass.png";

	protected int _width = 31;
	protected int _height = 31;
	protected int[][] _map = generateMap(); 
	
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
	
	class AddSoldierButtonAction implements ActionListener{
		private int nb_soldier = 0;
		private JButton button;
		private String type ;
		
		public AddSoldierButtonAction(JButton but, String type) {
			button = but;
			this.type = type;
		}
		
		public String getType(){return type;}
		public int getValue(){return nb_soldier;}
		public void setValue(int i){
			nb_soldier=i;
			button.setText(String.valueOf(nb_soldier));
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			nb_soldier++;
			button.setText(String.valueOf(nb_soldier));
		}
	}
	class ReleaseArmyButtonAction implements ActionListener{
		
		private Set<AddSoldierButtonAction> instanciateActions;
		
		public ReleaseArmyButtonAction(ActionListener[] list) {
			instanciateActions = new HashSet<AddSoldierButtonAction>();
			for (ActionListener act : list) {
				instanciateActions.add((AddSoldierButtonAction)act);
			}
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(true){//TODO : vérifier si le joueur possède une équipe

				int nb_infantryman = 0 ;
				int nb_horseman = 0 ;
				for(AddSoldierButtonAction action : instanciateActions){
					if(action.getType().matches("horseman")){nb_horseman = action.getValue();}
					if(action.getType().matches("infantryman")){nb_infantryman = action.getValue();}
					action.setValue(0);

					System.out.println(((AddSoldierButtonAction) action).type + " " +
							String.valueOf(nb_infantryman) + " | " +
							String.valueOf(nb_horseman)
					);
				}
				Team t = _teams_played.iterator().next();
				ArmyFactory a =t.getArmyFactory();
				addArmy(a.getArmy(_canvas, nb_horseman, nb_infantryman, t, "Player"+String.valueOf(t.getSide()))/*, 
						new MoveStrategyOnClickStraightLine(t.getPosition(), t.getPosition())*/);
			}
		}
	}
	
	public AddSoldierButtonAction getAddSoldierButtonAction(JButton but, String type){
		return new AddSoldierButtonAction(but, type);
	}
	
	public ReleaseArmyButtonAction getReleaseArmyButtonAction(ActionListener[] list){
		
		return new ReleaseArmyButtonAction(list);
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
	
	public void addArmy(Army army, MoveStrategyOnClickStraightLine move){
		GameMovableDriverDefaultImpl armyDriver = new GameMovableDriverDefaultImpl();
		armyDriver.setStrategy(move);
		armyDriver.setmoveBlockerChecker(moveBlockerChecker);
		_canvas.addMouseListener(move);
		army.setDriver(armyDriver);
		try{
			army.setTeam(_teams_played.iterator().next());
		}catch(Exception e){e.printStackTrace();}
		Point pos = army.getTeam().getPosition();
		pos.setLocation(pos.getX(), pos.getY());
		army.setPosition(pos);
		universe.addGameEntity(army);
	}
	
	public void addArmy(Team t, MoveStrategy move_strat, int nb_horseman, int nb_infantryman, String name){
		GameMovableDriverDefaultImpl armyDriver = new GameMovableDriverDefaultImpl();
		armyDriver.setStrategy(move_strat);
		armyDriver.setmoveBlockerChecker(moveBlockerChecker);
		
		Army army = t.getArmyFactory().getArmy(_canvas, nb_horseman, nb_infantryman, name);
		army.setDriver(armyDriver);
		try{
			army.setTeam(_teams_ia.iterator().next());
		}catch(Exception e){e.printStackTrace();}
		Point pos = army.getTeam().getPosition();
		pos.setLocation(pos.getX(), pos.getY());
		army.setPosition(pos);
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
		
	public void setSpriteSize(int size){
		SPRITE_SIZE = size;
	}
}
