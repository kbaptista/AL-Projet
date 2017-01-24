package notreJeu.rules;

import gameframework.core.GameUniverse;
import gameframework.core.ObservableValue;
import gameframework.moves_rules.Overlap;
import gameframework.moves_rules.OverlapRulesApplierDefaultImpl;

import java.util.Vector;

import notreJeu.entities.Army;
import notreJeu.entities.Barrack;
import notreJeu.entities.Flag;
import soldier.core.Unit;

public class CTFOverlapRules extends OverlapRulesApplierDefaultImpl {
	protected GameUniverse universe;

	private final ObservableValue<Integer> score;
	private final ObservableValue<Integer> life;
	private final ObservableValue<Boolean> endOfGame;


	public CTFOverlapRules(ObservableValue<Integer> life, ObservableValue<Integer> score,
			ObservableValue<Boolean> endOfGame) {
		this.life = life;
		this.score = score;
		this.endOfGame = endOfGame;
	}

	public void setUniverse(GameUniverse universe) {
		this.universe = universe;
	}

	@Override
	public void applyOverlapRules(Vector<Overlap> overlappables) {
		super.applyOverlapRules(overlappables);
	}

	public void overlapRule(Army a, Army a2) {
		Unit u1 = a.getSoldiers();
		Unit u2 = a2.getSoldiers();
		
		u2.parry(u1.strike());
		u1.parry(u2.strike());
	}

	public void overlapRule(Army a, Flag f) {
		//si c'est une armée d'un camps qui rencontre le drapeau d'un autre camps
		if(!(f.getTeam().getSide() == a.getTeam().getSide())){
			if ((!f.alreadyCatched()) && !a.haveAFlag())
				a.captureTheFlag();
		}
		//si une armée arrive à son propre drapeau
		else{
			if(a.haveAFlag())
				endOfGame.setValue(true);
		}
	}

	public void overlapRule(Army a, Barrack b) {
		//pour le moment, rien
	}
}
