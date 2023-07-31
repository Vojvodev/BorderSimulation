package org.unibl.etf.GUI;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.logging.Level;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;

import org.unibl.etf.BorderSimulation;
import org.unibl.etf.vehicles.Vehicle;


public class Frame1 {
	private static final String PAUSE_BUTTON_IMG_FILE = "images" + File.separator + "pause.jpg";
	private static final String PLAY_BUTTON_IMG_FILE  = "images" + File.separator + "play.jpg";
	public static final String CAR_IMG_FILE		  	  = "images" + File.separator + "car.jpg";
	public static final String BUS_IMG_FILE      	  = "images" + File.separator + "bus.jpg";
	public static final String TRUCK_IMG_FILE         = "images" + File.separator + "truck.jpg";
	public static final String EMPTY_ICON_FILE 		  = "images" + File.separator + "empty.jpg";
	
	public JFrame frame;
	public JButton btnPause = new JButton();
	
	public static JLabel timer = new JLabel();
	public static JLabel lblFirstVehicle  = new JLabel();
	public static JLabel lblSecondVehicle = new JLabel();
	public static JLabel lblThirdVehicle  = new JLabel();
	public static JLabel lblFourthVehicle = new JLabel();
	public static JLabel lblFifthVehicle  = new JLabel();
	
	public static JLabel lblCount = new JLabel();
	private final JLabel lblVehiclesProcessed = new JLabel("Vehicles processed: ");
	private final JLabel lblTime = new JLabel("Time: ");
	
	public static JLabel lblP1IMG = new JLabel();
	public static JLabel lblP2IMG = new JLabel();
	public static JLabel lblP3IMG = new JLabel();
	public static JLabel lblC1IMG = new JLabel();
	public static JLabel lblC2IMG = new JLabel();
	
	
	
