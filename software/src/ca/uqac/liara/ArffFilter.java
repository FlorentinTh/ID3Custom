package ca.uqac.liara;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * Created by Baptiste on 10/3/2016.
 */
public class ArffFilter extends FileFilter {
	//Accept all directories and all gif, jpg, tiff, or png files.
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}

		String extension = Utils.getExtension(f);
		if (extension != null) {
			if (extension.equals(Utils.arff)) {
				return true;
			} else {
				return false;
			}
		}

		return false;
	}

	//The description of this filter
	public String getDescription() {
		return "Arff files";
	}
}