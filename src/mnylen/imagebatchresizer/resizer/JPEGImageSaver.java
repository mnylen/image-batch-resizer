package mnylen.imagebatchresizer.resizer;

import java.io.File;
import java.io.IOException;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DirectColorModel;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

/**
 * An specialized <code>ImageSaver</code> for saving JPEG images.
 */
public class JPEGImageSaver extends ImageSaver {
	/**
	 * Creates a new <code>JPEGImageSaver</code> with the specified destination
	 * directory and the desired prefix for the saved files.
	 * 
	 * @param dstDirectory the destination directory for the saved files
	 * @param prefix the desired prefix for the saved files
	 * @throws NotADirectoryException if the <code>dstDirectory</code> is not a directory
	 */
	public JPEGImageSaver(File dstDirectory, String prefix) throws NotADirectoryException {
		super(dstDirectory, prefix);
	}
	
	/**
	 * Saves the given image.
	 * 
	 * @param image the image to save
	 * @param originalFile the original location of the image
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	public File save(BufferedImage image, File originalFile) throws IOException {
		File dstFile          = determineDestinationFile(originalFile);
		ImageWriter writer    = ImageIO.getImageWritersByFormatName("jpeg").next();
        ImageWriteParam param = writer.getDefaultWriteParam();
        param.setSourceBands(new int[] { 0, 1, 2 });
        
        ColorModel cm = new DirectColorModel(24,
        		0x00ff0000, // red
        		0x0000ff00, // green
        		0x000000ff, // blue
        		0x0);       // alpha
        
        param.setDestinationType(new ImageTypeSpecifier(
        		cm, cm.createCompatibleSampleModel(1, 1)));
        
        ImageOutputStream outStream = ImageIO.createImageOutputStream(dstFile);
        writer.setOutput(outStream);
        writer.write(null, new IIOImage(image, null, null), param);
        writer.dispose();
        outStream.close();
        
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
		return "jpg";
	}
}
