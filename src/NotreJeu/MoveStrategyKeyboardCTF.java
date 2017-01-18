package NotreJeu;

import gameframework.moves_rules.MoveStrategyKeyboard;

import java.awt.Point;
import java.awt.event.KeyEvent;

import NotreJeu.entities.Barrack;

/**
 * {@link MoveStrategy} which listens to the keyboard and answers new
 * {@link SpeedVector speed vectors} based on what the user typed.
 */
public class MoveStrategyKeyboardCTF extends MoveStrategyKeyboard {

	public Barrack _barrack;
	
	public MoveStrategyKeyboardCTF(Barrack barrack) {
		_barrack = barrack;
	}
	
	@Override
	public void keyPressed(KeyEvent event) {
		int keycode = event.getKeyCode();
		switch (keycode) {
		case KeyEvent.VK_RIGHT:
			speedVector.setDirection(new Point(1, 0));
			break;
		case KeyEvent.VK_LEFT:
			speedVector.setDirection(new Point(-1, 0));
			break;
		case KeyEvent.VK_UP:
			speedVector.setDirection(new Point(0, -1));
			break;
		case KeyEvent.VK_DOWN:
			speedVector.setDirection(new Point(0, 1));
			break;
			//TODO: chercher comment receptionner cet event sans passer par MoveStrategy
		case KeyEvent.VK_F1: //créer infantry
			//_barrack.createArmy(1);
			break;			
		case KeyEvent.VK_F2: //créer cheval	
			//_barrack.createArmy(2);
			break;
		}
	}
}
