package notreJeu.buttons;

import java.awt.event.MouseListener;

import javax.swing.JButton;

import notreJeu.Team;
import notreJeu.levels.AbstractLevelCTF;

public abstract class Action implements MouseListener{


	protected JButton button;
	
	/*
	 * Static variables :
	 * useful since the action that release the army
	 * need to know how many soldiers to instanciate
	 * and that this number is decided by another action
	 */
	
	protected static Integer nb_infantryman = 0 ;
	protected static Integer nb_riders = 0 ;
	protected static Integer army_cost = 0;
	
	protected static Team side ;
	protected static AbstractLevelCTF level;

	public Action(JButton button, AbstractLevelCTF level, Team team) {
		// TODO Auto-generated constructor stub
		this.button = button;
		this.level = level;
		this.side = team;
	}
}
