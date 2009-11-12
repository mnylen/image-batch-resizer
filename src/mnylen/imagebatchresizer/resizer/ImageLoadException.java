package mnylen.imagebatchresizer.resizer;

/**
 * An exception thrown when an image could not be loaded from a file. It usually
 * means that the file did not contain valid image data, e.g. in situations where
 * the file is corrupt.
 */
public class ImageLoadException extends RuntimeException {
	/**
	 * Creates a new <code>ImageLoadException</code> using the specified message.
	 * @param message the message
	 */
	public ImageLoadException(String message) {
		super(message);
	}
}
