package notreJeu.buttons;

import java.awt.Canvas;
import java.awt.event.MouseListener;

import javax.swing.JButton;

import notreJeu.Team;

public abstract class Action implements MouseListener{

	protected JButton button;
	protected Canvas canvas;
	
	/*
	 * Static variables :
	 * useful since the action that release the army
	 * need to know how many soldiers to instanciate
	 * and that this number is decided by another action
	 */
	
	protected static int nb_infantryman = 0 ;
	protected static int nb_riders = 0 ;
	protected static int army_cost = 0;
	
	protected static Team side ;
	
}
