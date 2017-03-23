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
import javax.swing.UIManager;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JToggleButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeListener;
import jssc.SerialPortException;
import jssc.SerialPortList;
import jssc.SerialPortTimeoutException;
import javax.swing.event.ChangeEvent;
import javax.swing.border.EtchedBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.Timer;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JOptionPane;
import javax.swing.JComboBox;

public class CoincideGUI {
	
	//private static final double BIN_LENGTH = .5; // length, in seconds, of FPGA data bin
	private static final int POLL_FLAG_ADDRESS = 98; // FPGA address of polling flag
	private static final int SHORT_WINDOW_ADDRESS = 99; // FPGA address of window one width
	private static final int LONG_WINDOW_ADDRESS = 100; // FPGA address of window two width
	private static final int POLL_DELAY = 50; // how often we check for new data, in ms
	private static final int MAX_WIDGETS = 20; // the number of widgets on the data panel
	private static final int[] translate = {0, 1, 4, 2, 5, 7, 10, 3, 6, 8, 11, 9, 12, 13, 14, 15, 16, 19, 17, 20, 22, 25, 18, 21, 23, 26, 24, 27, 28, 29}; // translation matrix for data array
	private static final double clockPeriod = 6.67;
	private JFrame frame;
	
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
	 * @param reg 
	 */
	public CoincideGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @param r 
	 */
	private void initialize() {
		String workingDir = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		int[] requiredParams = {2, 2, 1, 0}; // 0: first window 1: second window 2: integration interval 3: data points
		UIManager.put("Label.disabledForeground",Color.DARK_GRAY);
		RegisterInterface r = new RegisterInterface();
		JButtonGroup radioBtnGrp = new JButtonGroup();
		ArrayList<NumWidget> numwid = new ArrayList<NumWidget>(MAX_WIDGETS);
		Timer pollTimer = new Timer(POLL_DELAY,null);
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
		GridBagLayout gbl_buttonPanel = new GridBagLayout();
		gbl_buttonPanel.columnWidths = new int[]{165, 100, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_buttonPanel.rowHeights = new int[]{48, 0};
		gbl_buttonPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_buttonPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		buttonPanel.setLayout(gbl_buttonPanel);
		
		JToggleButton btnGO = new JToggleButton("GO");
		btnGO.setForeground(Color.GREEN);
		btnGO.setFont(new Font("Tahoma", Font.BOLD, 22));
		GridBagConstraints gbc_btnGO = new GridBagConstraints();
		gbc_btnGO.fill = GridBagConstraints.BOTH;
		gbc_btnGO.gridx = 0;
		gbc_btnGO.gridy = 0;
		buttonPanel.add(btnGO, gbc_btnGO);
		
		JTextPane txtpnWindowWidth = new JTextPane();
		txtpnWindowWidth.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtpnWindowWidth.setBackground(UIManager.getColor("Button.background"));
		txtpnWindowWidth.setText("Short: " + (int)(clockPeriod*(2 * requiredParams[0] - 1)) + "ns\r\nLong: " + (int)(clockPeriod*(2 * requiredParams[1] - 1)) + "ns\r\nInterval: " + requiredParams[2]);
		GridBagConstraints gbc_txtpnWindowWidth = new GridBagConstraints();
		gbc_txtpnWindowWidth.fill = GridBagConstraints.BOTH;
		gbc_txtpnWindowWidth.gridx = 1;
		gbc_txtpnWindowWidth.gridy = 0;
		buttonPanel.add(txtpnWindowWidth, gbc_txtpnWindowWidth);
		
		JButton btnA = new JButton("A");
		btnA.setForeground(Color.GREEN);
		btnA.setFont(new Font("Tahoma", Font.BOLD, 32));
		GridBagConstraints gbc_btnA = new GridBagConstraints();
		gbc_btnA.fill = GridBagConstraints.BOTH;
		gbc_btnA.gridx = 2;
		gbc_btnA.gridy = 0;
		buttonPanel.add(btnA, gbc_btnA);
		
		JButton btnB = new JButton("B");
		btnB.setForeground(Color.GREEN);
		btnB.setFont(new Font("Tahoma", Font.BOLD, 32));
		GridBagConstraints gbc_btnB = new GridBagConstraints();
		gbc_btnB.fill = GridBagConstraints.BOTH;
		gbc_btnB.gridx = 3;
		gbc_btnB.gridy = 0;
		buttonPanel.add(btnB, gbc_btnB);
		
		JButton btnC = new JButton("C");
		btnC.setForeground(Color.GREEN);
		btnC.setFont(new Font("Tahoma", Font.BOLD, 32));
		GridBagConstraints gbc_btnC = new GridBagConstraints();
		gbc_btnC.fill = GridBagConstraints.BOTH;
		gbc_btnC.gridx = 4;
		gbc_btnC.gridy = 0;
		buttonPanel.add(btnC, gbc_btnC);
		
		JButton btnD = new JButton("D");
		btnD.setForeground(Color.GREEN);
		btnD.setFont(new Font("Tahoma", Font.BOLD, 32));
		GridBagConstraints gbc_btnD = new GridBagConstraints();
		gbc_btnD.fill = GridBagConstraints.BOTH;
		gbc_btnD.gridx = 5;
		gbc_btnD.gridy = 0;
		buttonPanel.add(btnD, gbc_btnD);
		
		JButton btnP = new JButton("P");
		btnP.setEnabled(true);
		btnP.setForeground(Color.MAGENTA);
		btnP.setFont(new Font("Tahoma", Font.BOLD, 32));
		GridBagConstraints gbc_btnP = new GridBagConstraints();
		gbc_btnP.fill = GridBagConstraints.BOTH;
		gbc_btnP.gridx = 6;
		gbc_btnP.gridy = 0;
		buttonPanel.add(btnP, gbc_btnP);
		
		JButton btnF = new JButton("F");
		btnF.setEnabled(false);
		btnF.setForeground(Color.MAGENTA);
		btnF.setFont(new Font("Tahoma", Font.BOLD, 32));
		GridBagConstraints gbc_btnF = new GridBagConstraints();
		gbc_btnF.fill = GridBagConstraints.BOTH;
		gbc_btnF.gridx = 7;
		gbc_btnF.gridy = 0;
		buttonPanel.add(btnF, gbc_btnF);
		
		JButton btnLS = new JButton("L/S");
		btnLS.setForeground(Color.BLUE);
		btnLS.setFont(new Font("Tahoma", Font.BOLD, 32));
		GridBagConstraints gbc_btnLS = new GridBagConstraints();
		gbc_btnLS.fill = GridBagConstraints.BOTH;
		gbc_btnLS.gridx = 8;
		gbc_btnLS.gridy = 0;
		buttonPanel.add(btnLS, gbc_btnLS);
		
		JButton btnNG = new JButton("#/G");
		btnNG.setEnabled(false);
		btnNG.setFont(new Font("Tahoma", Font.BOLD, 32));
		GridBagConstraints gbc_btnNG = new GridBagConstraints();
		gbc_btnNG.fill = GridBagConstraints.BOTH;
		gbc_btnNG.gridx = 9;
		gbc_btnNG.gridy = 0;
		buttonPanel.add(btnNG, gbc_btnNG);
		
		JPanel dataPanel = new JPanel();
		dataPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		data.add(dataPanel, BorderLayout.CENTER);
		dataPanel.setLayout(new GridLayout(5, 4, 1, 1));
		
		
		for(int j = 0; j < 20; j++) {
			
				
				JPanel panel = new JPanel();
				panel.setBorder(new LineBorder(new Color(0, 0, 0)));
				dataPanel.add(panel);
				panel.setLayout(new GridLayout(0, 1, 0, 0));
		
				
				NumWidget numWidget_0 = new NumWidget();
				panel.add(numWidget_0);
				numWidget_0.setBorder(null);
				radioBtnGrp.add(numWidget_0.getRadioButton());
				numWidget_0.setState(0);
				numwid.add(numWidget_0);
		}
		numwid.get(0).getRadioButton().setSelected(true);
		

		JPanel parameters = new JPanel();
		tabbedPane.addTab("Parameters", null, parameters, null);
		parameters.setLayout(new GridLayout(7, 2, 0, 0));
		
		JLabel lblWindow1 = new JLabel("Short Window Width:");
		lblWindow1.setHorizontalAlignment(SwingConstants.CENTER);
		lblWindow1.setFont(new Font("Tahoma", Font.PLAIN, 40));
		parameters.add(lblWindow1);
		
		JSpinner spinnerWindow1 = new JSpinner();
		spinnerWindow1.setModel(new SpinnerNumberModel(2, 2, 15, 1));
		spinnerWindow1.setFont(new Font("Tahoma", Font.PLAIN, 50));
		parameters.add(spinnerWindow1);
		
		JLabel lblWindow2 = new JLabel("Long Window Width:");
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
		
		JSpinner spinnerDataPoints = new JSpinner();
		spinnerDataPoints.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		spinnerDataPoints.setFont(new Font("Tahoma", Font.PLAIN, 50));
		parameters.add(spinnerDataPoints);
		
		JLabel lblOutputFilename = new JLabel("Output Filename:");
		lblOutputFilename.setFont(new Font("Tahoma", Font.PLAIN, 40));
		lblOutputFilename.setHorizontalAlignment(SwingConstants.CENTER);
		parameters.add(lblOutputFilename);
		
		JTextField outputFilenameTextField = new JTextField();
		outputFilenameTextField.setHorizontalAlignment(SwingConstants.CENTER);
		outputFilenameTextField.setFont(new Font("Tahoma", Font.PLAIN, 24));
		parameters.add(outputFilenameTextField);
		outputFilenameTextField.setColumns(10);
		
		JLabel serialLabel = new JLabel("Serial Port:");
		serialLabel.setHorizontalAlignment(SwingConstants.CENTER);
		serialLabel.setFont(new Font("Tahoma", Font.PLAIN, 40));
		parameters.add(serialLabel);
		
		JComboBox<String> serialList = new JComboBox<String>(SerialPortList.getPortNames());
		serialList.setFont(new Font("Tahoma", Font.PLAIN, 40));
		parameters.add(serialList);
		
		JToggleButton tglbtnWrite = new JToggleButton("WRITE");
		tglbtnWrite.setForeground(Color.GREEN);
		tglbtnWrite.setFont(new Font("Tahoma", Font.BOLD, 40));
		parameters.add(tglbtnWrite);
		
		JToggleButton tglbtnConnect = new JToggleButton("CONNECT");
		tglbtnConnect.setForeground(Color.GREEN);
		tglbtnConnect.setFont(new Font("Tahoma", Font.BOLD, 40));
		parameters.add(tglbtnConnect);
		
		// Action Listeners
		// TODO: move all action/change/event listeners down here

		pollTimer.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				int k = 0;
				// do the things
				try {
					if(r.read(POLL_FLAG_ADDRESS) != 0) {
						k++;
						for(int i = 0; i < MAX_WIDGETS; i++) {
							NumWidget tmp = numwid.get(i);
							if(tmp.getState() != 0) {
								tmp.setAcc(tmp.getAcc() + r.read(translate[tmp.getState() - 1]));
								if(k != 0 && k%requiredParams[2] == 0) {
									tmp.setTextFieldText(String.format("%,d", tmp.getAcc()));
									tmp.setAcc(0);
								}
							}
						}
						if(requiredParams[3] != 0 && k == requiredParams[3]) pollTimer.stop();
					}
				} catch (SerialPortException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SerialPortTimeoutException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		/*frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
		        if(connected) {
		        	try {r.disconnect();} // disconnect serial port
			        catch (SerialPortException e1) {e1.printStackTrace();}
		        }
			}
		});
*/
		btnA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i = 0; i < numwid.size(); i++) {
					NumWidget tmp = numwid.get(i);
					if(tmp.getRadioButton().isSelected()) {
						if(tmp.getLabelAEnabled()) {
							tmp.setLabelAEnabled(false);
							tmp.setState(tmp.getState() - 1);
						}
						else {
							tmp.setLabelAEnabled(true);
							tmp.setState(tmp.getState() + 1);
						}
					}
				}
			}
		});

		btnB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i = 0; i < numwid.size(); i++) {
					NumWidget tmp = numwid.get(i);
					if(tmp.getRadioButton().isSelected()) {
						if(tmp.getLabelBEnabled()) {
							tmp.setLabelBEnabled(false);
							tmp.setState(tmp.getState() - 2);
						}
						else {
							tmp.setLabelBEnabled(true);
							tmp.setState(tmp.getState() + 2);
						}
					}
				}
			}
		});

		btnC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i = 0; i < numwid.size(); i++) {
					NumWidget tmp = numwid.get(i);
					if(tmp.getRadioButton().isSelected()) {
						if(tmp.getLabelCEnabled()) {
							tmp.setLabelCEnabled(false);
							tmp.setState(tmp.getState() - 4);
						}
						else {
							tmp.setLabelCEnabled(true);
							tmp.setState(tmp.getState() + 4);
						}
					}
				}
			}
		});

		btnD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i = 0; i < numwid.size(); i++) {
					NumWidget tmp = numwid.get(i);
					if(tmp.getRadioButton().isSelected()) {
						if(tmp.getLabelDEnabled()) {
							tmp.setLabelDEnabled(false);
							tmp.setState(tmp.getState() - 8);
						}
						else { 
							tmp.setLabelDEnabled(true);
							tmp.setState(tmp.getState() + 8);
						}
					}
				}
			}
		});

		btnP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i = 0; i < numwid.size(); i++) {
					NumWidget tmp = numwid.get(i);
					if(tmp.getRadioButton().isSelected()) {
						if(tmp.getLabelPEnabled()) {
							tmp.setLabelPEnabled(false);
						}
						else  {
							tmp.setLabelPEnabled(true);
							tmp.setLabelAEnabled(false);
							tmp.setLabelBEnabled(false);
							tmp.setLabelCEnabled(false);
							tmp.setLabelDEnabled(false);
						}
						tmp.setState(0);
					}
				}
			}
		});

		btnLS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i = 0; i < numwid.size(); i++) {
					NumWidget tmp = numwid.get(i);
					if(tmp.getRadioButton().isSelected()) {
						if(tmp.getLabelLSText() == "L") {
							tmp.setLabelLSText("S");
							tmp.setState(tmp.getState() - 15);
						}
						else {
							tmp.setLabelLSText("L");
							tmp.setState(tmp.getState() + 15);
						}
					}
				}
			}
		});

		spinnerWindow1.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				requiredParams[0] = (int) spinnerWindow1.getValue();
				txtpnWindowWidth.setText("Window 1: " + (int)(clockPeriod*(2 * requiredParams[0] - 1)) + "ns\r\nWindow 2: " + (int)(clockPeriod*(2 * requiredParams[1] - 1)) + "ns\r\nInterval: " + requiredParams[2]);
			}
		});

		spinnerWindow2.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				requiredParams[1] = (int) spinnerWindow2.getValue();
				txtpnWindowWidth.setText("Window 1: " + (int)(clockPeriod*(2 * requiredParams[0] - 1)) + "ns\r\nWindow 2: " + (int)(clockPeriod*(2 * requiredParams[1] - 1)) + "ns\r\nInterval: " + requiredParams[2]);
			}
		});

		spinnerIntegrationInterval.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				requiredParams[2] = (int) spinnerIntegrationInterval.getValue();
				txtpnWindowWidth.setText("Window 1: " + (int)(clockPeriod*(2 * requiredParams[0] - 1)) + "ns\r\nWindow 2: " + (int)(clockPeriod*(2 * requiredParams[1] - 1)) + "ns\r\nInterval: " + requiredParams[2]);
			}
		});

		spinnerDataPoints.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				requiredParams[3] = (int) spinnerDataPoints.getValue();
				}
		});

		tglbtnWrite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO: open/close file, make public bool ref'd by pollTimer, make timer write to file if true
				if(tglbtnWrite.isSelected()) {
					tglbtnWrite.setText("DON'T WRITE");
					tglbtnWrite.setForeground(Color.RED);
				}
				else {
					tglbtnWrite.setText("WRITE");
					tglbtnWrite.setForeground(Color.GREEN);
				}
			}
		});

		tglbtnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tglbtnConnect.isSelected()) {
					if (!r.connect(serialList.getSelectedItem().toString())) {
						JOptionPane.showMessageDialog(frame, "Failed to connect!");
						tglbtnConnect.setSelected(false);
					}
					else {
						tglbtnConnect.setText("DISCONNECT");
						tglbtnConnect.setForeground(Color.RED);
					}
				}
				else {
					tglbtnConnect.setText("CONNECT");
					tglbtnConnect.setForeground(Color.GREEN);
			        try {r.disconnect();} // disconnect serial port
			        catch (SerialPortException e1) {e1.printStackTrace();}
				}
			}
		});
		
		btnGO.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(btnGO.isSelected()) {
					if(!tglbtnConnect.isSelected()) {
						JOptionPane.showMessageDialog(frame, "Not connected!");
						btnGO.setSelected(false);
					}
					else {
						btnGO.setText("STOP");
						btnGO.setForeground(Color.RED);
						try {
							r.write(SHORT_WINDOW_ADDRESS, requiredParams[0]);
							r.write(LONG_WINDOW_ADDRESS, requiredParams[1]);
						} catch (SerialPortException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						pollTimer.start();
					}
				}
				else {
					btnGO.setText("GO");
					btnGO.setForeground(Color.GREEN);
					pollTimer.stop();
				}
			}
		});
	}
}
