package NotreJeu.entities;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public abstract class Building implements Entity {
	public static final int RENDERING_SIZE = 32;
	protected int x,y ;
	
	public Building(Canvas defaultCanvas, int xx, int yy){
		x = xx;
		y = yy;
	}

	public abstract void draw(Graphics g);

	@Override
	public Point getPosition() {
		return (new Point(x, y));
	}

	@Override
	public Rectangle getBoundingBox() {
		return (new Rectangle(x,y, 3*RENDERING_SIZE, 3*RENDERING_SIZE));
	}


}
