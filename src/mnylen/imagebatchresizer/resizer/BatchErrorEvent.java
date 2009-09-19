package mnylen.imagebatchresizer.resizer;

/**
 * An instance of <code>BatchErrorEvent</code> class is used to provide event data in
 * situations when an error occurs during the batch progress.
 */
public class BatchErrorEvent extends BatchEvent {
	private Exception exception;
	
	/**
	 * Creates a new <code>BatchErrorEvent</code> with the specified source of the
	 * event, the <code>BatchResizer</code> used when the error did occur and the
	 * exception thrown.
	 * 
	 * @param source the source of the event
	 * @param resizer the <code>BatchResizer</code> instance used when the error did occur
	 * @param ex the exception thrown
	 */
	public BatchErrorEvent(Object source, BatchResizer resizer, Exception ex) {
		super(source, resizer);
		this.exception = ex;
	}
	
	/**
	 * Gets the exception thrown when the error did occur during the batch progress.
	 * @return the exception thrown
	 */
	public Exception getException() {
		return exception;
	}
}
