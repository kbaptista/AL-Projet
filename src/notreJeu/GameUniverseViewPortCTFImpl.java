package notreJeu;

import gameframework.core.Drawable;
import gameframework.core.GameEntity;
import gameframework.core.GameUniverse;
import gameframework.core.GameUniverseViewPort;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Iterator;

public class GameUniverseViewPortCTFImpl implements GameUniverseViewPort {
	private Image buffer;
	private Graphics bufferGraphics;
	protected Canvas canvas;
	protected BackgroundImageCTF background;
	protected GameUniverse universe;

	public GameUniverseViewPortCTFImpl(Canvas canvas, GameUniverse universe, int sprite_size, int sprite_per_line) {
		this.canvas = canvas;
		buffer = canvas.createImage(canvas.getWidth(), canvas.getHeight());
		bufferGraphics = buffer.getGraphics();
		background = new BackgroundImageCTF(backgroundImage(), canvas, sprite_size, sprite_per_line);
		this.universe = universe;
	}

	protected String backgroundImage() {
		return "images/background_image.gif";
	}

	public void setBackground(String filename, int sprite_size, int sprite_per_line) {
		background = new BackgroundImageCTF(filename, canvas, sprite_size, sprite_per_line);
	}

	public void paint() {
		background.draw(bufferGraphics);
		Iterator<GameEntity> gt = universe.gameEntities();
		for (; gt.hasNext();) {
			GameEntity tmp = gt.next();
			if (tmp instanceof Drawable) {
				((Drawable) tmp).draw(bufferGraphics);
			}
		}
		refresh();
	}

	public void refresh() {
		canvas.getGraphics().drawImage(buffer, 0, 0, canvas.getWidth(),
				canvas.getHeight(), canvas);
	}
}
