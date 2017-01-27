package notreJeu.buttons;

import java.awt.Canvas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;

import gameframework.moves_rules.MoveStrategyKeyboard;
import notreJeu.ArmyFactory;
import notreJeu.Team;
import notreJeu.buttons.ActionRecruitSoldiers;
import notreJeu.levels.AbstractLevelCTF;

public class ActionReleaseArmy extends Action {

	private Integer valueToPerform;
	
	public ActionReleaseArmy(JButton button, AbstractLevelCTF level, Team team) {
		super(button, level, team);
		valueToPerform = army_cost;
		button.setText(String.valueOf(valueToPerform));
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println(army_cost + " | " );
		if (army_cost<=side.get_gold().getValue()){
			level.addArmy(side, nb_riders, nb_infantryman, new MoveStrategyKeyboard());
			button.setText(String.valueOf(valueToPerform));
		}else {
			button.setText(String.valueOf(valueToPerform));
			
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
