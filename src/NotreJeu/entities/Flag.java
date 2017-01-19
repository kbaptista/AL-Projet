package NotreJeu.entities;

import gameframework.core.DrawableImage;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import NotreJeu.FirstStep.Equip;

public class Flag implements Entity {
	protected DrawableImage image = null;
	protected Point _position;
	protected boolean _catched;
	protected Equip _side;
	public static final int RENDERING_SIZE = 32;

	public Flag(Canvas defaultCanvas, Point pos, Equip side) {
		image = new DrawableImage(side.getPicture(), defaultCanvas);
		_position = pos;
		_catched = false;
		_side=side;
	}

	public Point getPosition() {
		return _position;
	}
	
	/*
	 * if this flag is already catched, you can't catch it.
	 * return : true (you take it), false (already catched)
	*/
	public boolean alreadyCatched(){
		return _catched?false:true;
	}
	
	public void catchTheFlag(){
		_catched = true;
	}

	public Equip getSide(){
		return _side;
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
