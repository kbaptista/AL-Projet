package NotreJeu.entities;

import gameframework.core.DrawableImage;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class IndestructibleWall implements Entity {
	protected static DrawableImage image = null;
	int x, y;
	public static final int RENDERING_SIZE = 32;

	public IndestructibleWall(Canvas defaultCanvas, int xx, int yy) {
		image = new DrawableImage("images/wall.gif", defaultCanvas);
		x = xx;
		y = yy;
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(image.getImage(), x, y, RENDERING_SIZE, RENDERING_SIZE,
				null);
	}

	@Override
	public Point getPosition() {
		return (new Point(x, y));
	}

	@Override
	public Rectangle getBoundingBox() {
		return (new Rectangle(x, y, RENDERING_SIZE, RENDERING_SIZE));
	}

}
