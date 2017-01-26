package notreJeu.entities;

import gameframework.core.DrawableImage;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import notreJeu.Team;

public class Flag implements Entity {
	protected DrawableImage _image = null;
	protected Point _position;
	protected boolean _catched;
	protected Team _side;
	public static final int RENDERING_SIZE = 32;

	public Flag(Canvas defaultCanvas, Point pos, Team side) {
		_image = new DrawableImage(side.getPicture(), defaultCanvas);
		//
		_position = pos;
		_catched = false;
		_side=side;
	}

	public Point getPosition() {
		return _position;
	}
	
	/*
	 * if this flag is already catched, you can't catch it.
	 * return : true (you can take it), false (already catched)
	*/
	public boolean isCatched(){
		return _catched;
	}
	
	public void catchTheFlag(){
		_catched = true;
	}

	public Team getTeam(){
		return _side;
	}

	public void draw(Graphics g) {
		g.drawImage(_image.getImage(), getPosition().x, getPosition().y, RENDERING_SIZE, RENDERING_SIZE,
				null);
	}

	public Rectangle getBoundingBox() {
		return (new Rectangle((int) _position.getX(), (int) _position.getY(),
				RENDERING_SIZE, RENDERING_SIZE));
	}
}
