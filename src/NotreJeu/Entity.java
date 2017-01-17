package NotreJeu;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.text.Position;

import gameframework.core.Drawable;
import gameframework.core.GameEntity;
import gameframework.core.Overlappable;

public abstract class Entity implements GameEntity, Drawable, Overlappable {
	
	private int RENDERING_SIZE = 16;
	private Point position = new Point();
	
	@Override
	public Rectangle getBoundingBox() {
		// TODO Auto-generated method stub
		return (new Rectangle(0, 0, RENDERING_SIZE, RENDERING_SIZE));
	}

	@Override
	public Point getPosition() {
		// TODO Auto-generated method stub
		return position;
	}
	
	public abstract void draw(Graphics g);

}
