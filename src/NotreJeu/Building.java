package NotreJeu;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public abstract class Building implements Entity {


	public static final int RENDERING_SIZE = 16;
	private Point position ;
	
	
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Point getPosition() {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public Rectangle getBoundingBox() {
		return (new Rectangle(0, 0, 3*RENDERING_SIZE, 3*RENDERING_SIZE));
	}


}
