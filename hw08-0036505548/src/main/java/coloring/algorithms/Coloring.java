package coloring.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import marcupic.opjj.statespace.coloring.Picture;

/**
 * This class represents strategy for coloring. It implements four interfaces
 * used for giving as list of all neighbours for given pixel(Function) -> it
 * returs list of neighbours(only upper lower left and right), predicate-> it
 * tests whether given pixel's color should be changed ,consumer-> sets color
 * for given pixel to fillColor ,supplier-> it returns referenced pixel
 * 
 * @author af
 *
 */
public class Coloring implements Supplier<Pixel>, Consumer<Pixel>, Predicate<Pixel>, Function<Pixel, List<Pixel>> {
	/**
	 * Starting pixel
	 */
	private Pixel reference;
	/**
	 * Reference to picture
	 */
	private Picture picture;
	/**
	 * Color to fill
	 */
	private int fillColor;
	/**
	 * Color to refere to
	 */
	private int refColor;

	/**
	 * Public constructor for coloring
	 * 
	 * @param reference to pixel
	 * @param picture
	 * @param fillColor
	 */
	public Coloring(Pixel reference, Picture picture, int fillColor) {
		super();
		this.reference = reference;
		this.picture = picture;
		this.fillColor = fillColor;
		this.refColor = picture.getPixelColor(reference.getX(), reference.getY());
	}

	@Override
	public void accept(Pixel t) {
		picture.setPixelColor(t.getX(), t.getY(), fillColor);

	}

	@Override
	public Pixel get() {
		return reference;
	}

	@Override
	public boolean test(Pixel t) {
		return picture.getPixelColor(t.getX(), t.getY()) == refColor;
	}

	@Override
	public List<Pixel> apply(Pixel t) {
		int[] xmove = new int[] { 0, 1, 0, -1 };
		int[] ymove = new int[] { -1, 0, 1, 0 };
		List<Pixel> list = new ArrayList<>();
		int xpos = t.getX();
		int ypos = t.getY();
		for (int i = 0; i < 4; i++) {
			if (xpos + xmove[i] >= 0 && xpos + xmove[i] < picture.getWidth()) {
				if (ypos + ymove[i] >= 0 && ypos + ymove[i] < picture.getHeight()) {
					list.add(new Pixel(xpos + xmove[i], ypos + ymove[i]));
				}
			}
		}
		return list;

	}

}
