package mnylen.imagebatchresizer.gui;

import mnylen.imagebatchresizer.resizer.*;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.EtchedBorder;

import java.awt.Dimension;
import java.awt.event.*;

/**
 * The <code>BatchProgressFrame</code> implements the view where users can see the progress
 * of the batch.
 *
 */
public class BatchProgressFrame extends JFrame implements ActionListener, BatchProgressListener, WindowListener {
	private BatchResizer batchResizer;
	
	/**
	 * Creates a new <code>BatchProgressFrame</code> with the given batch resizer.
	 * 
	 * @param batchResizer the <code>BatchResizer</code> instance to use
	 */
	public BatchProgressFrame(BatchResizer batchResizer) {
		this.setTitle("Resizing images...");
		this.setResizable(false);
		addWindowListener(this);
		initializeControls();
		createLayout();
		
		this.batchResizer = batchResizer;
	}
	
	/**
	 * Invoked when an action has been performed.
	 */
	public void actionPerformed(ActionEvent evt) {
		if (evt.getActionCommand().equals(BUTTON_EXIT)) {
			dispose();
		}
	}
	
	private final String BUTTON_EXIT = "exit";
	private JList resultList;
	private JScrollPane scrollPane;
	private JButton exitButton;
	
	/**
	 * Initializes the controls.
	 */
	private void initializeControls() {
		resultList = new JList(new DefaultListModel());
		resultList.setAutoscrolls(true);
		
		scrollPane = new JScrollPane();
		scrollPane.getViewport().setView(resultList);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(600, 300));
		scrollPane.setBorder(new EtchedBorder());
		exitButton = new JButton("Exit");
		exitButton.setActionCommand(BUTTON_EXIT);
		exitButton.setEnabled(false);
		exitButton.addActionListener(this);
	}
	
	/**
	 * Creates the layout using the <code>GroupLayout</code>.
	 */
	private void createLayout() {
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		
		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.TRAILING)
				.addComponent(scrollPane)
				.addComponent(exitButton));
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(scrollPane)
				.addComponent(exitButton));
		
		pack();
	}
		
	/**
	 * Adds a result line to the list of results.
	 */
	protected void addResultLine(String text) {
		DefaultListModel model = (DefaultListModel)resultList.getModel();
		model.addElement(text);
		
		
	}
	
	/**
	 * Invoked when an error occurs during the batch progress.
	 */
	@Override
	public void errorOccured(BatchErrorEvent evt) {
		addResultLine("ERROR - Failed to resize image: " + evt.getException().getMessage());
	}
	
	/**
	 * Invoked when the file being processed changes in the batch progress.
	 */
	@Override
	public void fileChanged(BatchEvent evt) {
		addResultLine("Resizing image from file '" + evt.getCurrentFile().getAbsolutePath() + "'...");
		
	}
	
	/**
	 * Invoked when the file being processed has been saved in the batch progress.
	 */
	@Override
	public void fileSaved(BatchSaveEvent evt) {
		addResultLine("OK - saved to '" + evt.getDestinationFile().getAbsolutePath() + "'");
		
	}
	
	/**
	 * Invoked when the state of the batch progress changes.
	 */
	@Override
	public void stateChanged(BatchEvent evt) {
		if (evt.getState() == BatchState.Finished) {
			addResultLine("");
			addResultLine("Finished!");
			exitButton.setEnabled(true);
		}
	}
	

	/**
	 * Handles the case where user tries to close the window when the
	 * batch is being processed.
	 * 
	 * @see WindowListener#windowClosing(WindowEvent)
	 */
	@Override
	public void windowClosing(WindowEvent evt) {
		if (batchResizer.getState() == BatchState.Processing) {
			JOptionPane.showMessageDialog(this, "Please wait for the resize operation to finish...");
		}
	}
	
	/**
	 * Starts processing the batch.
	 * 
	 * @see WindowListener#windowOpened(WindowEvent)
	 */
	@Override
	public void windowOpened(WindowEvent evt) {
		batchResizer.setProgressListener(this);
		batchResizer.start();
	}
	
	/**
	 * Doesn't do anything.
	 * 
	 * @see WindowListener#windowActivated(WindowEvent)
	 */
	@Override
	public void windowActivated(WindowEvent evt) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Doesn't do anything.
	 * 
	 * @see WindowListener#windowClosed(WindowEvent)
	 */
	@Override
	public void windowClosed(WindowEvent evt) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Doesn't do anything.
	 * 
	 * @see WindowListener#windowDeactivated(WindowEvent)
	 */
	@Override
	public void windowDeactivated(WindowEvent evt) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Doesn't do anything.
	 * 
	 * @see WindowListener#windowDeiconified(WindowEvent)
	 */
	@Override
	public void windowDeiconified(WindowEvent evt) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Doesn't do anything.
	 * 
	 * @see WindowListener#windowIconified(WindowEvent)
	 */
	@Override
	public void windowIconified(WindowEvent evt) {
		// TODO Auto-generated method stub
		
	}
}
