package mnylen.imagebatchresizer.resizer.tests;

import mnylen.imagebatchresizer.resizer.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * Tests that the <code>BoxFittingImageResizer</code> class works as expected.
 */
public class BoxFittingImageResizerTests {
    private BoxFittingImageResizer resizer;
    
    /**
     * Initializes the test case.
     */
    @Before
    public void initialize() {
        resizer = new BoxFittingImageResizer(500, 500);
    }
    
    /**
     * Tests resizing an image.
     * 
     * <p>First the test image is loaded from <em>testimages/example1.jpg</em> and
     * then it is resized using the previously initialized <c>BoxFittingImageResizer</c>
     * instance.</p>
     * 
     * <p>Expected is that after resizing, the width and height of the resized image
     * is <c>500</c> px.</p>
     * 
     * @throws FileNotFoundException if the test fails
     * @throws IOException if the test fails
     */
    @Test
    public void testResize() throws FileNotFoundException, IOException {
        BufferedImage srcImage = ImageUtil.loadImage("testimages/example1.jpg");
        BufferedImage dstImage = resizer.resize(srcImage);
        
        assertEquals(500, dstImage.getWidth());
        assertEquals(500, dstImage.getHeight());
    }
}
