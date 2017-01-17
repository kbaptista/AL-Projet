package NotreJeu;

import gameframework.core.DrawableImage;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class Flag extends Entity {
	protected DrawableImage image = null;
	protected Point _position;
	protected boolean _catched;
	public static final int RENDERING_SIZE = 16;

	public Flag(Canvas defaultCanvas, Point pos, boolean side) {
		if(side)
			image = new DrawableImage("images/ctf_green-flag.gif", defaultCanvas);
		else
			image = new DrawableImage("images/ctf_yellow-flag.gif", defaultCanvas);
		_position = pos;
		_catched = false;
	}

	public Point getPosition() {
		return _position;
	}

	public void draw(Graphics g) {
		if(!_catched)
			g.drawImage(image.getImage(), (int) getPosition().getX(),
				(int) getPosition().getY(), RENDERING_SIZE, RENDERING_SIZE,
				null);
	}

	public Rectangle getBoundingBox() {
		return (new Rectangle((int) _position.getX(), (int) _position.getY(),
				RENDERING_SIZE, RENDERING_SIZE));
	}
}
