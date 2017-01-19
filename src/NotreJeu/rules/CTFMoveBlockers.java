package NotreJeu.rules;

import NotreJeu.entities.Army;
import NotreJeu.entities.IndestructibleWall;
import gameframework.moves_rules.IllegalMoveException;
import gameframework.moves_rules.MoveBlockerRulesApplierDefaultImpl;


public class CTFMoveBlockers extends MoveBlockerRulesApplierDefaultImpl {

	public void moveBlockerRule(Army a, IndestructibleWall w) throws IllegalMoveException {
		throw new IllegalMoveException();
	}
}
