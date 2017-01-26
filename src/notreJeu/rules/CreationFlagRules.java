package notreJeu.rules;

import java.awt.Point;

public interface CreationFlagRules {
	
	public default Point getFlagPosition(Point base, Point reference){
		return base;
	};
	
	public default Point getFlagPosition(Point base){
		return base;
	}
}
