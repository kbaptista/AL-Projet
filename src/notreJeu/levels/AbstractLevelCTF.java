package notreJeu.levels;

import java.awt.Canvas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JButton;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import gameframework.core.Game;
import gameframework.core.GameLevelDefaultImpl;
import gameframework.core.GameMovableDriverDefaultImpl;
import gameframework.moves_rules.MoveBlockerChecker;
import gameframework.moves_rules.MoveStrategyKeyboard;
import notreJeu.ArmyFactory;
import notreJeu.Team;
import notreJeu.entities.Army;

public abstract class AbstractLevelCTF extends GameLevelDefaultImpl{

	Canvas _canvas;
	public String _background_path = "images/ctf_grass.png";

	protected int _width = 31;
	protected int _height = 31;
	protected int[][] _map = generateMap(); 
	
	protected MoveBlockerChecker moveBlockerChecker;
	protected Set<Team> _teams;
	
	protected static final int SPRITE_SIZE = 32;
	
	public AbstractLevelCTF(Game g) {
		super(g);
		
		_teams = new HashSet<Team>(); 
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
				}
				Team t = _teams.iterator().next(); //first team is the player
				ArmyFactory af = t.getArmyFactory();
				String side = String.valueOf(t.getSide());
				System.out.println("Team P0 ArmyFactory = " + af.toString());
				Army a = af.getArmy(_canvas, nb_horseman, nb_infantryman, t, "Player"+side);
				addArmy(a, _canvas);
			}
		}
	}
	
	public AddSoldierButtonAction getAddSoldierButtonAction(JButton but, String type){
		return new AddSoldierButtonAction(but, type);
	}
	
	public ReleaseArmyButtonAction getReleaseArmyButtonAction(ActionListener[] list){
		
		return new ReleaseArmyButtonAction(list);
	}
	
	public void addArmy(Army army, Canvas canvas){
		GameMovableDriverDefaultImpl armyDriver = new GameMovableDriverDefaultImpl();
		MoveStrategyKeyboard keyPlayer = new MoveStrategyKeyboard();
		armyDriver.setStrategy(keyPlayer);
		armyDriver.setmoveBlockerChecker(moveBlockerChecker);
		canvas.addKeyListener(keyPlayer);
		army.setDriver(armyDriver);
		army.setTeam(_teams.iterator().next()); // get(0) parce que la première équipe intégrée est le joueur.
		army.setPosition(army.getTeam().getPosition());
		universe.addGameEntity(army);
	}
	
	
}
