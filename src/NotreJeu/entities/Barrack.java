package NotreJeu.entities;

import java.awt.Canvas;
import java.awt.Graphics;

import soldier.core.AgeAbstractFactory;
import gameframework.core.DrawableImage;

public class Barrack extends Building{
	protected static DrawableImage image = null;
	
	private AgeAbstractFactory _factory;
	
	public Barrack(Canvas defaultCanvas, int xx, int yy) {
		super(defaultCanvas, xx, yy);
		image = new DrawableImage("images/ctf_castle.png", defaultCanvas); //TODO:find picture
		//TODO: choisir l'age a la création de barrack
	}

	public void createArmy(){
		//TODO
	}
	
	@Override
	public void draw(Graphics g) {
		g.drawImage(image.getImage(), super.x, super.y, RENDERING_SIZE, RENDERING_SIZE,
				null);
	}
	
}
