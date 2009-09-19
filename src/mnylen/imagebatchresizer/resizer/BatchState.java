package mnylen.imagebatchresizer.resizer;

/**
 * Enumerate all possible states for the batch progress.
 */
public enum BatchState {
    /**
     * The batch is currently being processed.
     */
    Processing,
    
    /**
     * The batch process is idle.
     */
    Idle,
    
    /**
     * The batch process has finished.
     */
    Finished,
}
