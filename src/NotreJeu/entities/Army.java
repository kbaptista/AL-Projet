package NotreJeu.entities;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import NotreJeu.Team;
import gameframework.core.SpriteManagerDefaultImpl;
import soldier.core.Unit;

public class Army extends EntityMovable {

	public static final int RENDERING_SIZE = 32;
	private SpriteManagerDefaultImpl _spriteManager;
	
	private boolean _hasTheFlag;
	private boolean _movable;
	private Unit _unit;
	private Team _side;
	
	public Army(Canvas canvas, Unit unit, Team side) {
		_side = side;
		_unit = unit;
		_movable = true;
		_hasTheFlag = false;
		_spriteManager = new SpriteManagerDefaultImpl("images/ctf_horseman.gif",canvas, RENDERING_SIZE, 6);
		_spriteManager.setTypes(
				"right", "left", "up",
				"down");
	}
	
	public void captureTheFlag(){
		_hasTheFlag = true;
	}
	
	public boolean haveAFlag(){
		return _hasTheFlag;
	}
	
	public Team getSide(){
		return _side;
	}
	
	public Unit getSoldiers(){
		return _unit;
	}
	
	public void setTeam(Team t){
		_side = t;
	}
	
	@Override
	public void draw(Graphics g) {
		String spriteType = "";
		Point tmp = getSpeedVector().getDirection();
		_movable = true;

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
		g.drawString(_unit.getName(), getPosition().x-1, getPosition().y);
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
