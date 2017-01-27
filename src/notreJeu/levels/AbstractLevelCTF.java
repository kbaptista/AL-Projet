package notreJeu.levels;

import java.awt.Canvas;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import gameframework.core.Game;
import gameframework.core.GameEntity;
import gameframework.core.GameLevelDefaultImpl;
import gameframework.core.GameMovableDriverDefaultImpl;
import gameframework.moves_rules.MoveBlockerChecker;
import gameframework.moves_rules.MoveStrategy;
import gameframework.moves_rules.MoveStrategyKeyboard;
import gameframework.moves_rules.MoveStrategyRandom;

import notreJeu.Team;
import notreJeu.ArmyFactory;
import notreJeu.entities.Army;
import notreJeu.buttons.Action;
import notreJeu.buttons.ActionRecruitSoldiers;
import notreJeu.buttons.ActionReleaseArmy;
import notreJeu.coreextensions.GameCTFImpl;

public abstract class AbstractLevelCTF extends GameLevelDefaultImpl{
	
	Canvas _canvas;
	public String _background_path = "images/ctf_grass.png";

	protected int _width;
	protected int _height;
	protected int[][] _map;
	
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
	
	public void addArmy(Team t, int nb_riders, int nb_infantryman, MoveStrategy move){
		int cost = t.costRider()*nb_riders + t.costInfantryMan()*nb_riders;
		if (cost<=t.get_gold().getValue()) {
			Army army = t.createArmy(nb_riders, nb_infantryman, moveBlockerChecker, move);
			universe.addGameEntity(army);
		}
		System.out.println("Player"+t.getColor()+" has not enough money to buy his/her army.\n"+
				"Current Gold = "+t.get_gold().getValue()+" ; Army Cost = "+cost);
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
	
	public ActionRecruitSoldiers getActionRecruitSoldiers(JButton button, String type){
		return new ActionRecruitSoldiers(button, this, type, _teams_played.iterator().next());
	}
	
	protected ActionReleaseArmy getActionReleaseArmy(JButton button){
		
		return new ActionReleaseArmy(button, this, _teams_played.iterator().next());
	}
	
	protected ActionMoveToNextAge getActionMoveToNextAge(JButton button){
		
		return new ActionMoveToNextAge(button, this, _teams_played.iterator().next());
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
	
	public void createLevelButtons(){
		final JButton infantryman_button = createButton("infantry");
		final JButton rider_button = createButton("rider");
		final JButton release_button = createButton("release");
		final JButton nextAge_button = createButton("nextAge");
		
		Action infantryman_button_action = getActionRecruitSoldiers(infantryman_button, "Infantry");
		Action rider_button_action = getActionRecruitSoldiers(rider_button, "Rider");
		Action release_button_action = getActionReleaseArmy(release_button);
		Action nextAge_action = getActionMoveToNextAge(nextAge_button);

		infantryman_button.addMouseListener(infantryman_button_action);
		rider_button.addMouseListener(rider_button_action);
		release_button.addMouseListener(release_button_action);
		nextAge_button.addMouseListener(nextAge_action);
		System.out.println("Je suis passé par createLevelButtons");
		((GameCTFImpl)g).addJButton(infantryman_button);
		((GameCTFImpl)g).addJButton(rider_button);
		((GameCTFImpl)g).addJButton(release_button);
		((GameCTFImpl)g).addJButton(nextAge_button);
	}
	
	public Canvas get_canvas() {return _canvas;}
	
	public void cleanUniverse(){
		Iterator<GameEntity> entities = universe.gameEntities();
		while(entities.hasNext()){
			entities.next();
			entities.remove();
		}
	}
}
