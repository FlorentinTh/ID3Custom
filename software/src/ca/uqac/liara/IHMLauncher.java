package ca.uqac.liara;

import javax.swing.*;

/**
 * Created by Baptiste on 10/3/2016.
 */
public class IHMLauncher extends JFrame {
	private ID3Panel pane = new ID3Panel();

	public IHMLauncher() {
		this.setContentPane(pane);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(600, 600);
		this.setTitle("ID3 Modified Runner");
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public static void main(String args[]) { new IHMLauncher(); }
}
