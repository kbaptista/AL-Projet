package notreJeu;

import java.awt.Point;
import java.util.Timer;
import java.util.TimerTask;

import gameframework.core.ObservableValue;
import notreJeu.rules.CreationFlagRules;

public class Team {

	private int _equip;
	private Point _spawn_position;
	private Equip _side;
	private ArmyFactory _army_factory;
	private CreationFlagRules _rule;
	private Timer t ;
	
	private final ObservableValue<Integer> _gold ;
	
	public Team(int equip_id, Point position, Equip side, 
				ArmyFactory af, CreationFlagRules flagrule) {
		_equip = equip_id;
		_spawn_position = position;
		_side = side;
		_rule= flagrule;
		_army_factory = af;
		_gold = new ObservableValue<Integer>(0);
		
		initTimer();
	}
	
	public Team(int equip_id, Point position, Equip side, 
			ArmyFactory af, CreationFlagRules flagrule,
			ObservableValue<Integer> gold) {
		_equip = equip_id;
		_spawn_position = position;
		_side = side;
		_rule= flagrule;
		_army_factory = af;
		_gold = gold ;
		
		initTimer();
	}
	
	private void initTimer(){
		t = new Timer();
		TimerTask increaseGold = new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				_gold.setValue(_gold.getValue()+10);
				System.out.println("Team"+_side+" : Gold = "+_gold.getValue());
			}
		};
		t.scheduleAtFixedRate(increaseGold, 0, 5*1000);
	}
	
	public void setPosition(Point p) {
		_spawn_position=(Point) p.clone();
	}
	
	public Point getPosition(){
		return (Point) _spawn_position.clone();
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
	
	public CreationFlagRules getCreationFlagRule(){
		return _rule;
	}
	
	public String getAge(){
		String s = _army_factory._ageFactory.getClass().getName();
		return s;
	}
	
	public ObservableValue<Integer> get_gold() {
		return _gold;
	}
	
	public void use_gold(int amount){
		int gold = _gold.getValue();
		_gold.setValue(gold-amount);
		System.out.println(
				"Cost = " + amount + " | " +
				"Gold Before = "+ gold + " | " +
				"Gold After = " +_gold.getValue());
	}
}
