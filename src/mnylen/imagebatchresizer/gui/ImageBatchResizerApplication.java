package mnylen.imagebatchresizer.gui;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import mnylen.imagebatchresizer.resizer.*;
import java.awt.Font;
import java.awt.event.*;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * The Image Batch Resizer program.
 */
public class ImageBatchResizerApplication extends JFrame implements ActionListener {
	/**
	 * Creates a new <code>ImageBatchResizerApplication</code>.
	 */
	public ImageBatchResizerApplication() {
		this.setTitle("Image Batch Resizer");
		this.setResizable(false);
		
		initializeControls();
		createLayout();
	}
	
	/**
	 * Called when an action has been performed.
	 * @param evt the action event
	 */
	public void actionPerformed(ActionEvent evt) {
		if (evt.getActionCommand() == BUTTON_SIMPLEMODE) {
			simpleModeButton_Click();
		} else if (evt.getActionCommand() == BUTTON_LONGSIDEMODE) {
			longSideModeButton_Click();
		} else if (evt.getActionCommand() == BUTTON_FITTOBOXMODE) {
			fitToBoxModeButton_Click();
		} else if (evt.getActionCommand() == BUTTON_ADD) {
			addButton_Click();
		} else if (evt.getActionCommand() == BUTTON_REMOVE) {
			removeButton_Click();
		} else if (evt.getActionCommand() == BUTTON_BROWSE) {
			browseButton_Click();
		} else if (evt.getActionCommand() == BUTTON_START) {
			startButton_Click();
		}
	}
	
	/**
	 * Creates the <code>ImageSaver</code> instance based on the information
	 * user has given to us.
	 * 
	 * @return the <code>ImageSaver</code> instance created or <code>null</code>
	 *         if the instance could not be created (invalid data from the user)
	 */
	private ImageSaver createImageSaver() {
		if (dstDirectoryField.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "The destination directory is required", "Error",
					JOptionPane.ERROR_MESSAGE);
			
			return null;
		}
		
		File dstDirectory = new File(dstDirectoryField.getText());

