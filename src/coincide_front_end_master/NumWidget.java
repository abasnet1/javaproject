package coincide_front_end_master;

import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class NumWidget extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JLabel labelA;
	private JRadioButton radioButton;
	private JLabel labelB;
	private JLabel labelC;
	private JLabel labelD;
	private JLabel labelP;
	private JLabel labelS;
	private int state;

	/**
	 * Create the panel.
	 */
	public NumWidget() {
		state = 0;
		radioButton = new JRadioButton("");
		add(radioButton);
		
		labelA = new JLabel("A");
		labelA.setForeground(Color.GREEN);
		labelA.setFont(new Font("Tahoma", Font.BOLD, 24));
		labelA.setEnabled(false);
		add(labelA);
		
		labelB = new JLabel("B");
		labelB.setForeground(Color.GREEN);
		labelB.setFont(new Font("Tahoma", Font.BOLD, 24));
		labelB.setEnabled(false);
		add(labelB);
		
		labelC = new JLabel("C");
		labelC.setForeground(Color.GREEN);
		labelC.setFont(new Font("Tahoma", Font.BOLD, 24));
		labelC.setEnabled(false);
		add(labelC);
		
		labelD = new JLabel("D");
		labelD.setForeground(Color.GREEN);
		labelD.setFont(new Font("Tahoma", Font.BOLD, 24));
		labelD.setEnabled(false);
		add(labelD);
		
		labelP = new JLabel("P");
		labelP.setForeground(Color.MAGENTA);
		labelP.setFont(new Font("Tahoma", Font.BOLD, 24));
		labelP.setEnabled(false);
		add(labelP);
		
		JLabel labelF = new JLabel("F");
		labelF.setForeground(Color.MAGENTA);
		labelF.setFont(new Font("Tahoma", Font.BOLD, 24));
		labelF.setEnabled(false);
		add(labelF);
		
		labelS = new JLabel("S");
		labelS.setForeground(Color.BLUE);
		labelS.setFont(new Font("Tahoma", Font.BOLD, 24));
		add(labelS);
		
		textField = new JTextField();
		textField.setBackground(Color.WHITE);
		textField.setEditable(false);
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setFont(new Font("Tahoma", Font.BOLD, 26));
		textField.setColumns(8);
		add(textField);

	}

	public boolean getLabelAEnabled() {
		return labelA.isEnabled();
	}
	public void setLabelAEnabled(boolean enabled) {
		labelA.setEnabled(enabled);
	}
	public JRadioButton getRadioButton() {
		return radioButton;
	}
	public boolean getLabelBEnabled() {
		return labelB.isEnabled();
	}
	public void setLabelBEnabled(boolean enabled_1) {
		labelB.setEnabled(enabled_1);
	}
	public boolean getLabelCEnabled() {
		return labelC.isEnabled();
	}
	public void setLabelCEnabled(boolean enabled_2) {
		labelC.setEnabled(enabled_2);
	}
	public boolean getLabelDEnabled() {
		return labelD.isEnabled();
	}
	public void setLabelDEnabled(boolean enabled_3) {
		labelD.setEnabled(enabled_3);
	}
	public boolean getLabelPEnabled() {
		return labelP.isEnabled();
	}
	public void setLabelPEnabled(boolean enabled_4) {
		labelP.setEnabled(enabled_4);
	}
	public String getLabelSText() {
		return labelS.getText();
	}
	public void setLabelSText(String text) {
		labelS.setText(text);
	}
	public boolean getTextFieldEditable() {
		return textField.isEditable();
	}
	public void setTextFieldEditable(boolean editable) {
		textField.setEditable(editable);
	}
	public String getTextFieldText() {
		return textField.getText();
	}
	public void setTextFieldText(String text_1) {
		textField.setText(text_1);
	}
	public int getState() {
		return state;
	}
	public void setState(int n) {
		state = n;
	}
}
