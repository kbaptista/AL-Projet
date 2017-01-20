package notreJeu.levels;

import gameframework.core.CanvasDefaultImpl;
import gameframework.core.Game;
import gameframework.core.GameUniverseDefaultImpl;
import gameframework.moves_rules.MoveBlockerChecker;
import gameframework.moves_rules.MoveBlockerCheckerDefaultImpl;
import gameframework.moves_rules.OverlapProcessor;
import gameframework.moves_rules.OverlapProcessorDefaultImpl;

import java.awt.Canvas;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import soldier.ages.AgeMiddleFactory;
import soldier.core.AgeAbstractFactory;
import notreJeu.ArmyFactory;
import notreJeu.Equip;
import notreJeu.Team;
import notreJeu.coreextensions.GameUniverseViewPortCTFImpl;
import notreJeu.entities.Barrack;
import notreJeu.entities.Flag;
import notreJeu.entities.IndestructibleWall;
import notreJeu.rules.CTFMoveBlockers;
import notreJeu.rules.CTFOverlapRules;
import notreJeu.rules.CreationFlagRuleHorizontalAxisImpl;
import notreJeu.rules.CreationFlagRules;

public class CTFLevel1 extends AbstractLevelCTF{

	@Override
	protected void init() {

		ArmyFactory armyFactory = new ArmyFactory();
		AgeAbstractFactory ageFactory = new AgeMiddleFactory(); 
		CreationFlagRules cfr = new CreationFlagRuleHorizontalAxisImpl();
		
		OverlapProcessor overlapProcessor = new OverlapProcessorDefaultImpl();

		moveBlockerChecker = new MoveBlockerCheckerDefaultImpl();
		moveBlockerChecker.setMoveBlockerRules(new CTFMoveBlockers());

		CTFOverlapRules overlapRules = new CTFOverlapRules(life[0], score[0], endOfGame);
		overlapProcessor.setOverlapRules(overlapRules);

		universe = new GameUniverseDefaultImpl(moveBlockerChecker, overlapProcessor);
		overlapRules.setUniverse(universe);

		gameBoard = new GameUniverseViewPortCTFImpl(_canvas, universe, SPRITE_SIZE, _width);
		((GameUniverseViewPortCTFImpl)gameBoard).setBackground(_background_path, SPRITE_SIZE, _width);

		((CanvasDefaultImpl) _canvas).setDrawingGameBoard(gameBoard);

		_teams = new HashSet<Team>();
		
		
		Equip[] equips = Equip.values();
		// Filling up the universe with basic non movable entities and inclusion in the universe
		for (int i = 0; i < _height; ++i) {
			for (int j = 0; j < _width; ++j) {
				switch(_map[i][j]){
				case 1:
					universe.addGameEntity(new IndestructibleWall(_canvas, j * SPRITE_SIZE, i * SPRITE_SIZE));;
					break;
				case 3:
					Team t =new Team(_teams.size(),new Point(i,j), equips[_teams.size()], armyFactory,cfr);
					Point p = new Point(j * SPRITE_SIZE, i * SPRITE_SIZE);
					universe.addGameEntity(new Barrack(_canvas, p.x, p.y));
					universe.addGameEntity(new Flag(_canvas, cfr.getFlagPosition(p, new Point(_width*SPRITE_SIZE/2,SPRITE_SIZE*_height/2)), t));
					_teams.add(t);
					break;
				case 0:
				default:
					break;
				}
			} 
		}
	}
	
	
	
	public CTFLevel1(Game g, int size) {
		super(g);
		_width = size;
		_height = size;
		_canvas = g.getCanvas();
	}
}