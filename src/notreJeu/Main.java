package notreJeu;

import java.util.ArrayList;

import notreJeu.coreextensions.GameCTFImpl;
import notreJeu.levels.AbstractLevelCTF;
import notreJeu.levels.CTFLevel1;
import notreJeu.levels.CTFLevel2;

public class Main {
	public static void main(String[] args) {
		int size = 31;
		/* TODO : problème de création : 
		 * 
		 */
		GameCTFImpl g = new GameCTFImpl(size,size);
		ArrayList<AbstractLevelCTF> levels = new ArrayList<>();
		
		levels.add(new CTFLevel1(g, size)); //TMP
		levels.add(new CTFLevel2(g, size));
		g.setLevels(levels);
		g.start();
	}
}
