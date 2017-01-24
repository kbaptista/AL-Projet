package notreJeu.rules;

import gameframework.core.GameEntity;
import gameframework.core.GameUniverse;
import gameframework.core.ObservableValue;
import gameframework.moves_rules.Overlap;
import gameframework.moves_rules.OverlapRulesApplierDefaultImpl;

import java.util.Iterator;
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

	private GameEntity getArmyFromUniverse(Army a){
		Iterator<GameEntity> it = universe.gameEntities();
		GameEntity ge;
		while(it.hasNext()){
			ge = it.next();
			if(ge instanceof Army){
				if(((Army)ge).equals(a))
					return (Army)ge;
			}	
		}
		return null;
	}
	
	public void overlapRule(Army a, Army a2) {
		if(a.getTeam().getSide() != a2.getTeam().getSide()){
			Unit u1 = a.getSoldiers();
			Unit u2 = a2.getSoldiers();
			
			u2.parry(u1.strike());
			u1.parry(u2.strike());
			
			System.out.println("u1 :"+u1.alive()+" u2 "+u2.alive());//TMP
			
			if(!u1.alive())
				universe.removeGameEntity(getArmyFromUniverse(a));
			if(!u2.alive())
				universe.removeGameEntity(getArmyFromUniverse(a2));
			
			//TODO :disparition des entities si nécessaires
		}
		
			
	}

	public void overlapRule(Army a, Flag f) {
		//si c'est une armée d'un camps qui rencontre le drapeau d'un autre camps
		if(f.getTeam().getSide() != a.getTeam().getSide()){
			if ((!f.isCatched()) && !a.haveAFlag()){
				System.out.println("CATCHED");
				a.captureTheFlag();
			}
		}
		//si une armée arrive à son propre drapeau
		else{
			if(a.haveAFlag()){
				endOfGame.setValue(true);
			}
		}
	}

	public void overlapRule(Army a, Barrack b) {
		//pour le moment, rien
	}
}
