package mnylen.imagebatchresizer.resizer;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 * An specialized <code>ImageSaver</code> for saving PNG images.
 */
public class PNGImageSaver extends ImageSaver {
	/**
	 * Creates a new <code>JPEGImageSaver</code> with the specified destination
	 * directory and the desired prefix for the saved files.
	 * 
	 * @param dstDirectory the destination directory for the saved files
	 * @param prefix the desired prefix for the saved files
	 * @throws NotADirectoryException if the <code>dstDirectory</code> is not a directory
	 */
	public PNGImageSaver(File dstDirectory, String prefix) throws NotADirectoryException {
		super(dstDirectory, prefix);
	}
	
	/**
	 * Saves the given image.
	 * 
	 * @param image the image to save
	 * @param originalFile the original location of the image
	 * @throws IOException if an I/O error occurs
	 */
	public File save(BufferedImage image, File originalFile) throws IOException {
		File dstFile = determineDestinationFile(originalFile);
		ImageIO.write(image, "png", dstFile);
		
        return dstFile;
	}
	
	/**
     * Gets the file extension used for images saved using this <code>ImageSaver</code>
     * instance.
     * 
     * @return the file extension used for saved images
     */
	@Override
	public String getExtension() {
		return "png";
	}
}
