package notreJeu.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;

import notreJeu.ArmyFactory;
import notreJeu.Team;
import notreJeu.buttons.AddSoldierButtonAction;

public class ReleaseArmyButtonAction implements ActionListener{
	
	private Set<AddSoldierButtonAction> instanciateActions;
	private JButton button;
	
	public ReleaseArmyButtonAction(MouseListener[] list, JButton button) {
		this.button=button;
		instanciateActions = new HashSet<AddSoldierButtonAction>();
		for (MouseListener act : list) {
			instanciateActions.add((AddSoldierButtonAction)act);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		int nb_infantryman = 0 ;
		int nb_riders = 0 ;
		if(true){//TODO : vérifier si le joueur possède une équipe

			int army_cost = 0;
			for(AddSoldierButtonAction action : instanciateActions){
				if(action.getType().matches("rider")){
					nb_riders = action.getValue();
					army_cost+= nb_riders*action.getCost();
				}
				if(action.getType().matches("infantryman")){
					nb_infantryman = action.getValue();
					army_cost+= nb_infantryman*action.getCost();
				}
				action.setValue(0);
			}
			Team t = _teams_played.iterator().next();
			System.out.println("Army_cost = " + army_cost);
			if (army_cost<=t.get_gold().getValue()){
				ArmyFactory a =t.getArmyFactory();
				addArmy(a.getArmy(_canvas, nb_riders, nb_infantryman, t, "Player"+String.valueOf(t.getSide()))/*, 
						new MoveStrategyOnClickStraightLine(t.getPosition(), t.getPosition())*/);
				button.setText("0");
			}else {
				//if the army is too expensive, it is not instanciate and the buttons are given back their values
				for(AddSoldierButtonAction action : instanciateActions){
					if(action.getType().matches("rider")){action.setValue(nb_riders);}
					if(action.getType().matches("infantryman")){action.setValue(nb_infantryman);}
				}
				button.setText(String.valueOf(army_cost));
			}
		}
	}
}
