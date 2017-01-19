package NotreJeu.entities;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import NotreJeu.FirstStep.Equip;
import gameframework.core.SpriteManagerDefaultImpl;
import soldier.core.Unit;

public class Army extends EntityMovable {

	public static final int RENDERING_SIZE = 16;
	private SpriteManagerDefaultImpl _spriteManager;
	
	private boolean _hasTheFlag;
	private boolean _movable;
	private Unit _unit;
	private Equip _side;
	
	public Army(Canvas canvas, Unit unit, Equip side) {
		// TODO Auto-generated constructor stub
		_side = side;
		_unit = unit;
		_movable = true;
		_hasTheFlag = false;
		_spriteManager = new SpriteManagerDefaultImpl("images/ctf_horseman.gif",canvas, RENDERING_SIZE, 6);
	}
	
	public void captureTheFlag(){
		_hasTheFlag = true;
	}
	
	public boolean haveAFlag(){
		return _hasTheFlag;
	}
	
	public Equip getSide(){
		return _side;
	}
	
	public Unit getSoldiers(){
		return _unit;
	}
	
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		String spriteType = "";
		Point tmp = getSpeedVector().getDirection();
		_movable = true;

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

		_spriteManager.setType(spriteType);
		_spriteManager.draw(g, getPosition());
	}

	@Override
	public Rectangle getBoundingBox() {
		return (new Rectangle(0, 0, 3*RENDERING_SIZE, 3*RENDERING_SIZE));
	}

	@Override
	public void oneStepMoveAddedBehavior() {
		_spriteManager.increment();
	}

}
