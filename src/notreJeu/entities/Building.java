package notreJeu.entities;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public abstract class Building implements Entity {
	public static final int RENDERING_SIZE = 32;
	protected Point _p;
	
	public Building(Canvas defaultCanvas, Point p){
		_p = (Point) p.clone();
	}

	public abstract void draw(Graphics g);

	@Override
	public Point getPosition() {
		return _p;
	}

	@Override
	public Rectangle getBoundingBox() {
		return (new Rectangle(_p.x,_p.y, RENDERING_SIZE, RENDERING_SIZE));
	}


}
