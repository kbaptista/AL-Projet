package NotreJeu;

public enum Equip{
	YELLOW, GREEN, BLUE, VIOLET, RED, ORANGE;

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