package notreJeu.entities;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;

import soldier.ages.AgeMiddleFactory;
import soldier.core.AgeAbstractFactory;
import soldier.core.Unit;
import soldier.core.Weapon;
import gameframework.core.DrawableImage;
import notreJeu.Team;

public class Barrack extends Building{
	protected static DrawableImage image = null;
	
	public Barrack(Canvas defaultCanvas, Point p) {
		super(defaultCanvas,p);
		image = new DrawableImage("images/ctf_castle.png", defaultCanvas);
	}
	
	@Override
	public void draw(Graphics g) {
		g.drawImage(image.getImage(), _p.x, _p.y, RENDERING_SIZE, RENDERING_SIZE, null);
	}
	
}
