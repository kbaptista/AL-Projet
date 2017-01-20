package notreJeu;

import gameframework.core.DrawableImage;

import java.awt.Canvas;
import java.awt.Graphics;

public class BackgroundImageCTF extends DrawableImage {

	private int _slides_size;
	private int _slides_number;

	public BackgroundImageCTF(String filename, Canvas canvas, int slides_size, int slides_number_per_line) {
		super(filename, canvas);
		_slides_size = slides_size;
		_slides_number = slides_number_per_line;
	}

	@Override
	public void draw(Graphics g) {
		for (int i = 0; i < _slides_number; i++) {
			for (int j = 0; j < _slides_number; j++) {
				g.drawImage(image, i*_slides_size, j*_slides_size, _slides_size, _slides_size, canvas);
			}
		}

	}
}
