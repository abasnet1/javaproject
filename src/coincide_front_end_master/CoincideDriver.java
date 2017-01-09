package coincide_front_end_master;

import java.util.Scanner;

public class CoincideDriver {
	public static void main(String[] args) {
		String portName = args[0];
		String greet = "Greetings, Professor Falken. Welcome to Coincide 0.1 Alpha. Please enter initial parameters.";
		String s0 = "Please enter the number of data points you would like to collect: ";
		String s1 = "Thank you for using Coincide. Goodbye.";
		String s2 = "Invalid selection.";
		String[] labels = {"A:    ","B:    ","C:    ","D:    ","AB:   ","AC:   ","AD:   ","BC:   ","BD:   ","CD:   ","ABC:  ","ABD:  ","ACD:  ","BCD:  ","ABCD: "};
		PollObj mojo = new PollObj(portName);
		Scanner scan = new Scanner(System.in);
		boolean menuLoop = true;
		int menuSelection = 0;
		int dataPoints = 0;
		int[] requiredParams = new int[3]; // 0: first window 1: second window 2: integration interval
		int[][] last = new int[requiredParams[2]][30];
		System.out.println(greet);
		setWindowWidths(scan, mojo, requiredParams);
		setInterval(scan, requiredParams);
		while(menuLoop) {
			menuPrint();
			menuSelection = Integer.parseInt(scan.nextLine());
			switch(menuSelection) { 
				case 1: System.out.println(s0);
				   		dataPoints = Integer.parseInt(scan.nextLine());
				   		//System.out.println("Starting loop to collect at " + System.currentTimeMillis());
				   		for(int i = 0; i < dataPoints; i++) {
				   			last = collectData(mojo, requiredParams[2]);
				   			outputData(last, labels, requiredParams[2]);				   			
				   		}
				   		//System.out.println("Finished loop to collect at " + System.currentTimeMillis());
				   		//System.out.println("Finished output at " + System.currentTimeMillis());
				   		break;
				case 2: setWindowWidths(scan, mojo, requiredParams);
						break;
				case 3: setInterval(scan, requiredParams);
						break;
				case 4: getCurrentParams(requiredParams);
						break;
				case 5: //tau(last);
						System.out.println("NYI/n");
						break;
				case 6: menuLoop = false;
						System.out.println(s1);
						break;
				default: 
						System.out.print(s2);
						break;
			}
			
		}
		
	}
	
	private static void setWindowWidths(Scanner sc, PollObj p, int[] up) {
		String s0 = "Coincidence window width is p(2n - 1), where p is the period of the FPGA clock,";
		String s1 = "and n is the replicated pulse length in clock cycles.";
		String s2 = "Please enter the desired n for observation window 1 (min 2, max 15): ";
		String s3 = "Please enter the desired n for observation window 2 (min 2, max 15): ";
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
		String s0 = "Please enter the desired integration interval in halves of a second.";
		System.out.println(s0);
		up[2] = Integer.parseInt(sc.nextLine());
	}
	
	private static void getCurrentParams(int[] up) {
		String s0 = "Pulse length for observation window 1: ";
		String s1 = "Pulse length for observation window 2: ";
		String s2 = "Integration interval (tenths of a second): ";
		System.out.println(s0 + up[0]);
		System.out.println(s1 + up[1]);
		System.out.println(s2 + up[2]);
	}
	
	private static int[][] collectData(PollObj p, int interval) {
		int i = 0;
		int[][] tmp = new int[interval][30];
		p.clearTotals();
		while(i < interval) {
			try {
				if(p.newData()) {
					tmp[i] = p.readNewData();
					//p.addNewData();
					p.lowerPollFlag();
					i++;
				}
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//tmp = p.returnTotals();
		//for(int j = 0; j < tmp.length; j++) tmp[j] /= (interval/10);
		return tmp;
	}
	
	private static void outputData(int[][] last, String[] l, int interval) {
		float[] acc = new float[30];
		for(int j = 0; j < interval; j++)
			for(int k = 0; k < 30; k++)
				acc[k] += last[j][k];
		System.out.println("\t\tWindow 1\tWindow 2");
		System.out.println("\t\t--------\t--------");
		for(int i = 0; i < acc.length/2; i++)
			System.out.printf(l[i] + "%,15d" + "%,15d%n",(long)acc[i],(long)acc[i + (acc.length/2)]);
		tau(acc);
	}
	
	
	private static void menuPrint() {
		String s0 = "Coincide Main Menu:";
		String s1 = "1: Collect Data";
		String s2 = "2: Change Observation Window Widths";
		String s3 = "3: Change Integration Interval";
		String s4 = "4: View Current Parameters";
		String s5 = "5: Calculate Experimental Window Width (Tau)(NYI)";
		String s6 = "6: Exit";
		String s7 = "Enter Selection: ";
		System.out.println(s0);
		System.out.println();
		System.out.println(s1);
		System.out.println(s2);
		System.out.println(s3);
		System.out.println(s4);
		System.out.println(s5);
		System.out.println(s6);
		System.out.println();
		System.out.print(s7);
	}
	private static void tau(float[] d) { //needs refinement
		System.out.println("Tau values for last data set: ");
		System.out.printf("AB: " + "%,15f" + "%,15f%n",(d[4]/(d[0]*d[1]))*1000000000,(d[19]/(d[15]*d[16]))*1000000000);
		System.out.printf("AC: " + "%,15f" + "%,15f%n",(d[5]/(d[0]*d[2]))*1000000000,(d[20]/(d[15]*d[17]))*1000000000);
		System.out.printf("AD: " + "%,15f" + "%,15f%n",(d[6]/(d[0]*d[3]))*1000000000,(d[21]/(d[15]*d[18]))*1000000000);
		System.out.printf("BC: " + "%,15f" + "%,15f%n",(d[7]/(d[1]*d[2]))*1000000000,(d[22]/(d[16]*d[17]))*1000000000);
		System.out.printf("BD: " + "%,15f" + "%,15f%n",(d[8]/(d[1]*d[3]))*1000000000,(d[23]/(d[16]*d[18]))*1000000000);
		System.out.printf("CD: " + "%,15f" + "%,15f%n",(d[9]/(d[2]*d[3]))*1000000000,(d[24]/(d[17]*d[18]))*1000000000);
		
	}

}
