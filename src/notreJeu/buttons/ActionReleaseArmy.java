package notreJeu.buttons;

import java.awt.Canvas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;

import notreJeu.ArmyFactory;
import notreJeu.Team;
import notreJeu.buttons.ActionRecruitSoldiers;

public class ActionReleaseArmy extends Action {
	
	public ActionReleaseArmy(JButton button, Canvas canvas) {
		super.button=button;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (army_cost<=side.get_gold().getValue()){
			ArmyFactory a = side.getArmyFactory();
			addArmy(a.getArmy(canvas, nb_riders, nb_infantryman, side, "Player"+String.valueOf(side.getSide()))/*, 
						new MoveStrategyOnClickStraightLine(side.getPosition(), side.getPosition())*/);
			button.setText("0");
		}else {
			
		}
			button.setText(String.valueOf(army_cost));
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
