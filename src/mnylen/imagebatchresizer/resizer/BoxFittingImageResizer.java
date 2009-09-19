package mnylen.imagebatchresizer.resizer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * The <code>BoxFittingImageResizer</code> is an <code>ImageResizer</code>
 * implementation that fits the resized image inside a box of predefined
 * dimensions such that the aspect ratio is kept in all circumstances.
 */
public class BoxFittingImageResizer implements ImageResizer {
	private AspectKeepingImageResizer resizer;
	private int boxWidth;
	private int boxHeight;
	private int longSideLength;
	
	/**
	 * The default width of the box.
	 */
	public static final int DEFAULT_BOX_WIDTH  = 500;
	
	/**
	 * The default height of the box.
	 */
	public static final int DEFAULT_BOX_HEIGHT = 500;
	
	/**
	 * Creates a new <code>BoxFittingImageResizer</code> using the default width and
	 * height for the box.
	 */
	public BoxFittingImageResizer() {
		this(DEFAULT_BOX_WIDTH, DEFAULT_BOX_HEIGHT);
	}
	
	/**
	 * Creates a new <code>BoxFittingImageResizer</code> using the specified width and
	 * height for the box.
	 * 
	 * @param boxWidth the desired width for the box
	 * @param boxHeight the desired height for the box
	 * @throws IllegalArgumentException if the <code>boxWidth</code> or <code>boxHeight</code> is less than 1
	 */
	public BoxFittingImageResizer(int boxWidth, int boxHeight) {
		if (boxWidth < 1)
			throw new IllegalArgumentException("the value of boxWidth parameter must be at least 1");
		
		if (boxHeight < 1)
			throw new IllegalArgumentException("the value of boxHeight parameter must be at least 1");
		
		this.longSideLength = (boxWidth >= boxHeight) ? boxWidth : boxHeight;
		this.boxWidth       = boxWidth;
		this.boxHeight      = boxHeight;
		this.resizer        = new AspectKeepingImageResizer(longSideLength);
	}
	
	/**
	 * Resizes the given image so that it fits the predefined dimensions of the box and then
	 * inserts the resized image in middle of the box. The empty area of the box will be filled
	 * with a background color of white.
	 * 
	 * @param image the image to resize and fit to box
	 */
	@Override
	public BufferedImage resize(BufferedImage image) {
		BufferedImage resizedImage = resizer.resize(image);
		BufferedImage boxImage      = new BufferedImage(boxWidth, boxHeight, BufferedImage.TYPE_INT_RGB);
		Graphics graphics           = boxImage.createGraphics();
		
		
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, boxWidth, boxHeight);
		
		
		int xPos, yPos;
		if (longSideLength == resizedImage.getWidth()) {
			xPos = 0;
			yPos = (boxHeight - resizedImage.getHeight()) / 2;
		} else {
			xPos = (boxWidth  - resizedImage.getWidth())  / 2;
			yPos = 0;
		}
		
		graphics.drawImage(resizedImage, xPos, yPos, Color.WHITE, null);
		graphics.dispose();
		
		return boxImage;
	}
}