	/**
	 * Create the application.
	 */
	public Frame1() {
		initialize();
	}

	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 1155, 712);
		frame.setTitle("BorderSimulation");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
				// Button for pausing
		
		btnPause.setBackground(Color.WHITE);
		btnPause.setIcon(new ImageIcon(PAUSE_BUTTON_IMG_FILE));
		btnPause.setBounds(514, 0, 90, 37);
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!BorderSimulation.pause) {
					BorderSimulation.pause = true;
					btnPause.setIcon(new ImageIcon(PLAY_BUTTON_IMG_FILE));
				}
				else if(BorderSimulation.pause) {
					BorderSimulation.pause = false;
					BorderSimulation.resumeSimulation();
					btnPause.setIcon(new ImageIcon(PAUSE_BUTTON_IMG_FILE));
				}
			}
		});
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(btnPause);
		
		
		
				// Labels for terminals
		
		JLabel lblC1 = new JLabel("C1");
		lblC1.setBackground(new Color(139, 69, 19));
		lblC1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblC1.setForeground(Color.RED);
		lblC1.setBounds(609, 139, 49, 51);
		lblC1.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lblC1);
		
		JLabel lblP1 = new JLabel("P1");
		lblP1.setSize(new Dimension(50, 50));
		lblP1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblP1.setForeground(Color.BLUE);
		lblP1.setBounds(266, 213, 49, 51);
		lblP1.setHorizontalAlignment(SwingConstants.CENTER);
		lblP1.setBackground(new Color(30, 144, 255));
		frame.getContentPane().add(lblP1);
			
		JLabel lblC2 = new JLabel("C2");
		lblC2.setBackground(new Color(128, 0, 0));
		lblC2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblC2.setForeground(Color.RED);
		lblC2.setBounds(753, 294, 49, 51);
		lblC2.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lblC2);
		
		JLabel lblP2 = new JLabel("P2");
		lblP2.setSize(new Dimension(50, 50));
		lblP2.setBackground(new Color(30, 144, 255));
		lblP2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblP2.setForeground(Color.BLUE);
		lblP2.setBounds(407, 364, 49, 51);
		lblP2.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lblP2);
		
		JLabel lblP3 = new JLabel("P3");
		lblP3.setSize(new Dimension(50, 50));
		lblP3.setBackground(new Color(30, 144, 255));
		lblP3.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblP3.setForeground(Color.BLUE);
		lblP3.setBounds(555, 513, 49, 51);
		lblP3.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lblP3);
		
		
				// Timer
		
		timer.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 10));
		timer.setBorder(null);	
		timer.setHorizontalAlignment(SwingConstants.CENTER);
		timer.setBounds(1046, 10, 85, 37);
		frame.getContentPane().add(timer);
		
		
				// Labels for storing first five vehicles
		
		lblFirstVehicle.setBounds(10, 403, 231, 233);
		frame.getContentPane().add(lblFirstVehicle);
		
		lblSecondVehicle.setBounds(10, 450, 231, 186);
		frame.getContentPane().add(lblSecondVehicle);
		
		lblThirdVehicle.setBounds(10, 497, 231, 139);
		frame.getContentPane().add(lblThirdVehicle);
		
		lblFourthVehicle.setBounds(10, 544, 231, 92);
		frame.getContentPane().add(lblFourthVehicle);
		
		lblFifthVehicle.setBounds(10, 591, 231, 45);
		frame.getContentPane().add(lblFifthVehicle);
		
		
				// Labels for counting, processed and time
		
		lblCount.setBounds(139, 10, 49, 27);
		frame.getContentPane().add(lblCount); 
		
		lblVehiclesProcessed.setBounds(10, 10, 119, 27);
		frame.getContentPane().add(lblVehiclesProcessed);
		
		lblTime.setBounds(1013, 10, 62, 37);
		frame.getContentPane().add(lblTime);
		
		
				// Text areas for dashes next to terminals
		
		JTextArea textAreaP1Dash = new JTextArea();
		textAreaP1Dash.setEditable(false);
		textAreaP1Dash.setOpaque(false);
		textAreaP1Dash.setBackground(new Color(240, 240, 240));
		textAreaP1Dash.setText("\\\r\n    \\\r\n        \\\r\n            \\\r\n               \\");
		textAreaP1Dash.setBounds(318, 258, 62, 76);
		frame.getContentPane().add(textAreaP1Dash);
		
		JTextArea textAreaP2Dash = new JTextArea();
		textAreaP2Dash.setText("\\\r\n    \\\r\n        \\\r\n            \\\r\n               \\");
		textAreaP2Dash.setOpaque(false);
		textAreaP2Dash.setEditable(false);
		textAreaP2Dash.setBackground(UIManager.getColor("Button.background"));
		textAreaP2Dash.setBounds(461, 412, 62, 76);
		frame.getContentPane().add(textAreaP2Dash);
		
		JTextArea textAreaP3Dash = new JTextArea();
		textAreaP3Dash.setText("\\\r\n    \\\r\n        \\\r\n            \\\r\n               \\");
		textAreaP3Dash.setOpaque(false);
		textAreaP3Dash.setEditable(false);
		textAreaP3Dash.setBackground(UIManager.getColor("Button.background"));
		textAreaP3Dash.setBounds(609, 560, 62, 76);
		frame.getContentPane().add(textAreaP3Dash);
		
		JTextArea textAreaC1Dash = new JTextArea();
		textAreaC1Dash.setText("\\\r\n    \\\r\n        \\\r\n            \\\r\n               \\");
		textAreaC1Dash.setOpaque(false);
		textAreaC1Dash.setEditable(false);
		textAreaC1Dash.setBackground(UIManager.getColor("Button.background"));
		textAreaC1Dash.setBounds(663, 188, 62, 76);
		frame.getContentPane().add(textAreaC1Dash);
		
		JTextArea textAreaC2Dash = new JTextArea();
		textAreaC2Dash.setText("\\\r\n    \\\r\n        \\\r\n            \\\r\n               \\");
		textAreaC2Dash.setOpaque(false);
		textAreaC2Dash.setEditable(false);
		textAreaC2Dash.setBackground(UIManager.getColor("Button.background"));
		textAreaC2Dash.setBounds(806, 338, 62, 76);
		frame.getContentPane().add(textAreaC2Dash);
		
		
				// Labels for vehicle icons on the terminals, with mouse clicks
		
		lblP1IMG.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String text = lblP1IMG.getText();
				String id = text.split("#")[1];
						
				for(Vehicle v : BorderSimulation.vehicleArray) {
					if(v.getVehicleId() == Integer.parseInt(id)) {						
						if(JOptionPane.showOptionDialog(null, v + "\n\n Do you want to check information about all passengers?", "Selected vehicle", 0, 1, null, null, null) == 0) {
							JOptionPane.showMessageDialog(null, v.writePassengers());
						}
					}
				}
			}
		});
		lblP1IMG.setOpaque(true);
		lblP1IMG.setBounds(183, 290, 157, 69);
		frame.getContentPane().add(lblP1IMG);
		
		
		lblP2IMG.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String text = lblP2IMG.getText();
				String id = text.split("#")[1];
		
				for(Vehicle v : BorderSimulation.vehicleArray) {
					if(v.getVehicleId() == Integer.parseInt(id)) {
						if(JOptionPane.showOptionDialog(null, v + "\n\n Do you want to check information about all passengers?", "Selected vehicle", 0, 1, null, null, null) == 0) {
							JOptionPane.showMessageDialog(null, v.writePassengers());
						}
					}
				}
			}
		});	
		lblP2IMG.setOpaque(true);
		lblP2IMG.setBounds(306, 440, 164, 69);
		frame.getContentPane().add(lblP2IMG);
		
		
		lblP3IMG.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String text = lblP3IMG.getText();
				String id = text.split("#")[1];
					
				for(Vehicle v : BorderSimulation.vehicleArray) {
					if(v.getVehicleId() == Integer.parseInt(id)) {						
						if(JOptionPane.showOptionDialog(null, v + "\n\n Do you want to check information about all passengers?", "Selected vehicle", 0, 1, null, null, null) == 0) {
							JOptionPane.showMessageDialog(null, v.writePassengers());
						}
					}
				}
			}
		});
		lblP3IMG.setOpaque(true);
		lblP3IMG.setBounds(447, 591, 172, 69);
		frame.getContentPane().add(lblP3IMG);
		
		
		lblC1IMG.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String text = lblC1IMG.getText();
				String id = text.split("#")[1];
						
				for(Vehicle v : BorderSimulation.vehicleArray) {
					if(v.getVehicleId() == Integer.parseInt(id)) {						
						if(JOptionPane.showOptionDialog(null, v + "\n\n Do you want to check information about all passengers?", "Selected vehicle", 0, 1, null, null, null) == 0) {
							JOptionPane.showMessageDialog(null, v.writePassengers());
						}
					}
				}
			}
		});
		lblC1IMG.setOpaque(true);
		lblC1IMG.setBounds(514, 213, 157, 69);
		frame.getContentPane().add(lblC1IMG);
		
		
		lblC2IMG.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String text = lblC2IMG.getText();
				String id = text.split("#")[1];
						
				for(Vehicle v : BorderSimulation.vehicleArray) {
					if(v.getVehicleId() == Integer.parseInt(id)) {						
						if(JOptionPane.showOptionDialog(null, v + "\n\n Do you want to check information about all passengers?", "Selected vehicle", 0, 1, null, null, null) == 0) {
							JOptionPane.showMessageDialog(null, v.writePassengers());
						}
					}
				}
			}
		});
		lblC2IMG.setOpaque(true);
		lblC2IMG.setBounds(648, 364, 172, 69);
		frame.getContentPane().add(lblC2IMG);
		
		
				// Button that creates Frame2 and shows all the evidentations
		
		JButton btnShowEvidentations = new JButton("Show all evidentations");
		btnShowEvidentations.addActionListener(new ActionListener() {
			// Just invoke the Frame2
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							Frame2 frame = new Frame2();
							frame.setVisible(true);
						} catch (Exception e) {
							System.out.println("Error!");
            				BorderSimulation.GUILOGGER.log(Level.SEVERE, "Exception during the creation of logger!", e);
						}
					}
				});	
			}
		});
		btnShowEvidentations.setOpaque(false);
		btnShowEvidentations.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnShowEvidentations.setFocusTraversalKeysEnabled(false);
		btnShowEvidentations.setFocusPainted(false);
		btnShowEvidentations.setBorderPainted(false);
		btnShowEvidentations.setForeground(Color.RED);
		btnShowEvidentations.setBounds(974, 644, 157, 21);
		frame.getContentPane().add(btnShowEvidentations);
		
	}
}