		if (!(dstDirectory.exists())) {
			int result = JOptionPane.showConfirmDialog(this,
					"The specified directory '" + dstDirectory.getAbsolutePath() + "' does not exist. Would you like to create it?",
					"Create directory?", JOptionPane.YES_NO_OPTION);

			if (result == JOptionPane.YES_OPTION) {
				if (!(dstDirectory.mkdirs())) {
					JOptionPane.showMessageDialog(this,
							"Failed to create directory '" + dstDirectory.getAbsolutePath() + "'.",
							"Error", JOptionPane.ERROR_MESSAGE);

					return null;
				}
			} else return null;
		} else if (!(dstDirectory.isDirectory())) {
			JOptionPane.showMessageDialog(this,
					"The specified destination directory is not a directory.",
					"Error", JOptionPane.ERROR_MESSAGE);

			return null;
		}
		
		
		if (saveFormatBox.getSelectedItem().equals("PNG")) {
			return new PNGImageSaver(dstDirectory, prefixField.getText());
		} else {
			return new JPEGImageSaver(dstDirectory, prefixField.getText());
		}
	}
	
	/**
	 * Creates the <code>ImageResizer</code> instance based on the information the
	 * user has given to us.
	 * 
	 * @return the <code>ImageResizer</code> instance created or <code>null</code> if
	 *         the instance could not be created (invalid data from the user)
	 */
	private ImageResizer createImageResizer() {
		String selectedMode = modesGroup.getSelection().getActionCommand();
		ImageResizer resizer = null;
		
		if (selectedMode.equals(BUTTON_SIMPLEMODE)) {
			int width;
			int height;
			
			try {
				width  = Integer.parseInt(simpleWidthField.getText());
				height = Integer.parseInt(simpleHeightField.getText());
				
				if (width < 1 || height < 1)
					throw new IllegalArgumentException();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this,
						"The width and height must be at least 1", "Error",
						JOptionPane.ERROR_MESSAGE);
				
				return null;
			}
			
			resizer = new DefaultImageResizer(width, height);
		} else if (selectedMode.equals(BUTTON_LONGSIDEMODE)) {
			int longSide;
			
			try {
				longSide = Integer.parseInt(sideLengthField.getText());
				
				if (longSide < 1)
					throw new IllegalArgumentException();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this,
						"The side length must be at least 1", "Error",
						JOptionPane.ERROR_MESSAGE);
				
				return null;
			}
			
			resizer = new AspectKeepingImageResizer(longSide);
		} else if (selectedMode.equals(BUTTON_FITTOBOXMODE)) {
			int boxWidth, boxHeight;
			
			try {
				boxWidth  = Integer.parseInt(boxWidthField.getText());
				boxHeight = Integer.parseInt(boxHeightField.getText());
				
				if (boxWidth < 1 || boxHeight < 1)
					throw new IllegalArgumentException();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this,
						"The width and height of the box must be at least 1", "Error",
						JOptionPane.ERROR_MESSAGE);
				
				return null;
			}
			
			resizer = new BoxFittingImageResizer(boxWidth, boxHeight);
		}
		
		return resizer;
	}
	
	/**
	 * Invoked when the start button has been clicked.
	 */
	private void startButton_Click() {
		ImageSaver saver = createImageSaver();
		if (saver == null)
			return;
		
		ImageResizer resizer = createImageResizer();
		if (resizer == null)
			return;

		BatchResizer batchResizer = new BatchResizer(saver, resizer);
		for (int i = 0; i < sourceImagesList.getModel().getSize(); i++)
			batchResizer.addFile(new File(sourceImagesList.getModel().getElementAt(i).toString()));
		
		(new BatchProgressFrame(batchResizer)).setVisible(true);
		dispose();
	}
	
	/**
	 * Invoked when the radio button for simple resize mode has been clicked.
	 */
	private void simpleModeButton_Click() {
		boxWidthField.setEnabled(false);
		boxHeightField.setEnabled(false);
		sideLengthField.setEnabled(false);
		simpleWidthField.setEnabled(true);
		simpleHeightField.setEnabled(true);
	}
	
	/**
	 * Invoked when the radio button for long side resize mode has been clicked.
	 */
	private void longSideModeButton_Click() {
		simpleWidthField.setEnabled(false);
		simpleHeightField.setEnabled(false);
		boxWidthField.setEnabled(false);
		boxHeightField.setEnabled(false);
		sideLengthField.setEnabled(true);
	}
	
	/**
	 * Invoked when the radio button for fit to box resize mode has been clicked.
	 */
	private void fitToBoxModeButton_Click() {
		sideLengthField.setEnabled(false);
		simpleWidthField.setEnabled(false);
		simpleHeightField.setEnabled(false);
		boxWidthField.setEnabled(true);
		boxHeightField.setEnabled(true);
	}
	
	/**
	 * Invoked when the browse button has been clicked.
	 */
	private void browseButton_Click() {
		JFileChooser chooser = new JFileChooser();
		chooser.setMultiSelectionEnabled(false);
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			dstDirectoryField.setText(chooser.getSelectedFile().getAbsolutePath());
		}
	}
	
	/**
	 * Invoked when the remove button has been clicked.
	 */
	private void removeButton_Click() {
		DefaultListModel model = (DefaultListModel)sourceImagesList.getModel();
		
		for (Object obj : sourceImagesList.getSelectedValues()) {
			model.removeElement(obj);
		}
	}
	
	/**
	 * Invoked when the add button has been clicked.
	 */
	private void addButton_Click() {
		JFileChooser addDialog = new JFileChooser();
		
		addDialog.setMultiSelectionEnabled(true);
		addDialog.setFileFilter(new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes()));
		
		if (addDialog.showDialog(this, "Add") == JFileChooser.APPROVE_OPTION) {
			DefaultListModel model = (DefaultListModel)sourceImagesList.getModel();
			
			for (File selectedFile : addDialog.getSelectedFiles()) {	
				model.addElement(selectedFile.getAbsolutePath());
			}
		}
	}
	
	private final String BUTTON_SIMPLEMODE   = "mode-simple";
	private final String BUTTON_LONGSIDEMODE = "mode-long";
	private final String BUTTON_FITTOBOXMODE = "mode-fit";
	private final String BUTTON_BROWSE       = "browse";
	private final String BUTTON_REMOVE       = "remove";
	private final String BUTTON_ADD          = "add";
	private final String BUTTON_START        = "start";
	private JScrollPane  scrollPane;
	private JRadioButton simpleModeButton;
	private JRadioButton longSideModeButton;
	private JRadioButton fitToBoxModeButton;
	private ButtonGroup  modesGroup;
	private JTextField   simpleWidthField;
	private JTextField   simpleHeightField;
	private JTextField   boxWidthField;
	private JTextField   boxHeightField;
	private JTextField   sideLengthField;
	private JTextField   dstDirectoryField;
	private JTextField   prefixField;
	private JButton      browseButton;
	private JButton      addButton;
	private JButton      removeButton;
	private JButton      startButton;
	private JComboBox    saveFormatBox;
	private JList        sourceImagesList;
	
	/**
	 * Creates the layout using the <code>GroupLayout</code>.
	 */
	private void createLayout() {
		GroupLayout layout = new GroupLayout(getContentPane());	
		getContentPane().setLayout(layout);
		
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		JLabel resizeOptionsLabel = new JLabel("Resize options:");
		resizeOptionsLabel.setFont(resizeOptionsLabel.getFont().deriveFont(Font.BOLD));
		
		JLabel saveOptionsLabel   = new JLabel("Save options:");
		saveOptionsLabel.setFont(saveOptionsLabel.getFont().deriveFont(Font.BOLD));
		
		JLabel destinationDirectoryLabel = new JLabel("Destination directory:");
		JLabel prefixLabel               = new JLabel("Prefix saved files with:");
		JLabel saveFormatLabel           = new JLabel("Save resized images as:");
		
		JLabel selectFilesToResizeLabel  = new JLabel("Select files to resize:");
		selectFilesToResizeLabel.setFont(selectFilesToResizeLabel.getFont().deriveFont(Font.BOLD));
		
		JLabel simpleWidthXHeightLabel = new JLabel("x");
		JLabel boxWidthXHeightLabel    = new JLabel("x");
		JLabel sideLengthPxLabel       = new JLabel("px");
		JLabel simpleDimensionsPxLabel = new JLabel("px");
		JLabel boxDimensionsPxLabel    = new JLabel("px");
		
		JSeparator separator = new JSeparator();
		
		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.CENTER)
				.addGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup()
							.addComponent(resizeOptionsLabel)
							
							//
							// The options for simple resize mode
							//
							
							.addComponent(simpleModeButton)
							.addGroup(layout.createSequentialGroup()
									.addPreferredGap(simpleModeButton, simpleWidthField, ComponentPlacement.INDENT)
									.addPreferredGap(simpleModeButton, simpleWidthField, ComponentPlacement.INDENT)
									.addComponent(simpleWidthField, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(simpleWidthXHeightLabel)
									.addComponent(simpleHeightField, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(simpleDimensionsPxLabel))
	
							
							//
							// The options for "set long side to" resize mode
							//
							
							.addComponent(longSideModeButton)
							.addGroup(layout.createSequentialGroup()
									.addPreferredGap(longSideModeButton, sideLengthField, ComponentPlacement.INDENT)
									.addPreferredGap(longSideModeButton, sideLengthField, ComponentPlacement.INDENT)
									.addComponent(sideLengthField, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(sideLengthPxLabel))
							
							
							//
							// The options for fit to box resize mode
							//
							
							.addComponent(fitToBoxModeButton)
							.addGroup(layout.createSequentialGroup()
									.addPreferredGap(fitToBoxModeButton, boxWidthField, ComponentPlacement.INDENT)
									.addPreferredGap(fitToBoxModeButton, boxWidthField, ComponentPlacement.INDENT)
									.addComponent(boxWidthField, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(boxWidthXHeightLabel)
									.addComponent(boxHeightField, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(boxDimensionsPxLabel))
							
							//
							// The save options
							//
							
							.addComponent(saveOptionsLabel)
							
							//
							// The destination directory
							//
							
							.addComponent(destinationDirectoryLabel)
							.addGroup(layout.createSequentialGroup()
									.addComponent(dstDirectoryField, 130, 130, 130)
									.addComponent(browseButton))
									
							//
							// The prefix for saved files
							//
							
							.addComponent(prefixLabel)
							.addComponent(prefixField)
							
							//
							// The save format
							//
							
							.addComponent(saveFormatLabel)
							.addComponent(saveFormatBox)
							
					)
					
					//
					// The source images list
					//
					
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(layout.createSequentialGroup()
							.addGroup(layout.createParallelGroup(Alignment.TRAILING)
									.addComponent(selectFilesToResizeLabel, Alignment.LEADING)
									.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 600, GroupLayout.PREFERRED_SIZE))
							
							.addGroup(layout.createParallelGroup()
									.addComponent(addButton, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(removeButton, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))))
				
				.addComponent(separator)
				.addComponent(startButton, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
		);
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
					.addGroup(layout.createSequentialGroup()
						.addComponent(resizeOptionsLabel)
						
						//
						// The options for simple resize mode
						//
						
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(simpleModeButton)
						.addGroup(layout.createParallelGroup(Alignment.CENTER)
								.addComponent(simpleWidthField)
								.addComponent(simpleWidthXHeightLabel)
								.addComponent(simpleHeightField)
								.addComponent(simpleDimensionsPxLabel))
						
						//
						// The options for "set long side to" resize mode
						//
						
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(longSideModeButton)
						.addGroup(layout.createParallelGroup(Alignment.CENTER)
								.addComponent(sideLengthField)
								.addComponent(sideLengthPxLabel))
						
						//
						// The options for fit to box resize mode
						//
						
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(fitToBoxModeButton)
						.addGroup(layout.createParallelGroup(Alignment.CENTER)
								.addComponent(boxWidthField)
								.addComponent(boxWidthXHeightLabel)
								.addComponent(boxHeightField)
								.addComponent(boxDimensionsPxLabel))
						
						
						//
						// The save options
						//

						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(saveOptionsLabel)
						
						//
						// Destination directory
						//
						
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(destinationDirectoryLabel)
						.addGroup(layout.createParallelGroup(Alignment.CENTER)
								.addComponent(dstDirectoryField)
								.addComponent(browseButton))
						
						//
						// Prefix for resized images
						//
								
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(prefixLabel)
						.addComponent(prefixField)
						
						//
						// Save format
						//
						
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(saveFormatLabel)
						.addComponent(saveFormatBox))
					
					//
					// The source images list
					//
					
					.addGroup(layout.createSequentialGroup()
							.addComponent(selectFilesToResizeLabel)
							.addGroup(layout.createParallelGroup()
									.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
									.addGroup(layout.createSequentialGroup()
											.addComponent(addButton)
											.addComponent(removeButton)))))
				
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addComponent(separator)
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addComponent(startButton, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE));
		
		layout.linkSize(addButton, removeButton);
		pack();
	}
	
	/**
	 * Initializes the controls.
	 */
	private void initializeControls() {
		//
		// Initialize the radio buttons for choosing resize mode
		//
		simpleModeButton = new JRadioButton("Resize to size:");
		simpleModeButton.setActionCommand(BUTTON_SIMPLEMODE);
		simpleModeButton.addActionListener(this);
		simpleModeButton.setSelected(true);
		
		longSideModeButton = new JRadioButton("Resize long side to:");
		longSideModeButton.setActionCommand(BUTTON_LONGSIDEMODE);
		longSideModeButton.addActionListener(this);
		
		fitToBoxModeButton = new JRadioButton("Fit to box:");
		fitToBoxModeButton.setActionCommand(BUTTON_FITTOBOXMODE);
		fitToBoxModeButton.addActionListener(this);
		
		modesGroup = new ButtonGroup();
		modesGroup.add(simpleModeButton);
		modesGroup.add(longSideModeButton);
		modesGroup.add(fitToBoxModeButton);
		
		//
		// Initialize text fields for setting resizer options
		//
		simpleWidthField  = new JTextField(4);
		simpleHeightField = new JTextField(4);
		boxWidthField     = new JTextField(4); boxWidthField.setEnabled(false);
		boxHeightField    = new JTextField(4); boxHeightField.setEnabled(false);
		sideLengthField   = new JTextField(4); sideLengthField.setEnabled(false);
		
		//
		// Initialize controls for setting save options
		//
		dstDirectoryField = new JTextField(10);
		prefixField       = new JTextField(10);
		
		browseButton = new JButton("Browse");
		browseButton.setActionCommand(BUTTON_BROWSE);
		browseButton.addActionListener(this);
		
		saveFormatBox = new JComboBox();
		saveFormatBox.addItem("JPEG");
		saveFormatBox.addItem("PNG");
		saveFormatBox.setSelectedItem("JPEG");
		
		// 
		// Initialize controls for setting a list of source images
		//
		addButton    = new JButton("+");
		addButton.setActionCommand(BUTTON_ADD);
		addButton.addActionListener(this);
		
		removeButton = new JButton("-");
		removeButton.setActionCommand(BUTTON_REMOVE);
		removeButton.addActionListener(this);
		
		sourceImagesList = new JList(new DefaultListModel());
		
		scrollPane = new JScrollPane(sourceImagesList);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBorder(new EtchedBorder());
		
		startButton = new JButton("Start");
		startButton.setActionCommand(BUTTON_START);
		startButton.addActionListener(this);
	}
	
	/**
	 * The main program. Displays the resize options window.
	 * @param args the arguments for the application (not used)
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			// If setting the look and feel failed, there's nothing we can do. The
			// default l&f of Swing will be used instead...
		}
		
		JFrame frame = new ImageBatchResizerApplication();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
