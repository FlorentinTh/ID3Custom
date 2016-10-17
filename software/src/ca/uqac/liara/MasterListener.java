package ca.uqac.liara;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by Baptiste on 10/4/2016.
 */
public class MasterListener implements ActionListener, ChangeListener {

	private ID3Panel panel;

	public MasterListener(ID3Panel pane) {
		this.panel = pane;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.panel.getButton(ID3Panel.OPENBTN_STR)) {
			int returnVal = this.panel.getFileChooser().showOpenDialog(this.panel);
			String datasetFile = "";
			String datasetFileName = "";
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = this.panel.getFileChooser().getSelectedFile();
				datasetFile = file.getAbsolutePath();
				datasetFileName = file.getName();
			}
			this.panel.setDataset(datasetFile, datasetFileName);
		} else if (e.getSource() == this.panel.getButton(ID3Panel.SPLITBTN_STR)) {
			this.panel.setFolds(false);
		} else if (e.getSource() == this.panel.getButton(ID3Panel.CROSSBTN_STR)) {
			this.panel.setFolds(true);
		} else if (e.getSource() == this.panel.getButton(ID3Panel.STARTBTN_STR)) {
			(new Thread(new ID3Runner(this.panel))).start();
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider) e.getSource();
		if (!source.getValueIsAdjusting()) {
			this.panel.setCurrentFoldsNumber(source.getValue());
		}
	}
}
