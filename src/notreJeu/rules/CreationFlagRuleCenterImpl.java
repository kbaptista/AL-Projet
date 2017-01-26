package notreJeu.rules;

import java.awt.Point;

public class CreationFlagRuleCenterImpl implements CreationFlagRules{

	private Point _reference;
	
	public CreationFlagRuleCenterImpl(Point reference) {
		_reference = reference;
	}
	
	private double getPos(double a, double b){
		if(a<b)
			return a-2;
		else if(a == b)
			return a;
		else
			return a+2;
	}
		
	public Point getFlagPosition(Point base, Point reference){
		Point res = new Point();
		res.setLocation(getPos(base.getX(),reference.getX()), getPos(base.getY(),reference.getY()));
		return res;
	};
	
	public Point getFlagPosition(Point base){
		Point res = new Point();
		res.setLocation(getPos(base.getX(),_reference.getX()), getPos(base.getY(),_reference.getY()));
		return res;
	}
	
}
