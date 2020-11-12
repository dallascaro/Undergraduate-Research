import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import javax.swing.JSlider;
import java.util.Timer;
import java.util.Date;
import java.util.Scanner;

import arduino.*;

import com.fazecast.jSerialComm.SerialPort; ;
public class LcdClock {

	static SerialPort chosenPort;
	
	public static void main(String[] args) {
		
		// configure window
		JFrame window = new JFrame();
	
		window.setTitle("LCD Clock");
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
		
		// populate dop down box
		SerialPort[] portNames = SerialPort.getCommPorts();
			for (int i = 0; i < portNames.length; i++)
				portList.addItem(portNames[i].getSystemPortName());
			
		// Connect button / Thread
		connectButton.addActionListener(new ActionListener() {
			
			@Override public void actionPerformed(ActionEvent arg0) {
				if (connectButton.getText().contentEquals("Connect")) {
					// connect to serial Port
					
					chosenPort = SerialPort.getCommPort(portList.getSelectedItem().toString());
					chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
					if (chosenPort.openPort()) {
						connectButton.setText("Disconnect");
						portList.setEnabled(false);
						
					
				// create new thread to send data
						Thread thread = new Thread() {
							@Override public void run() {
								// wait after connecting
								try {Thread.sleep(100);} catch(Exception e) {}
								
					// send text data
								PrintWriter output = new PrintWriter(chosenPort.getOutputStream());
					
					// enter infinite loop sends text
								while(true) {
									output.print(new SimpleDateFormat("hh:mm:ss a     MMMMMMMMMMdd,yyyy").format(new Date()));
									output.flush();
									
									try {Thread.sleep(100);} catch(Exception e) {}
								}
								
							}
						};
				
						thread.start();
					}
				} else {
					
					// disconnect from serialPort
					
					chosenPort.closePort();
					portList.setEnabled(true);
					connectButton.setText("Connect");
					
				}
			}
		});
		
		// show the window
		
		window.setVisible(true);
		
		
	}

}
