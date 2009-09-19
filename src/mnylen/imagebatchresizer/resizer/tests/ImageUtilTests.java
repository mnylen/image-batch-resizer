package mnylen.imagebatchresizer.resizer.tests;

import java.io.FileNotFoundException;
import java.io.IOException;

import mnylen.imagebatchresizer.resizer.*;

import org.junit.*;
import static org.junit.Assert.*;

import java.awt.image.BufferedImage;

/**
 * Tests that the <code>ImageUtil</code> class works as expected.
 */
public class ImageUtilTests {
    /**
     * Tests that loading JPEG images works as expected.
     * 
     * @throws FileNotFoundException if the test fails
     * @throws IOException if the test fails
     */
    @Test
    public void testLoadJPEG() throws FileNotFoundException, IOException {
        BufferedImage im = ImageUtil.loadImage("testimages/example1.jpg");
        assertNotNull(im);
        
        assertEquals(1024, im.getWidth());
        assertEquals(768,  im.getHeight());
    }
    
    /**
     * Tests that loading PNG images works as expected.
     * 
     * @throws FileNotFoundException if the test fails
     * @throws IOException if the test fails
     */
    @Test
    public void testLoadPNG() throws FileNotFoundException, IOException {
        BufferedImage im = ImageUtil.loadImage("testimages/example2.png");
        assertNotNull(im);
        
        assertEquals(819,  im.getWidth());
        assertEquals(1024, im.getHeight());
    }
    
    /**
     * Tests that loading BMP images works as expected.
     * 
     * @throws FileNotFoundException if the test fails
     * @throws IOException if the test fails
     */
    @Test
    public void testLoadBMP() throws FileNotFoundException, IOException {
        BufferedImage im = ImageUtil.loadImage("testimages/example4.bmp");
        assertNotNull(im);
        
        assertEquals(1024,  im.getWidth());
        assertEquals(768,   im.getHeight());
    }
    
    /**
     * Tests that loading GIF images works as expected.
     * 
     * @throws FileNotFoundException if the test fails
     * @throws IOException if the test fails
     */
    @Test
    public void testLoadGIF() throws FileNotFoundException, IOException {
        BufferedImage im = ImageUtil.loadImage("testimages/example5.gif");
        assertNotNull(im);
        
        assertEquals(1024, im.getWidth());
        assertEquals(771,  im.getHeight());
    }
    
    /**
     * Tests that the <code>ImageUtils.loadImage()</code> method behaves as
     * expected when trying to load a file that does not exist.
     * 
     * @throws FileNotFoundException if the test is successful
     * @throws IOException if the test fails
     */
    @Test(expected=FileNotFoundException.class)
    public void testLoadNonExistingFile() throws FileNotFoundException, IOException {
        ImageUtil.loadImage("foobar.bmp");
    }
    
    /**
     * Tests that the <code>ImageUtils.loadImage()</code> method behaves as
     * expected when trying to load a file that does not contain valid image
     * data.
     * 
     * @throws FileNotFoundException if the test fails
     * @throws IOException if the test fails
     */
     @Test
     public void testLoadNonImage() throws FileNotFoundException, IOException {
         BufferedImage im = ImageUtil.loadImage("testimages/nonimage.txt");
         assertNull(im);
     }
}
