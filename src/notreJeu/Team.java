package notreJeu;

import java.awt.Point;

import notreJeu.rules.CreationFlagRules;

public class Team {

	private int _equip;
	private Point _spawn_position;
	private Equip _side;
	private ArmyFactory _army_factory;
	private CreationFlagRules _rule;
	
	public Team(int equip_id, Point position, Equip side, 
				ArmyFactory af, CreationFlagRules flagrule) {
		_equip = equip_id;
		_spawn_position = position;
		_side = side;
		_rule= flagrule;
	}
	
	public void setPosition(Point p) {
		_spawn_position=(Point) p.clone();
	}
	
	public Point getPosition(){
		return _spawn_position;
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
	
	public void setFactory(ArmyFactory armyFactory){
		_army_factory = armyFactory;
	}
	
	public ArmyFactory getArmyFactory(){
		return _army_factory;
	}
	
	
	
}
