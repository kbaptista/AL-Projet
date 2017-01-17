package NotreJeu;

import gameframework.core.GameDefaultImpl;
import gameframework.core.GameLevel;

import java.util.ArrayList;

public class Main {
/*
 * 
*/
	
	public static void main(String[] args) {
		GameDefaultImpl g = new GameDefaultImpl();
		ArrayList<GameLevel> levels = new ArrayList<>();
		
		levels.add(new FirstStep(g));
		g.setLevels(levels);
		g.start();
	}

}
