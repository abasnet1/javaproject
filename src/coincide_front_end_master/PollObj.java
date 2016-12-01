package coincide_front_end_master;

import jssc.*;

public class PollObj {
	static int LENGTH = 30;
	static int POLL_FLAG_ADDRESS = 98;
	private String portName;
	RegisterInterface reg = new RegisterInterface();
	private int[] acc = new int[LENGTH]; // array to store totals
	private int[] tmp = new int[LENGTH]; // array to store most recently polled data
	
	public PollObj(String s) {
		portName = s;
		for(int i = 0; i < LENGTH; i++) { // initialize arrays
			acc[i] = 0;
			tmp[i] = 0;
		}
	}
	
	public boolean newData() throws InterruptedException {
		boolean drdy = false;
		if (!reg.connect(portName)) {
			System.err.print("Failed to connect!");
			System.exit(1);
		}
		try {
			if(reg.read(POLL_FLAG_ADDRESS) == 1) drdy = true;
			else drdy = false;
		}
		catch (SerialPortException e) {e.printStackTrace();}
		catch (SerialPortTimeoutException e) {e.printStackTrace();}
		finally {
			try {reg.disconnect();}
			catch (SerialPortException e) {e.printStackTrace();}
		}
		return drdy;
	}
	
	public void readNewData() throws InterruptedException {
		if (!reg.connect(portName)) {
			System.err.print("Failed to connect!");
			System.exit(1);
		}
		try {reg.read(0,true,tmp);}
		catch (SerialPortException e) {e.printStackTrace();}
		catch (SerialPortTimeoutException e) {e.printStackTrace();}
		finally {
			try {reg.disconnect();}
			catch (SerialPortException e) {e.printStackTrace();}
		}
	}
	
	public void addNewData() {
		for(int i = 0; i < LENGTH; i++) acc[i] += tmp[i];
	}
	
	public int[] returnTotals() {
		return acc;
	}
	
	public void clearTotals() {
		for(int i = 0; i < LENGTH; i++) acc[i] = 0;
	}
	
	public void lowerPollFlag() throws InterruptedException {
		if (!reg.connect(portName)) {
			System.err.print("Failed to connect!");
			System.exit(1);
		}
		try {reg.write(98, 0);}
		catch (SerialPortException e) {e.printStackTrace();}
		finally {
			try {reg.disconnect();}
			catch (SerialPortException e) {e.printStackTrace();}
		}
	}
	
	public void writeWindowLengths(int x, int y) throws InterruptedException{
		if (!reg.connect(portName)) {
			System.err.print("Failed to connect!");
			System.exit(1);
		}
		try {
			reg.write(99,  x);
			reg.write(100, y);
		}
		catch (SerialPortException e) {e.printStackTrace();}
		finally {
			try {reg.disconnect();}
			catch (SerialPortException e) {e.printStackTrace();}
		}
	}

}
