package notreJeu.buttons;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

import notreJeu.Team;
import notreJeu.levels.AbstractLevelCTF;

public class ActionRecruitSoldiers extends Action{
	
	private String type;
	private Integer valueToPerform;
	
	/**
	 * 
	 * @param butt the button the action will be applied on
	 * @param level 
	 * @param type has to be either Infantry or Rider
	 * @param team 
	 */
	public ActionRecruitSoldiers(JButton butt, AbstractLevelCTF level, String type, Team team) {
		super(butt, level, team);
		this.type=type;
		if(type.matches("Infantry"))
			valueToPerform = nb_infantryman;
		else if(type.matches("Rider"))
			valueToPerform = nb_riders;
			
		button.setText(String.valueOf(valueToPerform));
	}
	
	public String getType(){return type;}

	private void decrease(){
		valueToPerform--;
		button.setText(String.valueOf(valueToPerform));
	}
	private void increase(){
		valueToPerform++;
		button.setText(String.valueOf(valueToPerform));
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.isShiftDown()){
			decrease();
		}else{
			increase();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
