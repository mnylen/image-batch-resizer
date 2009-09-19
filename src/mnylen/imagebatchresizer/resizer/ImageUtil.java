package mnylen.imagebatchresizer.resizer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 * Contains utility methods that help working with images.
 */
public class ImageUtil {
    /**
     * Loads an image from the file system. If the file contains valid image
     * data, it is placed into the returned <code>Image</code> instance.
     * If for some reason the file contents can not be determined as image data,
     * <code>null</code> is going to be returned.
     * 
     * @param filename the file to load
     * @return the loaded image
     * @throws FileNotFoundException if the file was not found
     * @throws IOException if the file could not be readed
     * @throws IllegalArgumentException if the <code>filename</code> parameter is <code>null</code>
     */
    public static BufferedImage loadImage(String filename)
      throws FileNotFoundException, IOException, IllegalArgumentException {
        
    	if (filename == null)
    		throw new IllegalArgumentException("the filename parameter must not be null");
    	
        File file = new File(filename);
        if (!(file.exists()))
            throw new FileNotFoundException(filename);
        
        return ImageIO.read(file);
    }
}
