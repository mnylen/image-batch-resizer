package mnylen.imagebatchresizer.resizer;

import java.io.File;

/**
 * An instance of <code>BatchSaveEvent</code> class is used to provide event data when
 * a file is saved in the batch progress.
 */
public class BatchSaveEvent extends BatchEvent {
	private File dstFile;
	
	/**
	 * Creates a new <code>BatchSaveEvent</code> with the specified source of the event,
	 * the <code>BatchResizer</code> instance that saved the file and the <code>File</code>
	 * instance representing the file that was written to.
	 * 
	 * @param source the source of the event
	 * @param batchResizer the <code>BatchResizer</code> instance that saved the file
	 * @param dstFile the file that was written to
	 */
	public BatchSaveEvent(Object source, BatchResizer batchResizer, File dstFile) {
		super(source, batchResizer);
		this.dstFile = dstFile;
	}
	
	/**
	 * Gets the file that was written to by the batch progress.
	 * @return the file that was written to by the batch progress
	 */
	public File getDestinationFile() {
		return dstFile;
	}
}
