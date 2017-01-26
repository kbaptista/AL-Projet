package notreJeu.rules;

import java.awt.Point;

public class CreationFlagRuleHorizontalAxisImpl implements CreationFlagRules{

	private int _reference;
	
	public CreationFlagRuleHorizontalAxisImpl(int axis) {
		_reference = axis;
	}
	
	public Point getFlagPosition(Point base, Point reference){
		Point res = new Point();
		res.setLocation(base.getX(), reference.getY()<base.getY()?base.getY()+2:base.getY()-2);
		return res;
	};
	
	public Point getFlagPosition(Point base){
		Point res = new Point();
		res.setLocation(base.getX(), base.getY()<_reference?base.getY()-2:base.getY()+2);
		return res;
	}
}
