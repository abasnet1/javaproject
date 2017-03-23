package coincide_front_end_master;

import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.Component;
import java.awt.Insets;
import javax.swing.JFormattedTextField;

public class NumWidget extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFormattedTextField txtlbl;
	private JLabel labelA;
	private JRadioButton radioButton;
	private JLabel labelB;
	private JLabel labelC;
	private JLabel labelD;
	private JLabel labelP;
	private JLabel labelLS;
	private int state;
	private int accumulator;

	/**
	 * Create the panel.
	 */
	public NumWidget() {
		setBorder(null);
		state = 0;
		radioButton = new JRadioButton("");
		add(radioButton);
		
		labelA = new JLabel("A");
		labelA.setForeground(Color.GREEN);
		labelA.setFont(new Font("Tahoma", Font.BOLD, 20));
		labelA.setEnabled(false);
		add(labelA);
		
		labelB = new JLabel("B");
		labelB.setForeground(Color.GREEN);
		labelB.setFont(new Font("Tahoma", Font.BOLD, 20));
		labelB.setEnabled(false);
		add(labelB);
		
		labelC = new JLabel("C");
		labelC.setForeground(Color.GREEN);
		labelC.setFont(new Font("Tahoma", Font.BOLD, 20));
		labelC.setEnabled(false);
		add(labelC);
		
		labelD = new JLabel("D");
		labelD.setForeground(Color.GREEN);
		labelD.setFont(new Font("Tahoma", Font.BOLD, 20));
		labelD.setEnabled(false);
		add(labelD);
		
		labelP = new JLabel("P");
		labelP.setForeground(Color.MAGENTA);
		labelP.setFont(new Font("Tahoma", Font.BOLD, 20));
		labelP.setEnabled(false);
		add(labelP);
		
		JLabel labelF = new JLabel("F");
		labelF.setForeground(Color.MAGENTA);
		labelF.setFont(new Font("Tahoma", Font.BOLD, 20));
		labelF.setEnabled(false);
		add(labelF);
		
		labelLS = new JLabel("S");
		labelLS.setForeground(Color.BLUE);
		labelLS.setFont(new Font("Tahoma", Font.BOLD, 20));
		add(labelLS);
		
		txtlbl = new JFormattedTextField();
		txtlbl.setMargin(new Insets(2, 2, 6, 2));
		add(txtlbl);
		txtlbl.setBorder(null);
		txtlbl.setAlignmentY(Component.TOP_ALIGNMENT);
		txtlbl.setBackground(Color.WHITE);
		txtlbl.setEditable(false);
		txtlbl.setHorizontalAlignment(SwingConstants.CENTER);
		txtlbl.setFont(new Font("Tahoma", Font.BOLD, 29));
		txtlbl.setColumns(8);

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
	public String getLabelLSText() {
		return labelLS.getText();
	}
	public void setLabelLSText(String text) {
		labelLS.setText(text);
	}
	public boolean getTextFieldEditable() {
		return txtlbl.isEditable();
	}
	public void setTextFieldEditable(boolean editable) {
		txtlbl.setEditable(editable);
	}
	public String getTextFieldText() {
		return txtlbl.getText();
	}
	public void setTextFieldText(String text_1) {
		txtlbl.setText(text_1);
	}
	public int getState() {
		return state;
	}
	public void setState(int n) {
		state = n;
	}
	public int getAcc() {
		return accumulator;
	}
	public void setAcc(int accumulator) {
		this.accumulator = accumulator;
	}
}
