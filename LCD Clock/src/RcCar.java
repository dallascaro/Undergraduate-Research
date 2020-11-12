import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.fazecast.jSerialComm.SerialPort;

public class RcCar {
	
	static SerialPort chosenPort;

	public static void main(String[] args) {
		
		// Creating A GUI to interface between user and Arduino
		
		JFrame window = new JFrame();
		
		window.setSize(400,75);
		window.setLayout(new BorderLayout());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// create drop box connect button
		JComboBox<String> portList = new JComboBox<String>();
		
		JButton connectButton = new JButton("Connect");
		JPanel topPanel = new JPanel();
		
		topPanel.add(portList);
		topPanel.add(connectButton);
		window.add(topPanel, BorderLayout.NORTH);
		
		SerialPort[] portNames = SerialPort.getCommPorts();
		for (int i = 0; i < portNames.length; i++)
			portList.addItem(portNames[i].getSystemPortName());
		
		// Connecting to the Arduino
		
				connectButton.addActionListener(new ActionListener() {
			
				@Override public void actionPerformed(ActionEvent arg0) {
					if (connectButton.getText().contentEquals("Connect")) {
						// connect to serial Port
						
						chosenPort = SerialPort.getCommPort(portList.getSelectedItem().toString());
						chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
						if (chosenPort.openPort()) {
							connectButton.setText("Disconnect");
							portList.setEnabled(false);
							
							Thread thread = new Thread() {
								@Override public void run() {
									// wait after connecting
									try {Thread.sleep(100);} catch(Exception e) {}
									
									
								}
							};
					
							thread.start();
							
							chosenPort.closePort();
							portList.setEnabled(true);
							connectButton.setText("Connect");
							
		// Functions of the RC Car
					/*
					 // Send data to serial port to turn car on / off
					  * 	method - getListeningEvents() // returns events passed to ports
					  * {
					  * 	call serialEvent
					  * 	(SerialPortEvent event) // must create a thread  
					  * 		//Cant handle large amounts of data Thread will process data
					  * 
					  *  	Events 
					  *  	 
     						SerialPort.LISTENING_EVENT_DATA_AVAILABLE
     						SerialPort.LISTENING_EVENT_DATA_RECEIVED
     						SerialPort.LISTENING_EVENT_DATA_WRITTEN
     						
     						events must be OR'ed received and and available cant be ORed.
     						
     						read in arduino binary data. (Control Ports)
     						
					  * 	{
					  * 	// port is open send turn on to port
					  * 	chosenPort.getInputStream() // stream of comm data
					  * 	setComPortParameters()
					  * 
					  * 	readBytes(byte [] buffer, long bytesToRead) 
					  * {
					  * 		reads serial Port bytes, stores in buffer.
					  * }
					  * 	
					  * 	writeBytes(byte[] buffer, long bytesToWrite)
					  *		 }
					  * }
					 */
						/* turn at a right degree angle, turn at a left degree angle, reverse, forward, stop, power */	
						
						}
					}
				}
			});
				// show the window
				
				window.setVisible(true);
				
	}

}
