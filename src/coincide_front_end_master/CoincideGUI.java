package coincide_front_end_master;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextPane;
import java.awt.Color;
import java.awt.Component;

import javax.swing.UIManager;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JToggleButton;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import javax.swing.border.BevelBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.EtchedBorder;

public class CoincideGUI {

	private JFrame frame;
	private int[] translate = {0, 1, 4, 2, 5, 7, 10, 3, 6, 8, 11, 9, 12, 13, 14, 15, 16, 19, 17, 20, 22, 25, 18, 21, 23, 26, 24, 27, 28, 29}; // translation matrix for data array

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CoincideGUI window = new CoincideGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CoincideGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 480);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane);
		
		JPanel data = new JPanel();
		tabbedPane.addTab("Data", null, data, null);
		data.setLayout(new BorderLayout(0, 0));
		
		JPanel buttonPanel = new JPanel();
		data.add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.setLayout(new GridLayout(0, 4, 0, 0));
		
		JToggleButton btnGO = new JToggleButton("GO");
		btnGO.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(btnGO.isSelected()) {
					btnGO.setText("STOP");
					btnGO.setForeground(Color.RED);
				}
				else {
					btnGO.setText("GO");
					btnGO.setForeground(Color.GREEN);
				}
			}
		});
		btnGO.setForeground(Color.GREEN);
		btnGO.setFont(new Font("Tahoma", Font.BOLD, 22));
		buttonPanel.add(btnGO);
		
		JTextPane txtpnWindowWidth = new JTextPane();
		txtpnWindowWidth.setBackground(UIManager.getColor("Button.background"));
		txtpnWindowWidth.setText("Window Width: 20 ns (3 clock cycles)\r\nIntegration Interval: 30\r\nNot Writing to Disk");
		buttonPanel.add(txtpnWindowWidth);
		
		JPanel dataPanel = new JPanel();
		dataPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		data.add(dataPanel, BorderLayout.CENTER);
		dataPanel.setLayout(new GridLayout(5, 4, 1, 1));
		
		JButtonGroup radioBtnGrp = new JButtonGroup();
		ArrayList<NumWidget> numwid = new ArrayList<NumWidget>(20);
				
				JPanel panel = new JPanel();
				panel.setBorder(new LineBorder(new Color(0, 0, 0)));
				dataPanel.add(panel);
				panel.setLayout(new GridLayout(0, 1, 0, 0));
		
				
				NumWidget numWidget_0 = new NumWidget();
				panel.add(numWidget_0);
				numWidget_0.setBorder(null);
				radioBtnGrp.add(numWidget_0.getRadioButton());
				numwid.add(numWidget_0);			
				numWidget_0.getRadioButton().setSelected(true);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		dataPanel.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));
		
		NumWidget numWidget_1 = new NumWidget();
		panel_1.add(numWidget_1);
		radioBtnGrp.add(numWidget_1.getRadioButton());
		numwid.add(numWidget_1);			
		
		NumWidget numWidget_2 = new NumWidget();
		numWidget_2.setBorder(null);
		dataPanel.add(numWidget_2);
		radioBtnGrp.add(numWidget_2.getRadioButton());
		numwid.add(numWidget_2);			
		
		NumWidget numWidget_3 = new NumWidget();
		dataPanel.add(numWidget_3);
		radioBtnGrp.add(numWidget_3.getRadioButton());
		numwid.add(numWidget_3);			
		
		NumWidget numWidget_4 = new NumWidget();
		dataPanel.add(numWidget_4);
		radioBtnGrp.add(numWidget_4.getRadioButton());
		numwid.add(numWidget_4);			
		
		NumWidget numWidget_5 = new NumWidget();
		dataPanel.add(numWidget_5);
		radioBtnGrp.add(numWidget_5.getRadioButton());
		numwid.add(numWidget_5);			
		
		NumWidget numWidget_6 = new NumWidget();
		dataPanel.add(numWidget_6);
		radioBtnGrp.add(numWidget_6.getRadioButton());
		numwid.add(numWidget_6);			
		
		NumWidget numWidget_7 = new NumWidget();
		dataPanel.add(numWidget_7);
		radioBtnGrp.add(numWidget_7.getRadioButton());
		numwid.add(numWidget_7);			
		
		NumWidget numWidget_8 = new NumWidget();
		dataPanel.add(numWidget_8);
		radioBtnGrp.add(numWidget_8.getRadioButton());
		numwid.add(numWidget_8);			
		
		NumWidget numWidget_9 = new NumWidget();
		dataPanel.add(numWidget_9);
		radioBtnGrp.add(numWidget_9.getRadioButton());
		numwid.add(numWidget_9);			
		
		NumWidget numWidget_10 = new NumWidget();
		dataPanel.add(numWidget_10);
		radioBtnGrp.add(numWidget_10.getRadioButton());
		numwid.add(numWidget_10);			
		
		NumWidget numWidget_11 = new NumWidget();
		dataPanel.add(numWidget_11);
		radioBtnGrp.add(numWidget_11.getRadioButton());
		numwid.add(numWidget_11);			
		
		NumWidget numWidget_12 = new NumWidget();
		dataPanel.add(numWidget_12);
		radioBtnGrp.add(numWidget_12.getRadioButton());
		numwid.add(numWidget_12);			
		
		NumWidget numWidget_13 = new NumWidget();
		dataPanel.add(numWidget_13);
		radioBtnGrp.add(numWidget_13.getRadioButton());
		numwid.add(numWidget_13);			
		
		NumWidget numWidget_14 = new NumWidget();
		dataPanel.add(numWidget_14);
		radioBtnGrp.add(numWidget_14.getRadioButton());
		numwid.add(numWidget_14);			
		
		NumWidget numWidget_15 = new NumWidget();
		dataPanel.add(numWidget_15);
		radioBtnGrp.add(numWidget_15.getRadioButton());
		numwid.add(numWidget_15);			
		
		NumWidget numWidget_16 = new NumWidget();
		dataPanel.add(numWidget_16);
		radioBtnGrp.add(numWidget_16.getRadioButton());
		numwid.add(numWidget_16);			
		
		NumWidget numWidget_17 = new NumWidget();
		dataPanel.add(numWidget_17);
		radioBtnGrp.add(numWidget_17.getRadioButton());
		numwid.add(numWidget_17);			
		
		NumWidget numWidget_18 = new NumWidget();
		dataPanel.add(numWidget_18);
		radioBtnGrp.add(numWidget_18.getRadioButton());
		numwid.add(numWidget_18);			
		
		NumWidget numWidget_19 = new NumWidget();
		dataPanel.add(numWidget_19);
		radioBtnGrp.add(numWidget_19.getRadioButton());
		numwid.add(numWidget_19);			
		
		JPanel parameters = new JPanel();
		tabbedPane.addTab("Parameters", null, parameters, null);
		parameters.setLayout(new GridLayout(5, 2, 0, 0));
		
		JLabel lblWindow1 = new JLabel("Window 1 Width:");
		lblWindow1.setHorizontalAlignment(SwingConstants.CENTER);
		lblWindow1.setFont(new Font("Tahoma", Font.PLAIN, 40));
		parameters.add(lblWindow1);
		
		JSpinner spinnerWindow1 = new JSpinner();
		spinnerWindow1.setModel(new SpinnerNumberModel(2, 2, 15, 1));
		spinnerWindow1.setFont(new Font("Tahoma", Font.PLAIN, 50));
		parameters.add(spinnerWindow1);
		
		JLabel lblWindow2 = new JLabel("Window 2 Width:");
		lblWindow2.setFont(new Font("Tahoma", Font.PLAIN, 40));
		lblWindow2.setHorizontalAlignment(SwingConstants.CENTER);
		parameters.add(lblWindow2);
		
		JSpinner spinnerWindow2 = new JSpinner();
		spinnerWindow2.setModel(new SpinnerNumberModel(2, 2, 15, 1));
		spinnerWindow2.setFont(new Font("Tahoma", Font.PLAIN, 50));
		parameters.add(spinnerWindow2);
		
		JLabel lblIntegrationInterval = new JLabel("Integration Interval:");
		lblIntegrationInterval.setHorizontalAlignment(SwingConstants.CENTER);
		lblIntegrationInterval.setFont(new Font("Tahoma", Font.PLAIN, 40));
		parameters.add(lblIntegrationInterval);
		
		JSpinner spinnerIntegrationInterval = new JSpinner();
		spinnerIntegrationInterval.setFont(new Font("Tahoma", Font.PLAIN, 50));
		spinnerIntegrationInterval.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		parameters.add(spinnerIntegrationInterval);
		
		JLabel lblDataPoints = new JLabel("Data Points: (0 = infinite)");
		lblDataPoints.setHorizontalAlignment(SwingConstants.CENTER);
		lblDataPoints.setFont(new Font("Tahoma", Font.PLAIN, 32));
		parameters.add(lblDataPoints);
		
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		spinner.setFont(new Font("Tahoma", Font.PLAIN, 50));
		parameters.add(spinner);
		
		JToggleButton toggleButtonWriteToDisk = new JToggleButton("Write Output to Disk");
		toggleButtonWriteToDisk.setFont(new Font("Tahoma", Font.PLAIN, 38));
		parameters.add(toggleButtonWriteToDisk);
		
		JButton btnPushParameters = new JButton("Apply Parameters");
		btnPushParameters.setFont(new Font("Tahoma", Font.PLAIN, 38));
		parameters.add(btnPushParameters);
	}
}
