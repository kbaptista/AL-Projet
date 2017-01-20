package notreJeu.levels;

import gameframework.core.CanvasDefaultImpl;
import gameframework.core.Game;
import gameframework.core.GameMovableDriverDefaultImpl;
import gameframework.core.GameUniverseDefaultImpl;
import gameframework.moves_rules.MoveBlockerChecker;
import gameframework.moves_rules.MoveBlockerCheckerDefaultImpl;
import gameframework.moves_rules.MoveStrategyKeyboard;
import gameframework.moves_rules.OverlapProcessor;
import gameframework.moves_rules.OverlapProcessorDefaultImpl;

import java.awt.Canvas;
import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import soldier.ages.AgeFutureFactory;
import soldier.ages.AgeMiddleFactory;
import notreJeu.AbstractLevelCTF;
import notreJeu.ArmyFactory;
import notreJeu.Equip;
import notreJeu.GetAgeFactory;
import notreJeu.Team;
import notreJeu.coreextensions.GameUniverseViewPortCTFImpl;
import notreJeu.entities.Army;
import notreJeu.entities.Barrack;
import notreJeu.entities.Flag;
import notreJeu.entities.IndestructibleWall;
import notreJeu.rules.CTFMoveBlockers;
import notreJeu.rules.CTFOverlapRules;
import notreJeu.rules.CreationFlagRuleHorizontalAxisImpl;
import notreJeu.rules.CreationFlagRules;

public class CTFLevel1 extends AbstractLevelCTF{

	Canvas _canvas;

	//our specific Environment
	public String _background = "images/ctf_grass.png";
	public int _width = 31;
	public int _height = 31;
	public int[][] _tab = generate_tab(); 
	public MoveBlockerChecker moveBlockerChecker;
	
	public List<Team> _teams;
	
	public static final int SPRITE_SIZE = 32;

	private int[][] generate_tab(){
		int[][] tab = new int[_width][_height];
		// -- empty spaces 
		for (int i = 1; i < _height-1; i++) {
			for (int j = 1; j < _width-1; j++) {
				tab[j][i]=0; 
			}
		}
		// -- frame
		for (int i = 0; i < _height; i++) {
			tab[0][i]=1;
			tab[_width-1][i]=1;
		}
		for (int j = 0; j < _width; j++) {
			tab[j][0]=1;
			tab[j][_height-1]=1;
		}
		// -- flags
		//tab[0+2][_height/2] = 2;
		//tab[_width-3][_height/2] = 2;
		
		// -- buildings
		tab[0+4][_height/2] = 2;
		tab[_width-5][_height/2] = 2;
		
		return tab;
	}

	@Override
	protected void init() {
		CreationFlagRules cfr = new CreationFlagRuleHorizontalAxisImpl();
		
		OverlapProcessor overlapProcessor = new OverlapProcessorDefaultImpl();

		moveBlockerChecker = new MoveBlockerCheckerDefaultImpl();
		moveBlockerChecker.setMoveBlockerRules(new CTFMoveBlockers());

		CTFOverlapRules overlapRules = new CTFOverlapRules(life[0], score[0], endOfGame);
		overlapProcessor.setOverlapRules(overlapRules);

		universe = new GameUniverseDefaultImpl(moveBlockerChecker, overlapProcessor);
		overlapRules.setUniverse(universe);

		gameBoard = new GameUniverseViewPortCTFImpl(_canvas, universe, SPRITE_SIZE, _width);
		((GameUniverseViewPortCTFImpl)gameBoard).setBackground(_background, SPRITE_SIZE, _width);

		((CanvasDefaultImpl) _canvas).setDrawingGameBoard(gameBoard);

		_teams = new ArrayList<Team>();
		
		
		Equip[] equips = Equip.values();
		// Filling up the universe with basic non movable entities and inclusion in the universe
		for (int i = 0; i < _height; ++i) {
			for (int j = 0; j < _width; ++j) {
				switch(_tab[i][j]){
				case 1:
					universe.addGameEntity(new IndestructibleWall(_canvas, j * SPRITE_SIZE, i * SPRITE_SIZE));;
					break;
				case 3:
					//ArmyFactory initiate the first age factory
					Queue<GetAgeFactory> getAgeFactory = new LinkedList<GetAgeFactory>();
					getAgeFactory.add(()->{return new AgeMiddleFactory();});
					getAgeFactory.add(()->{return new AgeFutureFactory();});
					ArmyFactory armyFactory = new ArmyFactory(getAgeFactory);
					
					Team t =new Team(_teams.size(),new Point(i,j), equips[_teams.size()], armyFactory,cfr);
					Point p = new Point(j * SPRITE_SIZE, i * SPRITE_SIZE);
					universe.addGameEntity(new Barrack(_canvas, p.x, p.y));
					universe.addGameEntity(new Flag(_canvas, cfr.getFlagPosition(p, new Point(_width/2,_height/2)), t));
					_teams.add(t);
					
					
					break;
				case 0:
				default:
					break;
				}
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
		army.setTeam(_teams.get(0)); // get(0) parce que la première équipe intégrée est le joueur.
		army.setPosition(army.getSide().getPosition());
		universe.addGameEntity(army);
	}
	
	public CTFLevel1(Game g, int size) {
		super(g);
		_width = size;
		_height = size;
		_canvas = g.getCanvas();
	}
}