package NotreJeu;

public enum Equip{
	YELLOW, GREEN, BLUE, VIOLET, RED, ORANGE;
	//YELLOW(0), GREEN(1), BLUE(2), VIOLET(3), RED(4), ORANGE(5);
	//private int value;

	/*private Equip(int value){
		this.value = value;
	}

	public int getValue(){
		return this.value;
	}*/

	public String getPicture(){
		switch(this){
		case YELLOW:
			return "images/ctf_flag_yellow.png"; 
		case GREEN:
			return "images/ctf_flag_green.png"; 
		case BLUE:
			return "images/ctf_flag_blue.png"; 
		case VIOLET:
			return "images/ctf_flag_violet.png";
		case RED:
			return "images/ctf_flag_red.png"; 
		case ORANGE:
			return "images/ctf_flag_orange.png"; 
		default:
			return "images/ctf_flag_white.png";
		}
	} 

};