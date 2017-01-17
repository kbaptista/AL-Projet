package NotreJeu;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import gameframework.core.Drawable;
import gameframework.core.GameEntity;
import gameframework.core.Overlappable;

public abstract class Entity implements GameEntity, Drawable, Overlappable {

	@Override
	public Rectangle getBoundingBox() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Point getPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public abstract void draw(Graphics g);

}
