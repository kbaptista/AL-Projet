package notreJeu.levels;

import gameframework.core.CanvasDefaultImpl;
import gameframework.core.Game;
import gameframework.core.GameUniverseDefaultImpl;
import gameframework.moves_rules.MoveBlockerCheckerDefaultImpl;
import gameframework.moves_rules.MoveStrategyRandom;
import gameframework.moves_rules.OverlapProcessor;
import gameframework.moves_rules.OverlapProcessorDefaultImpl;

import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.util.LinkedList;
import java.util.Queue;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import soldier.ages.AgeFutureFactory;
import soldier.ages.AgeMiddleFactory;
import notreJeu.ArmyFactory;
import notreJeu.Equip;
import notreJeu.GetAgeFactory;
import notreJeu.Team;
import notreJeu.buttons.Action;
import notreJeu.coreextensions.GameCTFImpl;
import notreJeu.coreextensions.GameUniverseViewPortCTFImpl;
import notreJeu.entities.Barrack;
import notreJeu.entities.Flag;
import notreJeu.entities.IAEntitySimple;
import notreJeu.entities.IndestructibleWall;
import notreJeu.rules.CTFMoveBlockers;
import notreJeu.rules.CTFOverlapRules;
import notreJeu.rules.CreationFlagRuleCenterImpl;
import notreJeu.rules.CreationFlagRules;

public class CTFLevel1 extends AbstractLevelCTF{
	
	protected int[][] generateMap(){
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
		
		// -- buildings
		map[0+4][_height/2] = 3;
		map[_width-5][_height/2] = 3;

		StringBuffer res = new StringBuffer();
		for (int i = 0; i < _height; i++) {
			for(int j=0 ; i < _width ; j++)
				res.append(map[i][j]);

			res.append("\n");
		}
		System.out.println(res.toString());
		return map;
	}
	
	@Override
	protected void init() {
		_map = generateMap();
		CreationFlagRules cfr = new CreationFlagRuleCenterImpl(new Point((_width)/2,(_height)/2));
		
		OverlapProcessor overlapProcessor = new OverlapProcessorDefaultImpl();

		moveBlockerChecker = new MoveBlockerCheckerDefaultImpl();
		moveBlockerChecker.setMoveBlockerRules(new CTFMoveBlockers());

		CTFOverlapRules overlapRules = new CTFOverlapRules(life[0], score[0], endOfGame);
		overlapProcessor.setOverlapRules(overlapRules);

		universe = new GameUniverseDefaultImpl(moveBlockerChecker, overlapProcessor);
		overlapRules.setUniverse(universe);
		overlapRules.setCanvas(_canvas);

		gameBoard = new GameUniverseViewPortCTFImpl(_canvas, universe, SPRITE_SIZE, _width);
		((GameUniverseViewPortCTFImpl)gameBoard).setBackground(_background_path, SPRITE_SIZE, _width);

		((CanvasDefaultImpl) _canvas).setDrawingGameBoard(gameBoard);
		
		Equip[] equips = Equip.values();
		// Filling up the universe with basic non movable entities and inclusion in the universe
		for (int i = 0; i < _height; ++i) {
			for (int j = 0; j < _width; ++j) {
				switch(_map[i][j]){
				case 1:
					universe.addGameEntity(new IndestructibleWall(_canvas, i * SPRITE_SIZE, j * SPRITE_SIZE));;
					break;
				case 3:

					System.out.println("J'ai vu une barrack");
					//ArmyFactory initiate the first age factory
					Queue<GetAgeFactory> getAgeFactory = new LinkedList<GetAgeFactory>();
					getAgeFactory.add(()->{return new AgeMiddleFactory();});
					getAgeFactory.add(()->{return new AgeFutureFactory();});
					ArmyFactory armyFactory=null;
					try{
						armyFactory = new ArmyFactory(getAgeFactory, _canvas);
					}
					catch(Exception e){e.printStackTrace();}
					
					Point p = new Point(i,j);
					Point flag_pos = cfr.getFlagPosition(p);
			
					p.setLocation(p.x* SPRITE_SIZE, p.y* SPRITE_SIZE);
					flag_pos.setLocation(flag_pos.x* SPRITE_SIZE, flag_pos.y* SPRITE_SIZE);
					
					int team_id = _teams_played.size()+_teams_ia.size();
					Team t = new Team(team_id, p, equips[team_id], armyFactory, cfr);
					universe.addGameEntity(new Barrack(_canvas, p));
					universe.addGameEntity(new Flag(_canvas, flag_pos, t));
					if(team_id == 0){
						_teams_played.add(t);
						System.out.println(team_id);
					}
					else{
						_teams_ia.add(t);
						//Les IAs ont une instance Entity pour avoir droit a des effets temporels.
						universe.addGameEntity(new IAEntitySimple(this, t, new MoveStrategyRandom(),2,2));
					}
					break;
				case 0:
				default:
					break;
				}
			} 
		}
		((GameCTFImpl)g).setGold1(_teams_played.iterator().next().get_gold());
		((GameCTFImpl)g).setGold2(_teams_played.iterator().next().get_gold());
		System.out.println("Je suis passé devant createLevelButtons");
		createLevelButtons();
	}
		
	public CTFLevel1(Game g, int height, int width) {
		super(g);
		_width = width;
		_height = height;
		_canvas = g.getCanvas();
	}
}