

import jssc.*;

public class coincide_multichannel {

	public static void main (String[] args) throws InterruptedException {
		RegisterInterface reg = new RegisterInterface();
		while(true){
			poll(reg);
			Thread.sleep(100);
		}		
	}
	
	
	public static void poll(RegisterInterface r) throws InterruptedException {
		int fields = 15;
		float[] counts = new float[fields];
		float[] tau = new float[fields];
		String[] labels = {"A:    ","B:    ","C:    ","D:    ","AB:   ","AC:   ","AD:   ","BC:   ","BD:   ","CD:   ","ABC:  ","ABD:  ","ACD:  ","BCD:  ","ABCD: "};
		int poll_flag = 0;
		if (!r.connect("COM3")) {
			System.err.print("Failed to connect for polling!");
			System.exit(1);
		}
		try {
			poll_flag = r.read(98);
			if (poll_flag != 0) {
				for(int i = 0; i < fields; i++)	counts[i] = r.read(i);
				tau[4] = (counts[4]/(counts[0]*counts[1]))*1000000000;
				tau[5] = (counts[5]/(counts[0]*counts[2]))*1000000000;
				tau[6] = (counts[6]/(counts[0]*counts[3]))*1000000000;
				tau[7] = (counts[7]/(counts[1]*counts[2]))*1000000000;
				tau[8] = (counts[8]/(counts[1]*counts[3]))*1000000000;
				tau[9] = (counts[9]/(counts[2]*counts[3]))*1000000000;
				tau[10] = (counts[10]/(counts[0]*counts[1]*counts[2]))*1000000000;
				tau[11] = (counts[11]/(counts[0]*counts[1]*counts[3]))*1000000000;
				tau[12] = (counts[12]/(counts[0]*counts[2]*counts[3]))*1000000000;
				tau[13] = (counts[13]/(counts[1]*counts[2]*counts[3]))*1000000000;
				tau[14] = (counts[14]/(counts[0]*counts[1]*counts[2]*counts[3]))*1000000000;
				
				for(int j = 0; j < fields; j++) {
					System.out.printf(labels[j] + "%,15d%n",(long)counts[j]);
					if(j > 3) System.out.printf("Tau: " + "%,15f%n%n", tau[j]);
				}
				r.write(98, 0);
				poll_flag = 0;
			}
		} catch (SerialPortException e) {
					e.printStackTrace();
		} catch (SerialPortTimeoutException e) {
					e.printStackTrace();
		} finally {
		try {
			r.disconnect();
			} catch (SerialPortException e) {
					e.printStackTrace();
			}
		}
		
	}
}