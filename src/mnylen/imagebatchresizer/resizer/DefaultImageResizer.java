package mnylen.imagebatchresizer.resizer;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**
 * An <code>ImageResizer</code> implementation for resizing images. The
 * implementation does not preserve the aspect ratio automatically.
 */
public class DefaultImageResizer implements ImageResizer {
    private int width;
    private int height;
    
    /**
     * The default width for the resized images.
     */
    public static final int DEFAULT_WIDTH  = 500;
    /**
     * The default height for the resized images.
     */
    public static final int DEFAULT_HEIGHT = 500;
    
    /**
     * Creates a new <code>DefaultImageResizer</code> using the default width
     * and height.
     */
    public DefaultImageResizer() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
    
    /**
     * Creates a new <code>DefaultImageResizer</code> using the specified width
     * and height for the resized images.
     * 
     * @param width the desired width for resized images
     * @param height the desired height for resized images
     * @throws IllegalArgumentException if the <code>width</code> or <code>height</code> parameters
     *         are less than <code>1</code>
     */
    public DefaultImageResizer(int width, int height) throws IllegalArgumentException {
    	if (width < 1)
    		throw new IllegalArgumentException("the value of the width parameter must not be less than 1");
    	
    	if (height < 1)
    		throw new IllegalArgumentException("the value of the height parameter must not be less than 1");
    	
    	this.width = width;
    	this.height = height;
    }
    
    /**
     * Sets the width for the resized images.
     * 
     * @param width the desired width
     * @throws IllegalArgumentException if the <code>width</code> parameter is less than <code>1</code>
     */
    public void setWidth(int width) throws IllegalArgumentException {
        if (width < 1)
            throw new IllegalArgumentException("the value of the width parameter must not be less than 1");
        
        this.width = width;
    }
    
    /**
     * Sets the height for the resized images.
     * 
     * @param height the desired height
     * @throws IllegalArgumentException if the <code>height</code> parameter is less than <code>1</code>
     */
    public void setHeight(int height) throws IllegalArgumentException {
        if (height < 1)
            throw new IllegalArgumentException("the value of the height parameter must not be less than 1");
        
        this.height = height;
    }
    
    /**
     * Resizes the given image. <strong>Please note that the aspect ratio will not be
     * preserved in the resized images.</strong>
     * 
     * @param image the image to resize
     * @return the resized image
     */
    public BufferedImage resize(BufferedImage image) {
        double sx = ((double)width / image.getWidth());
        double sy = ((double)height / image.getHeight());
        
        AffineTransform tx = new AffineTransform();
        tx.scale(sx, sy);
        
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        return op.filter(image, op.createCompatibleDestImage(image, null));
    }
}
