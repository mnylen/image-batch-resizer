package mnylen.imagebatchresizer.resizer.tests;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;

import org.junit.*;
import static org.junit.Assert.*;

import mnylen.imagebatchresizer.resizer.*;

import java.awt.image.BufferedImage;

/**
 * Tests that the <code>ImageSaver</code> class works as expected.
 */
public class ImageSaverTests {
    private ImageSaver jpgSaver;
    private ImageSaver pngSaver;
    private ImageSaver jpgPrefixedSaver;
    private File dstDirectory;
    
    /**
     * Initializes the test case.
     * @throws IOException if initializing fails
     */
    @Before
    public void initialize() throws IOException {
        // Create the directory structure
        dstDirectory = new File("testimages/test");
        dstDirectory.mkdir();
        
        // Initialize savers
        jpgSaver = new JPEGImageSaver(dstDirectory, null);
        pngSaver = new PNGImageSaver(dstDirectory, "");
        jpgPrefixedSaver = new JPEGImageSaver(dstDirectory, "test_");
    }
    
    /**
     * Cleans up any created resources.
     */
    @After
    public void cleanUp() {
        for (File file : dstDirectory.listFiles())
            file.delete();
        
        dstDirectory.delete();
    }
    
    /**
     * Tests saving an image in JPEG format.
     * 
     * @throws FileNotFoundException if the test fails
     * @throws IOException if the test fails
     */
    @Test
    public void testSaveJPEG()
      throws FileNotFoundException, IOException {
        
        File srcFile           = new File("testimages/example1.jpg");
        BufferedImage srcImage = ImageUtil.loadImage(srcFile.getAbsolutePath());
        
        File dstFile           = jpgSaver.save(srcImage, srcFile);
        assertTrue(dstFile.length() > 0);
        
        BufferedImage dstImage = ImageUtil.loadImage("testimages/test/example1.jpg");
        assertEquals(dstImage.getWidth(), srcImage.getWidth());
        assertEquals(dstImage.getHeight(), srcImage.getHeight());
    }
    
    /**
     * Tests saving an image in PNG format.
     * 
     * @throws FileNotFoundException if the test fails
     * @throws IOException if the test fails
     */
    @Test
    public void testSavePNG()
      throws FileNotFoundException, IOException {
        
        File srcFile           = new File("testimages/example1.jpg");
        BufferedImage srcImage = ImageUtil.loadImage(srcFile.getAbsolutePath());
        
        File dstFile           = pngSaver.save(srcImage, srcFile);
        assertTrue(dstFile.length() > 0);
        
        BufferedImage dstImage = ImageUtil.loadImage("testimages/test/example1.png");
        assertEquals(dstImage.getWidth(), srcImage.getWidth());
        assertEquals(dstImage.getHeight(), srcImage.getHeight());
    }
    
    /**
     * Tests saving a image in JPEG format using the prefixed image saver.
     * 
     * @throws FileNotFoundException if the test fails
     * @throws IOException if the test fails
     */
    @Test
    public void testSaveJPEGPrefixed()
      throws FileNotFoundException, IOException {
        
        File srcFile           = new File("testimages/example1.jpg");
        BufferedImage srcImage = ImageUtil.loadImage(srcFile.getAbsolutePath());
        
        File dstFile           = jpgPrefixedSaver.save(srcImage, srcFile);
        assertTrue(dstFile.length() > 0);
        
        BufferedImage dstImage = ImageUtil.loadImage("testimages/test/test_example1.jpg");
        assertEquals(dstImage.getWidth(), srcImage.getWidth());
        assertEquals(dstImage.getHeight(), srcImage.getHeight());
    }
}
