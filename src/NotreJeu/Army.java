package NotreJeu;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import gameframework.core.Movable;
import gameframework.core.SpriteManagerDefaultImpl;
import soldier.core.Unit;

public class Army extends EntityMovable {

	public static final int RENDERING_SIZE = 16;
	private SpriteManagerDefaultImpl spriteManager;
	
	private boolean hasTheFlag;
	private boolean movable ;
	private Unit unit;
	private int side;
	
	public Army(Canvas defaultCanvas, Unit unit, int side) {
		// TODO Auto-generated constructor stub
		this.side=side;
		this.unit=unit;
		movable = true;
		spriteManager = new SpriteManagerDefaultImpl("images/ctf_horseman.gif",defaultCanvas, RENDERING_SIZE, 6);
	}
	
	public void CaptureTheFlag(){
		hasTheFlag=true;
	}
	
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		String spriteType = "";
		Point tmp = getSpeedVector().getDirection();
		movable = true;

		spriteType = "right";

		if (tmp.getX() == -1) {
			spriteType += "left";
		} else if (tmp.getY() == 1) {
			spriteType += "down";
		} else if (tmp.getY() == -1) {
			spriteType += "up";
		} else {
			spriteType += "right";
		}

		spriteManager.setType(spriteType);
		spriteManager.draw(g, getPosition());
	}

	@Override
	public Rectangle getBoundingBox() {
		return (new Rectangle(0, 0, 3*RENDERING_SIZE, 3*RENDERING_SIZE));
	}

	@Override
	public void oneStepMoveAddedBehavior() {
		spriteManager.increment();
	}

}
