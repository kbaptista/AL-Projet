package notreJeu;

import gameframework.core.GameLevel;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		int size = 31;
		GameCTFImpl g = new GameCTFImpl(size,size);
		ArrayList<GameLevel> levels = new ArrayList<>();
		
		levels.add(new FirstStep(g, size));
		g.setLevels(levels);
		g.start();
	}
}
