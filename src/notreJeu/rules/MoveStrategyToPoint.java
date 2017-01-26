package notreJeu.rules;

import java.awt.Point;

import gameframework.moves_rules.MoveStrategyStraightLine;
import gameframework.moves_rules.SpeedVector;

public class MoveStrategyToPoint extends MoveStrategyStraightLine{

	public MoveStrategyToPoint(Point pos, Point goal) {
		super(pos, goal);
	}
	
	@Override
	public SpeedVector getSpeedVector(){		//UNFINISHED
		SpeedVector sv = super.getSpeedVector();

		return sv;
	}

}
