package notreJeu;

import soldier.core.AgeAbstractFactory;

@FunctionalInterface
public interface GetAgeFactory {
	public AgeAbstractFactory getAgeFactory();
}
