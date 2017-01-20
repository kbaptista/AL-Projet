package notreJeu.rules;

import java.awt.Point;

public class CreationFlagRuleCenterImpl implements CreationFlagRules{

	public Point getFlagPosition(Point base, Point reference){
		Point res = new Point();
		res.setLocation(reference.getX()<base.getX()?base.getX()+2:base.getX()-2, reference.getY()<base.getY()?base.getY()+2:base.getY()-2);
		return res;
	};
	
}
