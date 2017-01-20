package NotreJeu;

import java.awt.Point;

public class Team {

	private int _equip;
	private Point _position;
	private Equip _side;
	//private String _picture;
	
	public Team(int e, Point position, Equip side) {
		_equip = e;
		_position = position;
		_side = side;
		//_picture = picture;
	}
	
	public void setPosition(Point p) {
		_position=(Point) p.clone();
	}
	
	public Point getPosition(){
		return _position;
	}
	
	public String getPicture(){
		return _side.getPicture();
		//System.out.println("lol "+_picture);
		//return _picture;
	}
	
	public String getColor(){
		/*String[] blocs = _picture.split("/");
		blocs = blocs[blocs.length-1].split("_");
		blocs = blocs[blocs.length-1].split(".");
		return blocs[0];*/
		return _side.name();
	}
	
	public int getSide(){
		return _equip;
	}
}
