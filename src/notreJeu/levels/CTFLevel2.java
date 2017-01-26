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
import notreJeu.coreextensions.GameCTFImpl;
import notreJeu.coreextensions.GameUniverseViewPortCTFImpl;
import notreJeu.entities.Barrack;
import notreJeu.entities.Flag;
import notreJeu.entities.IAEntityUnitsVariable;
import notreJeu.entities.IndestructibleWall;
import notreJeu.entities.Water;
import notreJeu.rules.CTFMoveBlockers;
import notreJeu.rules.CTFOverlapRules;
import notreJeu.rules.CreationFlagRuleHorizontalAxisImpl;
import notreJeu.rules.CreationFlagRules;

public class CTFLevel2 extends AbstractLevelCTF{

	protected int[][] generateMap(){
		int[][] map = new int[_width][_height];
		// -- empty spaces 
		for (int i = 1; i < _height-1; i++) {
			for (int j = 1; j < _width-1; j++) {
				if((j < (_width/2)- 2 || j > (_width/2)+ 2) && i == _height/2)
					map[j][i]=2;
				else
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
		map[4][_height/3] = 3;
		map[_width-5][_height-(_height/3)] = 3;
		
		//blocker for IA
		map[_width-5][_height-(_height/3)+3] = 1;
		return map;
	}

	@Override
	protected void init() {
		_map = generateMap();
		CreationFlagRules cfr = new CreationFlagRuleHorizontalAxisImpl(_height/2);
		
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
					universe.addGameEntity(new IndestructibleWall(_canvas, j * SPRITE_SIZE, i * SPRITE_SIZE));;
					break;
				case 2:
					universe.addGameEntity(new Water(_canvas, new Point(j * SPRITE_SIZE, i * SPRITE_SIZE)));
					break;
				case 3:
					//ArmyFactory initiate the first age factory
					Queue<GetAgeFactory> getAgeFactory = new LinkedList<GetAgeFactory>();
					getAgeFactory.add(()->{return new AgeMiddleFactory();});
					getAgeFactory.add(()->{return new AgeFutureFactory();});
					ArmyFactory armyFactory=null;
					try {
						armyFactory = new ArmyFactory(getAgeFactory);
					} catch (Exception e) {e.printStackTrace();}
					
					//position format de la matrice
					Point p = new Point(i,j);
					Point flag_pos = cfr.getFlagPosition(p, new Point((_width)/2,(_height)/2));
					
					//position au format de la carte
					p.setLocation(p.x* SPRITE_SIZE, p.y* SPRITE_SIZE);
					flag_pos.setLocation(flag_pos.x* SPRITE_SIZE, flag_pos.y* SPRITE_SIZE);
					
					int team_id = _teams_played.size()+_teams_ia.size();
					Team t = new Team(team_id, p, equips[team_id], armyFactory, cfr);
					universe.addGameEntity(new Barrack(_canvas, p));
					universe.addGameEntity(new Flag(_canvas, flag_pos, t));
					if(team_id == 0)
						_teams_played.add(t);
					else{
						_teams_ia.add(t);
						universe.addGameEntity(new IAEntityUnitsVariable(this, t, new MoveStrategyRandom(),5,5,2));
					}
					break;
				case 0:
				default:
					break;
				}
			} 
		}
		createLevelButtons();
	}
	
	private JButton createButton(String name){
		JButton res = new JButton();
		try {
			Image img = ImageIO.read(new FileInputStream("images/"+name+"_icon.png"));
			res.setIcon(new ImageIcon(img));
		} catch (Exception ex) {System.out.println(ex);}
		res.setVerticalTextPosition(SwingConstants.BOTTOM);
		res.setHorizontalTextPosition(SwingConstants.RIGHT);
		
		return res;
	}
	
	private void createLevelButtons(){
		final JButton infantryman_button = createButton("infantryman");
		final JButton horseman_button = createButton("horseman");
		final JButton release_button = createButton("release");
		
		ActionListener infantryman_button_action = getAddSoldierButtonAction(infantryman_button, "infantryman");
		ActionListener horseman_button_action = getAddSoldierButtonAction(horseman_button, "horseman");
		ActionListener[] tmp = {infantryman_button_action,horseman_button_action};
		ActionListener release_button_action = getReleaseArmyButtonAction(tmp);

		infantryman_button.addActionListener(infantryman_button_action);
		horseman_button.addActionListener(horseman_button_action);
		release_button.addActionListener(release_button_action);
		
		((GameCTFImpl)g).addJButton(infantryman_button);
		((GameCTFImpl)g).addJButton(horseman_button);
		((GameCTFImpl)g).addJButton(release_button);
	}
	
	public CTFLevel2(Game g, int height, int width) {
		super(g);
		_width = width;
		_height = height;
		_canvas = g.getCanvas();
	}
}
