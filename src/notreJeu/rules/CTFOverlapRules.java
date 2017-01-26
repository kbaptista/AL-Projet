package notreJeu.rules;

import gameframework.core.GameEntity;
import gameframework.core.GameUniverse;
import gameframework.core.ObservableValue;
import gameframework.moves_rules.Overlap;
import gameframework.moves_rules.OverlapRulesApplierDefaultImpl;

import java.awt.Canvas;
import java.awt.Point;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import notreJeu.entities.Army;
import notreJeu.entities.Barrack;
import notreJeu.entities.Flag;
import notreJeu.entities.FlagEmpty;
import soldier.core.Unit;

public class CTFOverlapRules extends OverlapRulesApplierDefaultImpl {
	protected GameUniverse universe;
	protected Canvas canvas;

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
	
	public void setCanvas(Canvas canvas){
		this.canvas = canvas;
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
	
	private void rebuildFlag(Army killed_army, Army victorious_army){
		if(killed_army.haveAFlag()){
			List<Flag> tmp = killed_army.getCapturedFlags();
			for(Flag f : tmp)
				if(f.getTeam().equals(victorious_army.getTeam())){
					Point p = victorious_army.getTeam().getCreationFlagRule().getFlagPosition(victorious_army.getTeam().getPosition());
					universe.addGameEntity(new Flag(canvas,p,victorious_army.getTeam()));
				}	
		}
	}
	
	public void overlapRule(Army a, Army a2) {
		if(a.getTeam().getSide() != a2.getTeam().getSide()){
			Unit u1 = a.getSoldiers();
			Unit u2 = a2.getSoldiers();
			
			u2.parry(u1.strike());
			u1.parry(u2.strike());

			if(!u1.alive()){
				universe.removeGameEntity(getArmyFromUniverse(a));
				rebuildFlag(a, a2);
			}
			if(!u2.alive()){
				universe.removeGameEntity(getArmyFromUniverse(a2));
				rebuildFlag(a2, a);
			}
		}
		
			
	}

	public void overlapRule(Army a, Flag f) {
		//si c'est une armée d'un camps qui rencontre le drapeau d'un autre camps
		if(f.getTeam().getSide() != a.getTeam().getSide()){
			if ((!f.isCatched()) && !a.haveAFlag()){
				a.captureTheFlag(f);
				universe.removeGameEntity(f);
				universe.addGameEntity(new FlagEmpty(canvas,f.getPosition(),f.getTeam()));
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
