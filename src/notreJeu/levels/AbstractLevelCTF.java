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
	
	class AddSoldierButtonAction implements MouseListener{
		private int nb_soldier = 0;
		private int cost ;
		private JButton button;
		private String type ;
		
		public AddSoldierButtonAction(JButton but, String type, int cost) {
			button = but;
			this.type = type;
			this.cost = cost;
			button.setText(String.valueOf(nb_soldier));
		}
		
		public int getCost(){return cost;}
		public String getType(){return type;}
		public int getValue(){return nb_soldier;}
		
		public void setValue(int i){
			nb_soldier=i;
			button.setText(String.valueOf(nb_soldier));
		}

		private void decrease(){
			nb_soldier--;
			button.setText(String.valueOf(nb_soldier));
		}
		private void increase(){
			nb_soldier++;
			button.setText(String.valueOf(nb_soldier));
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if (e.isShiftDown()){
				decrease();
			}else{
				increase();
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	class ReleaseArmyButtonAction implements ActionListener{
		
		private Set<AddSoldierButtonAction> instanciateActions;
		private JButton button;
		
		public ReleaseArmyButtonAction(MouseListener[] list, JButton button) {
			this.button=button;
			instanciateActions = new HashSet<AddSoldierButtonAction>();
			for (MouseListener act : list) {
				instanciateActions.add((AddSoldierButtonAction)act);
			}
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {

			int nb_infantryman = 0 ;
			int nb_riders = 0 ;
			if(true){//TODO : vérifier si le joueur possède une équipe

				int army_cost = 0;
				for(AddSoldierButtonAction action : instanciateActions){
					if(action.getType().matches("rider")){
						nb_riders = action.getValue();
						army_cost+= nb_riders*action.getCost();
					}
					if(action.getType().matches("infantryman")){
						nb_infantryman = action.getValue();
						army_cost+= nb_infantryman*action.getCost();
					}
					action.setValue(0);
				}
				Team t = _teams_played.iterator().next();
				System.out.println("Army_cost = " + army_cost);
				if (army_cost<=t.get_gold().getValue()){
					ArmyFactory a =t.getArmyFactory();
					addArmy(a.getArmy(_canvas, nb_riders, nb_infantryman, t, "Player"+String.valueOf(t.getSide()))/*, 
							new MoveStrategyOnClickStraightLine(t.getPosition(), t.getPosition())*/);
					button.setText("0");
				}else {
					//if the army is too expensive, it is not instanciate and the buttons are given back their values
					for(AddSoldierButtonAction action : instanciateActions){
						if(action.getType().matches("rider")){action.setValue(nb_riders);}
						if(action.getType().matches("infantryman")){action.setValue(nb_infantryman);}
					}
					button.setText(String.valueOf(army_cost));
				}
			}
		}
	}
	
	public AddSoldierButtonAction getAddSoldierButtonAction(JButton but, String type, int cost){
		return new AddSoldierButtonAction(but, type, cost);
	}
	
	public ReleaseArmyButtonAction getReleaseArmyButtonAction(MouseListener[] list, JButton button){
		
		return new ReleaseArmyButtonAction(list, button);
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
	
	public void addArmy(Team t, MoveStrategy move_strat, int nb_riders, int nb_infantryman, String name){
		GameMovableDriverDefaultImpl armyDriver = new GameMovableDriverDefaultImpl();
		armyDriver.setStrategy(move_strat);
		armyDriver.setmoveBlockerChecker(moveBlockerChecker);
		
		Army army = t.getArmyFactory().getArmy(_canvas, nb_riders, nb_infantryman, name);
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
