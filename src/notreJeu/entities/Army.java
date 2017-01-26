package notreJeu.entities;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import notreJeu.Team;
import gameframework.core.SpriteManagerDefaultImpl;
import soldier.core.Unit;

public class Army extends EntityMovable {

	public static final int RENDERING_SIZE = 32;
	private SpriteManagerDefaultImpl _spriteManager;
	
	
	private List<Flag> _captured_flags;
	private Unit _unit;
	private Team _side;
	
	public Army(Canvas canvas, Unit unit, Team side, int nb_rider, int nb_infantry) {
		_side = side;
		_unit = unit;
		_captured_flags = new ArrayList<Flag>();
		StringBuffer picture_path = new StringBuffer();
		if (nb_infantry<nb_rider) {
			picture_path.append("images/ctf_rider");
		}else if(nb_infantry>=nb_rider){
			picture_path.append("images/ctf_infantry");
		}

		//picture_path.append(side.getAge());
		picture_path.append(".gif");
		_spriteManager = new SpriteManagerDefaultImpl(picture_path.toString(), canvas, RENDERING_SIZE, 6);
		_spriteManager.setTypes("right", "left", "up","down");
	}
	
	public void captureTheFlag(Flag f){
		_captured_flags.add(f);
	}
	
	public boolean haveAFlag(){
		return !_captured_flags.isEmpty();
	}
	
	public List<Flag> getCapturedFlags(){
		List<Flag> res = new ArrayList<Flag>(_captured_flags.size());
		res.addAll(_captured_flags);
		return res;
	}
	
	public boolean putATeamFlag(Team t){
		Iterator<Flag> it = _captured_flags.iterator();
		boolean res=false;
		Flag f=null;
		while(it.hasNext()){
			f = it.next();
			if(f.getTeam().equals(t)){
				res = true;
				break;
			}
		}
		if(res)
			_captured_flags.remove(f);
		return res;
		
	}
	
	public Team getTeam(){
		return _side;
	}
	
	public void setTeam(Team t){
		_side = t;
	}
	
	//TODO : retirer accès à Unit et ajouter Army.parry et Army.strike
	public Unit getSoldiers(){
		return _unit;
	}
	
	@Override
	public void draw(Graphics g) {
		String spriteType = "";
		Point tmp = getSpeedVector().getDirection();

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
		return (new Rectangle(0, 0, RENDERING_SIZE, RENDERING_SIZE));
	}

	@Override
	public void oneStepMoveAddedBehavior() {
		_spriteManager.increment();
	}

}
