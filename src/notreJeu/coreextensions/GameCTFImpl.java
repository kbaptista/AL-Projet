package notreJeu.coreextensions;

import gameframework.core.CanvasDefaultImpl;
import gameframework.core.Game;
import gameframework.core.GameLevel;
import gameframework.core.ObservableValue;


import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Container;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import notreJeu.levels.AbstractLevelCTF;

/**
 * Create a basic game application with menus and displays of lives and score
 */
public class GameCTFImpl implements Game, Observer {
	protected int NB_ROWS = 31;
	protected int NB_COLUMNS = 28;
	protected static final int SPRITE_SIZE = 32;
	public static final int MAX_NUMBER_OF_PLAYER = 4;
	public static final int NUMBER_OF_LIVES = 1;

	protected CanvasDefaultImpl defaultCanvas = null;
	protected ObservableValue<Integer> score[] = new ObservableValue[MAX_NUMBER_OF_PLAYER];
	protected ObservableValue<Integer> life[] = new ObservableValue[MAX_NUMBER_OF_PLAYER];

	protected ObservableValue<Integer> gold1 ;
	protected ObservableValue<Integer> gold2 ;
	
	// initialized before each level
	protected ObservableValue<Boolean> endOfGame = null;

	private Frame f;
	private Panel buttonsPanel;
	
	protected int levelNumber;
	protected ArrayList<AbstractLevelCTF> gameLevels;
	private AbstractLevelCTF currentPlayedLevel = null;

	protected Label lifeText, scoreText;
	protected Label team1Gold, team2Gold;
	protected Label information;
	protected Label informationValue;
	protected Label team1GoldValue, team2GoldValue;
	protected Label lifeValue, scoreValue;
	protected Label currentLevel;
	protected Label currentLevelValue;

	
	public GameCTFImpl(int nb_columns, int nb_rows) {
		NB_COLUMNS = nb_columns;
		NB_ROWS = nb_rows;
		
		for (int i = 0; i < MAX_NUMBER_OF_PLAYER; ++i) {
			score[i] = new ObservableValue<Integer>(0);
			life[i] = new ObservableValue<Integer>(0);
		}
		
		gold1 = new ObservableValue<Integer>(0);
		gold2 = new ObservableValue<Integer>(0);
		
		team1Gold = new Label("Gold Team 1 : ");
		team2Gold = new Label("Gold Team 2 : ");

		lifeText = new Label("Lives:");
		scoreText = new Label("Score:");
		information = new Label("State:");
		informationValue = new Label("Playing");
		currentLevel = new Label("Level:");
		createGUI();
	}

