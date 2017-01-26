package notreJeu.buttons;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

public class AddSoldierButtonAction implements MouseListener{
	private int nb_soldier = 0;
	private int cost ;
	private JButton button;
	private String type ;
	
	public AddSoldierButtonAction(JButton but, String type, int cost) {
		button = but;
		this.type = type;
		this.cost = cost;
		button.setText(String.valueOf(nb_soldier));
	}
	
	public int getCost(){return cost;}
	public String getType(){return type;}
	public int getValue(){return nb_soldier;}
	
	public void setValue(int i){
		nb_soldier=i;
		button.setText(String.valueOf(nb_soldier));
	}

	private void decrease(){
		nb_soldier--;
		button.setText(String.valueOf(nb_soldier));
	}
	private void increase(){
		nb_soldier++;
		button.setText(String.valueOf(nb_soldier));
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
