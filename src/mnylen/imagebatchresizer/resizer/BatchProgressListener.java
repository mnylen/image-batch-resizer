package mnylen.imagebatchresizer.resizer;

/**
 * An event listener that listens events from the batch progress.
 */
public interface BatchProgressListener {
	/**
	 * Invoked when state of the batch progress changes.
	 * @param evt the provided event data
	 */
    void stateChanged(BatchEvent evt);
    
    /**
     * Invoked when the file processed by the batch progress changes.
     * @param evt the provided event data
     */
    void fileChanged(BatchEvent evt);
    
    /**
     * Invoked when the file processed by the batch progress is saved.
     * @param evt the provided event data
     */
    void fileSaved(BatchSaveEvent evt);
    
    /**
     * Invoked when an error occurs during the batch progress.
     * @param evt the provided event data
     */
    void errorOccured(BatchErrorEvent evt);
}
