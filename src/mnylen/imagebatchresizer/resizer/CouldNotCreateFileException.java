package mnylen.imagebatchresizer.resizer;

/**
 * An exception thrown when creating a file or a directory fails for some
 * reason.
 */
public class CouldNotCreateFileException extends RuntimeException {
    /**
     * Creates a new <code>CouldNotCreateFileException</code> using the specified
     * message.
     * 
     * @param message the message
     */
    public CouldNotCreateFileException(String message) {
        super(message);
    }
}
