package notreJeu;

import java.util.HashSet;
import java.util.Set;

import gameframework.core.Game;
import gameframework.core.GameLevelDefaultImpl;

public abstract class AbstractLevelCTF extends GameLevelDefaultImpl{

	protected Set<Team> _teams;
	
	public AbstractLevelCTF(Game g) {
		super(g);
		
		_teams = new HashSet<Team>(); 
	}

	
	
}
