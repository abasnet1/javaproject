package coincide_front_end_master;

import java.util.Scanner;

public class CoincideDriver {
	public static void main(String[] args) {
		String portName = args[0];
		String greet = "Welcome to Coincide 0.1 Alpha.";
		PollObj mojo = new PollObj(portName);
		Scanner scan = new Scanner(System.in);
		boolean menuLoop = true;
		int menuSelection = 0;
		int[] requiredParams = new int[3]; // 0: first window 1: second window 2: integration interval
		System.out.println(greet);
		setWindowWidths(scan, mojo, requiredParams);
		setInterval(scan, requiredParams);
		while(menuLoop) {
			menuPrint();
			menuSelection = Integer.parseInt(scan.nextLine());
			switch(menuSelection) { // finish this
				
			}
			
		}
		
	}
	
	private static void setWindowWidths(Scanner sc, PollObj p, int[] up) {
		String s0 = "Coincidence window width is p(2n - 1), where p is the period of the FPGA clock,";
		String s1 = "and n is the replicated pulse length in clock cycles.";
		String s2 = "Please enter the desired pulse length for observation window 1: (min 2, max 15)";
		String s3 = "Please enter the desired pulse length for observation window 2: (min 2, max 15)";
		System.out.println(s0);
		System.out.println(s1);
		System.out.print(s2);
		up[0] = Integer.parseInt(sc.nextLine());
		System.out.print(s3);
		up[1] = Integer.parseInt(sc.nextLine());
		try {
			p.writeWindowLengths(up[0], up[1]);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private static void setInterval(Scanner sc, int[] up) {
		String s0 = "Please enter the desired integration interval in tenths of a second.";
		System.out.println(s0);
		up[2] = Integer.parseInt(sc.nextLine());
	}
	
	private void getCurrentParams(int[] up) {
		String s0 = "Pulse length for observation window 1: ";
		String s1 = "Pulse length for observation window 2: ";
		String s2 = "Integration interval (tenths of a second): ";
		System.out.println(s0 + up[0]);
		System.out.println(s1 + up[1]);
		System.out.println(s2 + up[2]);
	}
	
	private int[] collectData(PollObj p, int interval) {
		int i = 0;
		int[] tmp = new int[30];
		while(i < interval) {
			try {
				if(p.newData()) {
					p.readNewData();
					p.addNewData();
					p.lowerPollFlag();
					i++;
				}
				else Thread.sleep(25);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		tmp = p.returnTotals();
		p.clearTotals();
		return tmp;
	}
	
	private void outputData(int[] d, int interval) { // finish this
		System.out.println("/tWindow 1/t/t/tWindow 2");
		System.out.println("/t--------/t/t/t--------");
		
	}
	
	private static void menuPrint() {
		String s0 = "Coincide Main Menu:";
		String s1 = "1: Collect Data";
		String s2 = "2: Change Observation Window Widths";
		String s3 = "3: Change Integration Interval";
		String s4 = "4: View Current Parameters";
		String s5 = "5: Exit";
		String s6 = "Enter Selection: ";
		System.out.println(s0);
		System.out.println();
		System.out.println(s1);
		System.out.println(s2);
		System.out.println(s3);
		System.out.println(s4);
		System.out.println(s5);
		System.out.println();
		System.out.print(s6);
	}

}
