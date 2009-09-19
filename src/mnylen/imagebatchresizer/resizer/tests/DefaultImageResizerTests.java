package mnylen.imagebatchresizer.resizer.tests;

import mnylen.imagebatchresizer.resizer.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * Tests that the <code>DefaultImageResizer</code> class works as expected.
 * @author Mikko Nylén
 */
public class DefaultImageResizerTests {
    private DefaultImageResizer resizer;
    
    /**
     * Initializes the test case.
     */
    @Before
    public void initialize() {
        resizer = new DefaultImageResizer();
    }
    
    /**
     * Tests resizing an image.
     * 
     * <p>First the test image is loaded from <em>testimages/example1.jpg</em>
     * and then it's resized using the previously initialized <c>DefaultImageResizer</c>
     * instance.</p>
     * 
     * <p>After resizing, it is expected that the width of the resized image is the default
     * width specified in <c>DefaultImageResizer.DEFAULT_WIDTH</c> constant. The height should
     * be the default height specified in <c>DefaultImageResizer.DEFAULT_HEIGHT</c> constant.</p>
     * 
     * @throws FileNotFoundException if the test fails
     * @throws IOException if the test fails
     */
    @Test
    public void testResize() throws FileNotFoundException, IOException {
        BufferedImage srcImage = ImageUtil.loadImage("testimages/example1.jpg");
        BufferedImage dstImage = resizer.resize(srcImage);
        
        assertEquals(DefaultImageResizer.DEFAULT_WIDTH, dstImage.getWidth());
        assertEquals(DefaultImageResizer.DEFAULT_HEIGHT, dstImage.getHeight());
    }
    
    /**
     * Tests resizing an image.
     * 
     * <p>First the test image is loaded from <em>testimages/example1.jpg</em> and then
     * we specify the dimensions of the resized image to be <c>800 x 600</c> px.</p>
     * 
     * <p>After this the image is resized using the initialized <c>DefaultImageResizer</c>
     * instance. Expected is that after resizing the image, we have a image with the
     * previously mentioned dimensions.</p>
     * 
     * @throws FileNotFoundException if the test fails
     * @throws IOException if the test fails
     */
    @Test
    public void testResizeUserSpecified() throws FileNotFoundException, IOException {
        BufferedImage srcImage = ImageUtil.loadImage("testimages/example1.jpg");
        resizer.setWidth(800);
        resizer.setHeight(600);
        
        BufferedImage dstImage = resizer.resize(srcImage);
        assertEquals(800, dstImage.getWidth());
        assertEquals(600, dstImage.getHeight());
        
        // Do another resize
        resizer.setWidth(300);
        resizer.setHeight(800);
        
        dstImage = resizer.resize(srcImage);
        assertEquals(300, dstImage.getWidth());
        assertEquals(800, dstImage.getHeight());
    }
}
