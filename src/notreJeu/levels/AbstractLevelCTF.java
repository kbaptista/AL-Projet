package notreJeu.levels;

import java.awt.Canvas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;

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

	private int[][] generateMap(){
		int[][] map = new int[_width][_height];
		// -- empty spaces 
		for (int i = 1; i < _height-1; i++) {
			for (int j = 1; j < _width-1; j++) {
				map[j][i]=0; 
			}
		}
		// -- frame
		for (int i = 0; i < _height; i++) {
			map[0][i]=1;
			map[_width-1][i]=1;
		}
		for (int j = 0; j < _width; j++) {
			map[j][0]=1;
			map[j][_height-1]=1;
		}
		// -- flags
		//map[0+2][_height/2] = 2;
		//map[_width-3][_height/2] = 2;
		
		// -- buildings
		map[0+4][_height/2] = 2;
		map[_width-5][_height/2] = 2;
		
		return map;
	}
	
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
		
		private Set<AddSoldierButtonAction> instanciateButtons;

		private CTFLevel1 currentPlayedLevel = null;
		
		public ReleaseArmyButtonAction() {
			instanciateButtons = new HashSet<AddSoldierButtonAction>();
		}
		
		public void addAddSoldierButton(AddSoldierButtonAction but){instanciateButtons.add(but);}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(true){//TODO : vérifier si le joueur possède une équipe

				int nb_infantryman = 0 ;
				int nb_horseman = 0 ;
				for(AddSoldierButtonAction but : instanciateButtons){
					if(but.getType().matches("horseman")){nb_horseman = but.getValue();}
					if(but.getType().matches("infantryman")){nb_infantryman = but.getValue();}
					but.setValue(0);
				}
				Team t = _teams.iterator().next();
				ArmyFactory a =t.getArmyFactory();
				this.addArmy(a.getArmy(_canvas, nb_horseman, nb_infantryman, t, "Player"+t.getSide()), _canvas);
			}
		}
	}
	public void addArmy(Army army, Canvas canvas){
		GameMovableDriverDefaultImpl armyDriver = new GameMovableDriverDefaultImpl();
		MoveStrategyKeyboard keyPlayer = new MoveStrategyKeyboard();
		armyDriver.setStrategy(keyPlayer);
		armyDriver.setmoveBlockerChecker(moveBlockerChecker);
		canvas.addKeyListener(keyPlayer);
		army.setDriver(armyDriver);
		army.setTeam(_teams.iterator().next()); // get(0) parce que la première équipe intégrée est le joueur.
		army.setPosition(army.getSide().getPosition());
		universe.addGameEntity(army);
	}
	
	
}
