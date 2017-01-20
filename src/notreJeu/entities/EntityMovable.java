package notreJeu.entities;

import java.awt.Graphics;

import gameframework.core.GameMovable;

public abstract class EntityMovable extends GameMovable implements Entity {

	@Override
	public abstract void draw(Graphics g);

}
