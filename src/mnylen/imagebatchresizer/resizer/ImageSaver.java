package mnylen.imagebatchresizer.resizer;

import java.io.IOException;
import java.io.File;
import java.awt.image.BufferedImage;

/**
 * An instance of the <code>ImageSaver</code> class can be used for saving images to
 * the predefined destination directory.
 */
public abstract class ImageSaver {
    private File dstDirectory;
    private String prefix;
    
    /**
     * Creates a new <code>ImageSaver</code> instance using the specified save
     * format, destination directory and prefix.
     * 
     * @param dstDirectory the destination directory
     * @param prefix the prefix (if <code>null</code>, an empty string will be used)
     * @throws NotADirectoryException if the <code>dstDirectory</code> is not a directory or does not exist
     */
    protected ImageSaver(File dstDirectory, String prefix)
      throws NotADirectoryException {
        
        if (dstDirectory == null || !(dstDirectory.exists()) || !(dstDirectory.isDirectory()))
            throw new NotADirectoryException("the specified destination directory is not a directory");
        
        if (prefix == null)
            prefix = "";
        
        this.dstDirectory = dstDirectory;
        this.prefix = prefix;
    }
    
    /**
     * Saves the given image.
     * 
     * @param image the image to save
     * @param originalFile  the original location of the image
     * @return the file the image was written to
     * @throws IOException if an I/O error occurs
     */
    public abstract File save(BufferedImage image, File originalFile) throws IOException;
    
    /**
     * Gets the file extension used for images saved using this <code>ImageSaver</code>
     * instance.
     * 
     * @return the file extension used for saved images
     */
    public abstract String getExtension();
    
    /**
     * Determines the destination file for files loaded from the specified file.
     * @param file the file that the image was originally loaded from
     * @return the destination file
     */
    protected File determineDestinationFile(File file) {
        String dstFileName = file.getName();
        int dotPos         = file.getName().lastIndexOf('.');
        if (dotPos != -1)    dstFileName = dstFileName.substring(0, dotPos);
        dstFileName       += "." + getExtension();
        dstFileName        = prefix + dstFileName;
        
        return new File(dstDirectory, dstFileName);
    }
}
