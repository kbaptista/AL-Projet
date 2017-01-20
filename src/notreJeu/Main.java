package notreJeu;

import gameframework.core.GameLevel;

import java.util.ArrayList;

import notreJeu.coreextensions.GameCTFImpl;
import notreJeu.levels.CTFLevel1;

public class Main {

	public static void main(String[] args) {
		int size = 31;
		GameCTFImpl g = new GameCTFImpl(size,size);
		ArrayList<GameLevel> levels = new ArrayList<>();
		
		levels.add(new CTFLevel1(g, size));
		g.setLevels(levels);
		g.start();
	}
}
