package mnylen.imagebatchresizer.resizer;

import java.util.HashSet;

import java.io.File;

import java.awt.image.BufferedImage;

/**
 * The <code>BatchResizer</code> class can be used for resizing multiple images
 * at the same time.
 */
public class BatchResizer {
    private BatchState state;
    private File currentFile;
    private ImageSaver saver;
    private ImageResizer resizer;
    private HashSet<File> files;
    private BatchProgressListener progressListener;
    private Thread resizeThread;
    
    /**
     * The object used as a synchronization root.
     */
    protected final Object syncRoot = new Object();
    
    /**
     * Updates the state of the batch progress.
     * @param state the new state of the batch progress
     */
    protected void updateState(BatchState state) {
    	synchronized (syncRoot) {
    		this.state = state;
    	}
    	
    	if (progressListener != null)
    		progressListener.stateChanged(new BatchEvent(this, this));
    }
    
    /**
     * Updates the current file being processed.
     * @param currentFile the current file being processed
     */
    protected void updateCurrentFile(File currentFile) {
    	synchronized (syncRoot) {
    		this.currentFile = currentFile;
    	}
    	
    	if (progressListener != null)
    		progressListener.fileChanged(new BatchEvent(this, this));
    }
    
    /**
     * Creates a new <code>BatchResizer</code> instance using the specified
     * <code>ImageSaver</code> and <code>ImageResizer</code> instances.
     * 
     * @param saver the <code>ImageSaver</code> instance used for saving resized images
     * @param resizer the <code>ImageResizer</code> instance used for resizing images
     * 
     * @throws IllegalArgumentException if the <code>saver</code> and/or <code>resizer</code>
     *         parameters are <code>null</code>
     */
    public BatchResizer(ImageSaver saver, ImageResizer resizer) throws IllegalArgumentException {
    	if (saver == null)
    		throw new IllegalArgumentException("the saver parameter must not be null");
    	
    	if (resizer == null)
    		throw new IllegalArgumentException("the resizer parameter must not be null");
    	
        this.saver   = saver;
        this.resizer = resizer;
        this.files   = new HashSet<File>();
        
        state        = BatchState.Idle;
        currentFile  = null;
    }
    
    /**
     * Sets the progress listener.
     * @param listener the progress listener to use
     */
    public void setProgressListener(BatchProgressListener listener) {
        synchronized (syncRoot) {
            progressListener = listener;
        }
    }
    
    /**
     * Adds a file to the batch. If the file is already in the batch, it will not
     * be added. If the current batch state is something else than
     * <code>BatchState.Idle</code>, the file will not be added. This method is
     * thread-safe.
     * 
     * @param file the file to add
     * @return <code>true</code> if the file was added; <code>false</code> otherwise
     */
    public boolean addFile(File file) {
        synchronized (syncRoot) {
            if (state != BatchState.Idle)
                return false;
            
            return files.add(file);
        }
    }
    
    /**
     * Gets the current state of the batch progress. This method is thread-safe.
     * @return the current state of the batch progress
     */
    public BatchState getState() {
        synchronized (syncRoot) {
            return state;
        }
    }
    
    /**
     * Gets the current file being processed. This method is thread-safe.
     * 
     * @return the current file being processed; <code>null</code> if no file
     *         is being processed at the moment
     */
    public File getCurrentFile() {
        synchronized (syncRoot) {
            return currentFile;
        }
    }
    
    /**
     * Starts the batch progress.
     */
    public void start() {
    	if (resizeThread != null)
    		return;
    	
    	resizeThread = new Thread(new Runnable() {
    		public void run() {
    			doRun();
    		}
    	});
    	
    	resizeThread.start();
    }
    
    /**
     * Runs the batch progress.
     */
    protected void doRun() {
    	updateState(BatchState.Processing);
    	
    	for (File file : files) {
    		updateCurrentFile(file);
    		
    		try {
    			BufferedImage srcImage = ImageUtil.loadImage(file.getAbsolutePath());
    			if (srcImage == null)
    				throw new ImageLoadException("The file does not contain image data");
    			
    			BufferedImage dstImage = resizer.resize(srcImage);
    			File dstFile = saver.save(dstImage, file);
    			
    			if (progressListener != null)
    				progressListener.fileSaved(new BatchSaveEvent(this, this, dstFile));
    			
    		} catch (Exception e) {
    			if (progressListener != null)
    				progressListener.errorOccured(new BatchErrorEvent(this, this, e));
    		}
    	}
    	
    	updateState(BatchState.Finished);
    }
}
