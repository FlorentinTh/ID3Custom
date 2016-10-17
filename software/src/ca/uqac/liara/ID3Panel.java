package ca.uqac.liara;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultCaret;
import java.awt.*;

/**
 * Created by Baptiste on 10/4/2016.
 */
public class ID3Panel extends JPanel {
	public final static String OPENBTN_STR = "openButton";
	public final static String SPLITBTN_STR = "splitButton";
	public final static String CROSSBTN_STR = "crossButton";
	public final static String STARTBTN_STR = "runButton";

	private final JFileChooser fc = new JFileChooser();
	private final int FOLDS_MIN = 1;
	private final int FOLDS_MAX = 10;
	private final int FOLDS_INIT = 2;

	private int currentFoldsNumber = FOLDS_INIT;

	private JPanel leftPanel = new JPanel(new GridLayout(0, 1));
	private JPanel rightPanel = new JPanel(new GridLayout(0, 1));
	private JPanel chooseFilePanel = new JPanel(new GridLayout(0, 2));
	private JPanel chooseTrainingPanel = new JPanel(new GridLayout(1, 2));

	private JButton button = new JButton("Start");
	private JButton openButton = new JButton("Open File");

	private JRadioButton crossValidationButton = new JRadioButton("Cross-validation");
	private JRadioButton percentageSplitButton = new JRadioButton("Percentage Split");

	private ButtonGroup trainingChooseButtonGroup = new ButtonGroup();

	private JLabel datasetLabel = new JLabel("Datasets");
	private JLabel alphaLabel = new JLabel("Value of the alpha parameter");

	private JTextField alpha = new JTextField();
	private JTextField percentageSplitText = new JTextField();
	private JTextField datasetNameLabel = new JTextField("Name of the file");

	private JTextArea resultsText = new JTextArea(16, 58);
	private JScrollPane scrollPane = new JScrollPane(this.resultsText);

	private JSlider foldsNumber = new JSlider(JSlider.HORIZONTAL, FOLDS_MIN, FOLDS_MAX, FOLDS_INIT);

	private String datasetFile = "";

	public ID3Panel() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

        /* LEFT PANEL */
		MasterListener ml = new MasterListener(this);

		this.fc.addChoosableFileFilter(new ArffFilter());
		this.fc.setAcceptAllFileFilterUsed(false);

		this.alphaLabel.setLabelFor(this.alpha);
		this.datasetLabel.setLabelFor(this.chooseFilePanel);
		this.datasetNameLabel.setEnabled(false);

		this.openButton.addActionListener(ml);

		this.crossValidationButton.setSelected(true);
		this.crossValidationButton.addActionListener(ml);
		this.percentageSplitButton.addActionListener(ml);
		this.percentageSplitText.setEnabled(false);
		this.trainingChooseButtonGroup.add(crossValidationButton);
		this.trainingChooseButtonGroup.add(percentageSplitButton);

		this.foldsNumber.setMajorTickSpacing(1);
		this.foldsNumber.setMinorTickSpacing(1);
		this.foldsNumber.setPaintTicks(true);
		this.foldsNumber.setPaintLabels(true);
		this.foldsNumber.addChangeListener(ml);

		this.chooseFilePanel.add(datasetNameLabel);
		this.chooseFilePanel.add(openButton);

		this.leftPanel.add(this.datasetLabel);
		this.leftPanel.add(this.chooseFilePanel);

		this.leftPanel.add(this.alphaLabel);
		this.leftPanel.add(this.alpha);

		this.chooseTrainingPanel.add(this.crossValidationButton);
		this.chooseTrainingPanel.add(this.percentageSplitButton);
		this.leftPanel.add(this.chooseTrainingPanel);
		this.leftPanel.add(this.percentageSplitText);
		this.leftPanel.add(this.foldsNumber);

		this.button.addActionListener(ml);
		this.leftPanel.add(this.button);

		gbc.gridx = gbc.gridy = 0;
		gbc.gridwidth = gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.weightx = gbc.weighty = 70;
		this.add(this.leftPanel, gbc);

        /* RIGHT PANEL */
		DefaultCaret caret = (DefaultCaret) this.resultsText.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		this.resultsText.setEditable(false);
		this.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.scrollPane.setAutoscrolls(true);
		this.rightPanel.setBorder(new TitledBorder(new EtchedBorder(), "Results:"));
		this.rightPanel.add(this.scrollPane);

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 2;
		gbc.weightx = /*gbc.weighty = */ 20;
		gbc.insets = new Insets(2, 2, 2, 2);
		this.add(this.rightPanel, gbc);
	}

	public boolean isCrossValidation() {
		return this.crossValidationButton.isSelected();
	}

	public int getFoldsNumber() {
		return this.currentFoldsNumber;
	}

	public int getPercentageSplitNumber() {
		int ret = -1;
		try {
			ret = Integer.parseInt(this.percentageSplitText.getText());
		} catch (Exception e) {}
		return ret;
	}

	public JFileChooser getFileChooser() { return this.fc; }

	public void setDataset(String dataset, String fileName) {
		this.datasetFile = dataset;
		this.datasetNameLabel.setText(fileName);
	}

	public Object getButton(String str) {
		switch (str) {
			case OPENBTN_STR:
				return this.openButton;
			case SPLITBTN_STR:
				return this.percentageSplitButton;
			case STARTBTN_STR:
				return this.button;
			default:
				return this.crossValidationButton;
		}
	}

	public void setFolds(boolean folds) {
		this.foldsNumber.setEnabled(folds);
		this.percentageSplitText.setEnabled(!folds);
	}

	public void setCurrentFoldsNumber(int currentFoldsNumber) {
		this.currentFoldsNumber = currentFoldsNumber;
	}

	public double getAlpha() {
		double ret = -1;
		try {
			ret = Double.valueOf(this.alpha.getText());
		} catch (Exception e) {}
		return ret;
	}

	public String getDatasetPath() {
		return this.datasetFile;
	}

	public void writeResult(String toWrite) {
		this.resultsText.append(toWrite);
	}

	public void resetResultArea() {
		this.resultsText.setText("");
	}

	public boolean validateFields() {
		boolean valid = false;
		if (this.datasetFile.trim().isEmpty()) return valid;

		if (!this.alpha.getText().trim().isEmpty()) {
			double tmp = this.getAlpha();
			if (tmp > 0.0 && tmp < 1.0) {
				if (!this.isCrossValidation()) {
					if (!this.percentageSplitText.getText().trim().isEmpty()) {
						int tmp2 = this.getPercentageSplitNumber();
						if (tmp2 > 0 && tmp2 < 100) {
							valid = true;
						}
					}
				} else {
					valid = true;
				}
			}
		}
		return valid;
	}
}
