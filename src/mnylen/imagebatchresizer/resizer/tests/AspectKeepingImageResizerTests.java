package mnylen.imagebatchresizer.resizer.tests;

import mnylen.imagebatchresizer.resizer.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * Tests that the <code>AspectKeepingImageResizer</code> class works as expected.
 */
public class AspectKeepingImageResizerTests {
    private AspectKeepingImageResizer resizer;
    
    /**
     * Initializes the test case.
     */
    @Before
    public void initialize() {
        resizer = new AspectKeepingImageResizer();
    }
    
    /**
     * Tests resizing an image.
     * 
     * <p>First we load the test image from <em>testimages/example1.jpg</em> and
     * then we resize it using the <c>AspectKeepingImageResizer.resize()</c> method.
     * Expected is that the width of the resized image is <c>600</c> and the height
     * is <c>450</c>.</p>
     * 
     * @throws FileNotFoundException if the test fails
     * @throws IOException if the test fails
     */
    @Test
    public void testResize() throws FileNotFoundException, IOException {
        BufferedImage srcImage = ImageUtil.loadImage("testimages/example1.jpg");
        BufferedImage dstImage = resizer.resize(srcImage);
        
        assertEquals(600, dstImage.getWidth());
        assertEquals(450, dstImage.getHeight());
    }
    
    /**
     * Tests resizing an image using user-specified width and height.
     * 
     * <p>First we load the test image from <em>testimages/example2.png</em> and
     * then we set the long side length of the <c>AspectKeepingImageResizer</c>
     * instance to <c>800</c>.</p>
     * 
     * <p>After this the loaded image is resized. Expected is that the width of
     * the resized image is <c>640</c> px and the height is <c>800</c> px.</p>
     * 
     * @throws FileNotFoundException if the test fails
     * @throws IOException if the test fails
     */
    @Test
    public void testResizeUserSpecified() throws FileNotFoundException, IOException {
        BufferedImage srcImage = ImageUtil.loadImage("testimages/example2.png");
        resizer.setLongSideLength(800);
        
        BufferedImage dstImage = resizer.resize(srcImage);
        assertEquals(640, dstImage.getWidth());
        assertEquals(800, dstImage.getHeight());
        
        // Do another resize
        resizer.setLongSideLength(300);
        
        dstImage = resizer.resize(srcImage);
        assertEquals(240, dstImage.getWidth());
        assertEquals(300, dstImage.getHeight());
    }
    
    /**
     * Tests setting the long side length to an illegal value (<c>-1</c>).
     * @throws IllegalArgumentException if the test succeeded
     */
    @Test(expected=IllegalArgumentException.class)
    public void testResizeWithIllegalLength() throws IllegalArgumentException {
    	resizer.setLongSideLength(-1);
    }
}
