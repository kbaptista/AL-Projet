package NotreJeu;

import java.awt.Point;

public class Team {

	private int _equip;
	private Point _position;
	private Equip _side;
	
	public Team(int e, Point position, Equip side) {
		_equip = e;
		_position = position;
		_side = side;
	}
	
	public void setPosition(Point p) {
		_position=(Point) p.clone();
	}
	
	public Point getPosition(){
		return _position;
	}
	
	public String getPicture(){
		return _side.getPicture();
	}
	
	public String getColor(){
		return _side.name();
	}
	
	public int getSide(){
		return _equip;
	}
}
