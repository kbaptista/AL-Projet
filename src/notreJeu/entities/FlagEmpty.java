package notreJeu.entities;

import gameframework.core.DrawableImage;

import java.awt.Canvas;
import java.awt.Point;

import notreJeu.Team;

public class FlagEmpty extends Flag{
	
	public FlagEmpty(Canvas defaultCanvas, Point pos, Team side) {
		super(defaultCanvas,pos,side);
		_image = new DrawableImage("images/ctf_flag_empty.png", defaultCanvas);
		_catched=true;
	}

}
