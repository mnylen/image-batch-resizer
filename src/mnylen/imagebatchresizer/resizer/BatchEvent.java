package mnylen.imagebatchresizer.resizer;

import java.util.EventObject;

import java.io.File;

/**
 * An instance of <code>BatchEvent</code> class is used to provide event data when
 * events occur in the batch progress.
 */
public class BatchEvent extends EventObject {
    private BatchResizer batchResizer;
    private BatchState state;
    private File currentFile;
    
    /**
     * Creates a new <code>BatchEvent</code> instance with the specified source of
     * the event and the <code>BatchResizer</code> used when the event did occur.
     * 
     * @param source the source of the event
     * @param resizer the <code>BatchResizer</code> instance used when the event did occur
     */
    public BatchEvent(Object source, BatchResizer batchResizer) {
        super(source);
        
        this.batchResizer = batchResizer;
        this.state        = batchResizer.getState();
        this.currentFile  = batchResizer.getCurrentFile();
    }
    
    /**
     * Gets the <code>BatchResizer</code> instance used when the event did occur
     * @return the <code>BatchResizer</code> instance used when the event did occur
     */
    public BatchResizer getBatchResizer() {
    	return batchResizer;
    }

    /**
     * Gets the current state of the batch progress.
     * @return the current state of the batch progress
     */
    public BatchState getState() {
        return state;
    }

    /**
     * Gets the current file being processed by the batch progress.
     * @return the current file being processed
     */
    public File getCurrentFile() {
        return currentFile;
    }
}