	public void createGUI() {
		f = new Frame("CatchTheFlag Game");
		f.dispose();

		createMenuBar();
		Container c = createStatusBar();
		buttonsPanel = new Panel();
		Panel accessor = new Panel(new BorderLayout());
		accessor.add(c, BorderLayout.NORTH);
		accessor.add(buttonsPanel, BorderLayout.SOUTH);
		
		defaultCanvas = new CanvasDefaultImpl();
		defaultCanvas.setSize(SPRITE_SIZE * NB_COLUMNS, SPRITE_SIZE * NB_ROWS);
		f.add(defaultCanvas);
		f.add(accessor,BorderLayout.NORTH);
		f.pack();
		f.setVisible(true);

		f.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	
	private void createMenuBar() {
		MenuBar menuBar = new MenuBar();
		Menu file = new Menu("file");
		MenuItem start = new MenuItem("new game");
		MenuItem save = new MenuItem("save");
		MenuItem restore = new MenuItem("load");
		MenuItem quit = new MenuItem("quit");
		Menu game = new Menu("game");
		MenuItem pause = new MenuItem("pause");
		MenuItem resume = new MenuItem("resume");
		menuBar.add(file);
		menuBar.add(game);
		f.setMenuBar(menuBar);

		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start();
			}
		});
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		restore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				restore();
			}
		});
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		pause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pause();
			}
		});
		resume.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resume();
			}
		});

		file.add(start);
		file.add(save);
		file.add(restore);
		file.add(quit);
		game.add(pause);
		game.add(resume);
	}

	private Container createStatusBar() {
		JPanel c = new JPanel();
		GridBagLayout layout = new GridBagLayout();
		c.setLayout(layout);
		team1GoldValue = new Label(Integer.toString(life[0].getValue()));
		team2GoldValue = new Label(Integer.toString(score[0].getValue()));
		lifeValue = new Label(Integer.toString(life[0].getValue()));
		scoreValue = new Label(Integer.toString(score[0].getValue()));
		currentLevelValue = new Label(Integer.toString(levelNumber));
		c.add(lifeText);
		c.add(lifeValue);
		c.add(scoreText);
		c.add(scoreValue);
		c.add(team1Gold);
		c.add(team1GoldValue);
		c.add(team2Gold);
		c.add(team2GoldValue);
		c.add(currentLevel);
		c.add(currentLevelValue);
		c.add(information);
		c.add(informationValue);
		return c;
	}
	
	public void addJButton(JButton butt){
		buttonsPanel.add(butt);
		SwingUtilities.updateComponentTreeUI(f);
	}
	
	public void cleanLevel(){
		buttonsPanel.removeAll();
		currentPlayedLevel.cleanUniverse();
	}
	
	public Canvas getCanvas() {
		return defaultCanvas;
	}

	public void start() {
		for (int i = 0; i < MAX_NUMBER_OF_PLAYER; ++i) {
			score[i].addObserver(this);
			life[i].addObserver(this);
			score[i].setValue(0);
			life[i].setValue(0);
		}
		
		levelNumber = 0;
		for (GameLevel level : gameLevels) {
			endOfGame = new ObservableValue<Boolean>(false);
			endOfGame.addObserver(this);
			try {
				if (currentPlayedLevel != null && currentPlayedLevel.isAlive()) {
					currentPlayedLevel.interrupt();
					currentPlayedLevel = null;
				}
				currentPlayedLevel = (AbstractLevelCTF) level;
				levelNumber++;
				currentLevelValue.setText(Integer.toString(levelNumber));
				currentPlayedLevel.start();
				currentPlayedLevel.join();
			} catch (Exception e) {}
			//cleanLevel();
		}
	}

	public void restore() {
		System.out.println("restore(): Unimplemented operation");
	}

	public void save() {
		System.out.println("save(): Unimplemented operation");
	}

	public void pause() {
		System.out.println("pause(): Unimplemented operation");
		// currentPlayedLevel.suspend();
	}

	public void resume() {
		System.out.println("resume(): Unimplemented operation");
		// currentPlayedLevel.resume();
	}

	public ObservableValue<Integer>[] score() {
		return score;
	}

	public ObservableValue<Integer>[] life() {
		return life;
	}

	public ObservableValue<Boolean> endOfGame() {
		return endOfGame;
	}

	public void setLevels(ArrayList<AbstractLevelCTF> levels) {
		System.out.println(levels.toString());
		gameLevels = levels;
	}

	public void setGold1(ObservableValue<Integer> gold1) {
		this.gold1 = gold1;
	}
	
	public void setGold2(ObservableValue<Integer> gold2) {
		this.gold2 = gold2;
	}
	
	public void update(Observable o, Object arg) {

		System.out.println("lol");
		if (o == endOfGame) {
			if (endOfGame.getValue()) {
				informationValue.setText("You win");
				currentPlayedLevel.interrupt();
				currentPlayedLevel.end();
			}
		} else {
			
			for (ObservableValue<Integer> lifeObservable : life) {
				if (o == lifeObservable) {
					int lives = ((ObservableValue<Integer>) o).getValue();
					lifeValue.setText(Integer.toString(lives));
					if (lives == 0) {
						informationValue.setText("Defeat");
						currentPlayedLevel.interrupt();
						currentPlayedLevel.end();
					}
				}
			}
			for (ObservableValue<Integer> scoreObservable : score) {
				if (o == scoreObservable) {
					scoreValue.setText(Integer.toString(((ObservableValue<Integer>) o).getValue()));
				}
			}
			team1GoldValue.setText(Integer.toString(gold1.getValue()));
			team2GoldValue.setText(Integer.toString(gold2.getValue()));
		}
	}
}

