package mnylen.imagebatchresizer.resizer;

/**
 * An exception thrown when trying to pass a <code>File</code> instance that
 * does not represent a directory when a directory is excepted.
 */
public class NotADirectoryException extends RuntimeException {
    /**
     * Creates a new <code>NotADirectoryException</code> with the specified
     * message.
     * 
     * @param message the message
     */
    public NotADirectoryException(String message) {
        super(message);
    }
}
