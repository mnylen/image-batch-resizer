package mnylen.imagebatchresizer.resizer;

import java.awt.image.BufferedImage;

/**
 * An interface that defines methods for resizing images.
 */
public interface ImageResizer {
    /**
     * Resizes the source image. 
     * @param image the image to resize
     * @return the resized image
     */
    public BufferedImage resize(BufferedImage image);
}
