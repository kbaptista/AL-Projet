package NotreJeu;

import gameframework.core.CanvasDefaultImpl;
import gameframework.core.Game;
import gameframework.core.GameLevelDefaultImpl;
import gameframework.core.GameUniverseDefaultImpl;
import gameframework.moves_rules.MoveBlockerChecker;
import gameframework.moves_rules.MoveBlockerCheckerDefaultImpl;
import gameframework.moves_rules.OverlapProcessor;
import gameframework.moves_rules.OverlapProcessorDefaultImpl;

import java.awt.Canvas;
import java.awt.Point;

import NotreJeu.entities.Barrack;
import NotreJeu.entities.Flag;
import NotreJeu.entities.IndestructibleWall;
import NotreJeu.rules.CTFOverlapRules;
import pacman.rule.PacmanMoveBlockers;

public class FirstStep extends GameLevelDefaultImpl{

	public enum Equip{
		YELLOW(0), GREEN(1), BLUE(2), VIOLET(3), RED(4), ORANGE(5);
		private int value;

		private Equip(int value){
			this.value = value;
		}

		public int getValue(){
			return this.value;
		}

		public String getPicture(){
			switch(this){
			case YELLOW:
				return "images/ctf_flag_yellow.png"; 
			case GREEN:
				return "images/ctf_flag_green.png"; 
			case BLUE:
				return "images/ctf_flag_blue.png"; 
			case VIOLET:
				return "images/ctf_flag_violet.png";
			case RED:
				return "images/ctf_flag_red.png"; 
			case ORANGE:
				return "images/ctf_flag_orange.png"; 
			default:
				return "images/ctf_flag_white.png";
			}
		} 

	};
/* UNUSED
	public enum Element{
		NOTHING(0), WALL(1), FLAG(2), BUILDING(3), ARMY(4);
		private int value;

		private Element(int value){
			this.value = value;
		}

		public int getValue(){
			return this.value;
		}
	}
*/
	Canvas canvas;

	//our specific Environment
	public String background = "images/ctf_grass.png";
	int width = 31;
	int height = 31;
	int[][] tab = generate_tab(); 
	/*{ 
		{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
		{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
		{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
		{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
		{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
		{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
		{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
		{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
		{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
		{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },//
		{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
		{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
		{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
		{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
		{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
		{ 1, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 1 },//////
		{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
		{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
		{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
		{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
		{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
		{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },//
		{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
		{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
		{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
		{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
		{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
		{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
		{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
		{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
		{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } };*/

	public static final int SPRITE_SIZE = 32;

	private int[][] generate_tab(){
		int[][] tab = new int[width][height];
		// -- empty spaces 
		for (int i = 1; i < height-1; i++) {
			for (int j = 1; j < width-1; j++) {
				tab[j][i]=0; 
			}
		}
		// -- frame
		for (int i = 0; i < height; i++) {
			tab[0][i]=1;
			tab[width-1][i]=1;
		}
		for (int j = 0; j < width; j++) {
			tab[j][0]=1;
			tab[j][height-1]=1;
		}
		// -- flags
		tab[0+2][height/2] = 2;
		tab[width-3][height/2] = 2;
		
		// -- buildings
		tab[0+4][height/2] = 3;
		tab[width-5][height/2] = 3;
		
		/*
		for (int i = 0; i < tab.length; i++) {
			for (int j = 0; j < tab.length; j++) {
				System.out.print(tab[i][j]+" ");
			}
			System.out.println();
		}*/
		return tab;
	}

	@Override
	protected void init() {

		OverlapProcessor overlapProcessor = new OverlapProcessorDefaultImpl();

		//unused now
		MoveBlockerChecker moveBlockerChecker = new MoveBlockerCheckerDefaultImpl();
		moveBlockerChecker.setMoveBlockerRules(new PacmanMoveBlockers());

		CTFOverlapRules overlapRules = new CTFOverlapRules(life[0], score[0], endOfGame);
		overlapProcessor.setOverlapRules(overlapRules);

		universe = new GameUniverseDefaultImpl(moveBlockerChecker, overlapProcessor);
		overlapRules.setUniverse(universe);

		gameBoard = new GameUniverseViewPortCTFImpl(canvas, universe, SPRITE_SIZE, width);
		((GameUniverseViewPortCTFImpl)gameBoard).setBackground(background, SPRITE_SIZE, width);


		((CanvasDefaultImpl) canvas).setDrawingGameBoard(gameBoard);

		// Filling up the universe with basic non movable entities and inclusion in the universe
		for (int i = 0; i < height; ++i) {
			for (int j = 0; j < width; ++j) {
				switch(tab[i][j]){
				case 1:
					universe.addGameEntity(new IndestructibleWall(canvas, j * SPRITE_SIZE, i * SPRITE_SIZE));;
					break;
				case 2:
					boolean side = j < (width/2);
					universe.addGameEntity(new Flag(canvas, new Point(j * SPRITE_SIZE, i * SPRITE_SIZE), side?Equip.RED:Equip.BLUE));
					break;
				case 3:
					universe.addGameEntity(new Barrack(canvas, j * SPRITE_SIZE, i * SPRITE_SIZE));
					break;
				case 4:
					//universe.addGameEntity(new Army());// TODO
					break;
				case 0:
				default:
					break;
				}
			} 
		}
	}

	public FirstStep(Game g, int size) {
		super(g);
		width = size;
		height = size;
		canvas = g.getCanvas();
	}
}