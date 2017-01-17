package NotreJeu;

import gameframework.core.DrawableImage;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class IndestructibleWall extends Entity {
	protected static DrawableImage image = null;
	int x, y;
	public static final int RENDERING_SIZE = 16;

	public IndestructibleWall(Canvas defaultCanvas, int xx, int yy) {
		image = new DrawableImage("images/wall.gif", defaultCanvas);
		x = xx;
		y = yy;
	}

	public void draw(Graphics g) {
		g.drawImage(image.getImage(), x, y, RENDERING_SIZE, RENDERING_SIZE,
				null);
	}

	public Point getPos() {
		return (new Point(x, y));
	}

	public Rectangle getBoundingBox() {
		return (new Rectangle(x, y, RENDERING_SIZE, RENDERING_SIZE));
	}
}
