package notreJeu.coreextensions;

import gameframework.core.CanvasDefaultImpl;
import gameframework.core.Game;
import gameframework.core.GameLevel;
import gameframework.core.ObservableValue;
import soldier.ages.AgeMiddleFactory;
import soldier.core.AgeAbstractFactory;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Container;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import notreJeu.ArmyFactory;
import notreJeu.levels.CTFLevel1;

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

	// initialized before each level
	protected ObservableValue<Boolean> endOfGame = null;

	private Frame f;

	private CTFLevel1 currentPlayedLevel = null;
	protected int levelNumber;
	protected ArrayList<GameLevel> gameLevels;
	
	protected Label lifeText, scoreText;
	protected Label information;
	protected Label informationValue;
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
		Panel buttonsPanel = createButtonsPanel();

		defaultCanvas = new CanvasDefaultImpl();
		defaultCanvas.setSize(SPRITE_SIZE * NB_COLUMNS, SPRITE_SIZE * NB_ROWS);
		f.add(defaultCanvas);
		f.add(c, BorderLayout.NORTH);
		f.add(buttonsPanel, BorderLayout.NORTH);
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
		lifeValue = new Label(Integer.toString(life[0].getValue()));
		scoreValue = new Label(Integer.toString(score[0].getValue()));
		currentLevelValue = new Label(Integer.toString(levelNumber));
		c.add(lifeText);
		c.add(lifeValue);
		c.add(scoreText);
		c.add(scoreValue);
		c.add(currentLevel);
		c.add(currentLevelValue);
		c.add(information);
		c.add(informationValue);
		return c;
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
	
	private Panel createButtonsPanel(){
		Panel res = new Panel();
		
		final JButton infantryman_button = createButton("infantryman");
		final JButton horseman_button = createButton("horseman");
		final JButton release_button = createButton("release");
		
		AddSoldierButtonAction infantryman_button_action = new AddSoldierButtonAction(infantryman_button, "infantryman");
		AddSoldierButtonAction horseman_button_action = new AddSoldierButtonAction(horseman_button, "horseman");
		ReleaseArmyButtonAction release_button_action = new ReleaseArmyButtonAction();

		infantryman_button.addActionListener(infantryman_button_action);
		horseman_button.addActionListener(horseman_button_action);
		
		release_button_action.addAddSoldierButton(infantryman_button_action);
		release_button_action.addAddSoldierButton(horseman_button_action);
		release_button.addActionListener(release_button_action);
		
		res.add(infantryman_button);
		res.add(horseman_button);
		res.add(release_button);
		
		return res ;
	}

	public Canvas getCanvas() {
		return defaultCanvas;
	}

	public void start() {
		for (int i = 0; i < MAX_NUMBER_OF_PLAYER; ++i) {
			score[i].addObserver(this);
			life[i].addObserver(this);
			life[i].setValue(NUMBER_OF_LIVES);
			score[i].setValue(0);
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
				currentPlayedLevel = (CTFLevel1) level;
				levelNumber++;
				currentLevelValue.setText(Integer.toString(levelNumber));
				currentPlayedLevel.start();
				currentPlayedLevel.join();
			} catch (Exception e) {
			}
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

	public void setLevels(ArrayList<GameLevel> levels) {
		gameLevels = levels;
	}

	public void update(Observable o, Object arg) {
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
					scoreValue
							.setText(Integer
									.toString(((ObservableValue<Integer>) o)
											.getValue()));
				}
			}
		}
	}
}

