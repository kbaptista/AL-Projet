package notreJeu;

import java.awt.Point;
import java.util.Timer;
import java.util.TimerTask;

import gameframework.core.GameMovableDriverDefaultImpl;
import gameframework.core.ObservableValue;
import gameframework.moves_rules.MoveBlockerChecker;
import gameframework.moves_rules.MoveStrategy;
import notreJeu.entities.Army;
import notreJeu.rules.CreationFlagRules;

public class Team {

	private static final int COST_RIDER = 7;
	private static final int COST_INFANTRYMAN = 5;
	
	private int _equip;
	private Point _spawn_position;
	private Equip _side;
	private ArmyFactory _army_factory;
	private CreationFlagRules _rule;
	private Timer t ;
	
	private final ObservableValue<Integer> _gold ;
	
	/**
	 * 
	 * @param equip_id
	 * @param position
	 * @param side
	 * @param af
	 * @param flagrule
	 */
	public Team(int equip_id, 
				Point position, 
				Equip side, 
				ArmyFactory af, 
				CreationFlagRules flagrule) {
		
		_gold = new ObservableValue<Integer>(0);
		initTeam(equip_id, position, side, af, flagrule);
		initTimer();
	}
	
	/**
	 * 
	 * @param equip_id
	 * @param position
	 * @param side
	 * @param af
	 * @param flagrule
	 * @param gold
	 */
	public Team(int equip_id,
				Point position, 
				Equip side, 
				ArmyFactory af, 
				CreationFlagRules flagrule,
				ObservableValue<Integer> gold) {
		
		initTeam(equip_id, position, side, af, flagrule);
		_gold = gold ;
		initTimer();
	}
	
	/**
	 * 
	 * @param equip_id
	 * @param position
	 * @param side
	 * @param af
	 * @param flagrule
	 */
	private void initTeam(	int equip_id, 
							Point position,
							Equip side,
							ArmyFactory af, 
							CreationFlagRules flagrule) {
		_equip = equip_id;
		_spawn_position = position;
		_side = side;
		_rule= flagrule;
		_army_factory = af;
	}

	private void initTimer(){
		t = new Timer();
		TimerTask increaseGold = new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				_gold.setValue(_gold.getValue()+10);
				System.out.println("Team"+_equip+
						" : Gold = "+_gold.getValue());
			}
		};
		t.scheduleAtFixedRate(increaseGold, 0, 5*1000);
	}
	
	/**
	 * 
	 * @param p
	 */
	public void setPosition(Point p) {
		_spawn_position=(Point) p.clone();
	}
	
	
	/**
	 * 
	 * @return
	 */
	public Point getPosition(){
		return (Point) _spawn_position.clone();
	}
	
	/**
	 * 
	 * @return
	 */
	public String getPicture(){
		return _side.getPicture();
	}
	
	/**
	 * 
	 * @return
	 */
	public String getColor(){
		return _side.name();
	}
	
	/**
	 * 
	 * @return
	 */
	public int getSide(){
		return _equip;
	}
	
	/**
	 * 
	 * @param armyFactory
	 */
	public void setFactory(ArmyFactory armyFactory){
		_army_factory = armyFactory;
	}
	
	/**
	 * 
	 * @return
	 */
	public ArmyFactory getArmyFactory(){
		return _army_factory;
	}
	
	/**
	 * 
	 * @return
	 */
	public CreationFlagRules getCreationFlagRule(){
		return _rule;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getAge(){
		String s = _army_factory._ageFactory.getClass().getName();
		return s;
	}
	
	/**
	 * 
	 * @return
	 */
	public ObservableValue<Integer> get_gold() {
		return _gold;
	}
	
	/**
	 * Return an Army which has to be added to a universe.
	 * 
	 * @param nb_riders 
	 * @param nb_infantry
	 * @param moveBlockerChecker
	 * @return an Army
	 */
	
	public Army createArmy(int nb_riders, int nb_infantryman, MoveBlockerChecker moveBlockerChecker, MoveStrategy move){
		use_gold(nb_riders*COST_RIDER-nb_infantryman*COST_INFANTRYMAN);
		GameMovableDriverDefaultImpl armyDriver = new GameMovableDriverDefaultImpl();
		Army a = _army_factory.getArmy(nb_riders, nb_infantryman, this, "Player"+_side.toString(), move);
		armyDriver.setStrategy(move);
		armyDriver.setmoveBlockerChecker(moveBlockerChecker);
		a.setDriver(armyDriver);
		a.setPosition(_spawn_position);
		return a;
	}
	
	/**
	 * 
	 * @return
	 */
	public int costRider() {return COST_RIDER;}
	
	/**
	 * 
	 * @return
	 */
	public int costInfantryMan(){return COST_INFANTRYMAN;}
	
	private void use_gold(int amount){
		int gold = _gold.getValue();
		int updated_gold = gold-amount;
		assert updated_gold<0 : "Gold can not have negative value. Please check your Army instanciation.";
		_gold.setValue(updated_gold);
		System.out.println(
				"Cost = " + amount + " | " +
				"Gold Before = "+ gold + " | " +
				"Gold After = " +_gold.getValue());
	}
}
