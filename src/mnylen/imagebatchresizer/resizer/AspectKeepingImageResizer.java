package mnylen.imagebatchresizer.resizer;

import java.awt.image.BufferedImage;

/**
 * An <code>ImageResizer</code> implementation that resizes images so that the
 * aspect ratio of the images will be kept in all circumstances.
 */
public class AspectKeepingImageResizer implements ImageResizer {
    private int longSideLength;
    private DefaultImageResizer resizer;
    
    /**
     * The default size for the long side of resized images.
     */
    public static final int DEFAULT_LONG_SIDE_LENGTH = 600;
    
    /**
     * Creates a new <code>AspectKeepingImageResizer</code> instance using the
     * default length for the long side.
     */
    public AspectKeepingImageResizer() {
        this(DEFAULT_LONG_SIDE_LENGTH);
    }
    
    /**
     * Creates a new <code>AspectKeepingImageResizer</code> instance using the
     * specified length for the long side of the resized images.
     * 
     * @param longSideLength the desired length of the long side in resized images
     * @throws IllegalArgumentException if the <code>longSideLength</code> parameter is less than 1
     */
    public AspectKeepingImageResizer(int longSideLength) throws IllegalArgumentException {
    	if (longSideLength < 1)
    		throw new IllegalArgumentException("the value of longSideLength parameter must not be less than 1");
    	
    	this.longSideLength = longSideLength;
    	this.resizer  = new DefaultImageResizer();
    }
    
    /**
     * Sets the length of the long side of the resized images.
     * 
     * @param longSideLength the desired length of the long side
     * @throws IllegalArgumentException if the <code>longSideLength</code> parameter is less than 1
     */
    public void setLongSideLength(int longSideLength) throws IllegalArgumentException {
        if (longSideLength < 1)
            throw new IllegalArgumentException("the value of longSideLength parameter must not be less than 1");
        
        this.longSideLength = longSideLength;
    }
    
    /**
     * Resizes the source image. The length of the long side of the resized image will be set
     * to the value set by {@link #setLongSideLength(int)} and the length of the short side will
     * be calculated automatically so that the aspect ratio is kept in the resized image.
     */
    public BufferedImage resize(BufferedImage srcImage) {
        int width, height;
        if (srcImage.getWidth() >= srcImage.getHeight()) {
            width  = longSideLength;
            height = Math.round(((float)width * srcImage.getHeight()) / srcImage.getWidth());
        } else {
            height = longSideLength;
            width  = Math.round(((float)height * srcImage.getWidth()) / srcImage.getHeight());
        }
        
        resizer.setWidth(width);
        resizer.setHeight(height);
        
        return resizer.resize(srcImage);
    }
}
