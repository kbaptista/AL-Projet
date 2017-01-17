package NotreJeu;

import java.awt.Graphics;

import gameframework.core.Movable;
import gameframework.moves_rules.SpeedVector;

public abstract class EntityMovable extends Entity implements Movable {

	@Override
	public SpeedVector getSpeedVector() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSpeedVector(SpeedVector m) {
		// TODO Auto-generated method stub

	}

	@Override
	public void oneStepMove() {
		// TODO Auto-generated method stub

	}

	@Override
	public abstract void draw(Graphics g);

}
