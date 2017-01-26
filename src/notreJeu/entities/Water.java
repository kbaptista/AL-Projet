package notreJeu.entities;

import gameframework.core.DrawableImage;
import gameframework.moves_rules.MoveBlocker;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class Water implements Entity, MoveBlocker {
	protected static DrawableImage image = null;
	public static final int RENDERING_SIZE = 32;
	public Point _p;

	public Water(Canvas defaultCanvas, Point p) {
		image = new DrawableImage("images/ctf_water.png", defaultCanvas);
		_p = (Point) p.clone();
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(image.getImage(), _p.x, _p.y, RENDERING_SIZE, RENDERING_SIZE,null);
	}

	@Override
	public Point getPosition() {
		return (new Point(_p.x, _p.y));
	}

	@Override
	public Rectangle getBoundingBox() {
		return (new Rectangle(_p.x, _p.y, RENDERING_SIZE, RENDERING_SIZE));
	}


}
