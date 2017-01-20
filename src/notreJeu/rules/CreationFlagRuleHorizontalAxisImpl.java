package notreJeu.rules;

import java.awt.Point;

public class CreationFlagRuleHorizontalAxisImpl implements CreationFlagRules{

	public Point getFlagPosition(Point base, Point reference){
		Point res = new Point();
		res.setLocation(base.getX(), reference.getY()<base.getY()?base.getY()+2:base.getY()-2);
		return res;
	};
	
}
