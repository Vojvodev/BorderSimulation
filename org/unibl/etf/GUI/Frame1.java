package org.unibl.etf.GUI;


import org.unibl.etf.BorderSimulation;



import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import java.awt.Dimension;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JSeparator;
import javax.swing.JEditorPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;


public class Frame1 {
	private static final String PAUSE_BUTTON_IMG_FILE = "org" + File.separator + "unibl" + File.separator + "etf" + File.separator + "images" + File.separator + "pause.jpg";
	private static final String PLAY_BUTTON_IMG_FILE  = "org" + File.separator + "unibl" + File.separator + "etf" + File.separator + "images" + File.separator + "play.jpg";
	private static final String CAR_IMG_FILE		  = "org" + File.separator + "unibl" + File.separator + "etf" + File.separator + "images" + File.separator + "car.jpg";
	private static final String BUS_IMG_FILE      	  = "org" + File.separator + "unibl" + File.separator + "etf" + File.separator + "images" + File.separator + "bus.jpg";
	private static final String TRUCK_IMG_FILE        = "org" + File.separator + "unibl" + File.separator + "etf" + File.separator + "images" + File.separator + "truck.jpg";
	
	
	
	public JFrame frame;
	public JButton btnPause = new JButton();
	public static JLabel timer = new JLabel();
	public static JLabel lblFirstVehicle  = new JLabel();
	public static JLabel lblSecondVehicle = new JLabel();
	public static JLabel lblThirdVehicle  = new JLabel();
	public static JLabel lblFourthVehicle = new JLabel();
	public static JLabel lblFifthVehicle  = new JLabel();
	public static JLabel lblCount = new JLabel();
	private final JLabel lblNewLabel = new JLabel("Vehicles processed: ");
	private final JLabel lblNewLabel_1 = new JLabel("Time: ");
	
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
		timer.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 10));
		timer.setBorder(null);
		
		
		timer.setHorizontalAlignment(SwingConstants.CENTER);
		timer.setBounds(1046, 10, 85, 37);
		frame.getContentPane().add(timer);
		
		
		
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
		lblCount.setBounds(139, 10, 49, 27);
		
		frame.getContentPane().add(lblCount);
		lblNewLabel.setBounds(10, 10, 119, 27);
		
		frame.getContentPane().add(lblNewLabel);
		lblNewLabel_1.setBounds(1013, 10, 62, 37);
		
		frame.getContentPane().add(lblNewLabel_1);
		
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setOpaque(false);
		textArea.setBackground(new Color(240, 240, 240));
		textArea.setText("\\\r\n    \\\r\n        \\\r\n            \\\r\n               \\");
		textArea.setBounds(318, 258, 62, 76);
		frame.getContentPane().add(textArea);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setText("\\\r\n    \\\r\n        \\\r\n            \\\r\n               \\");
		textArea_1.setOpaque(false);
		textArea_1.setEditable(false);
		textArea_1.setBackground(UIManager.getColor("Button.background"));
		textArea_1.setBounds(461, 412, 62, 76);
		frame.getContentPane().add(textArea_1);
		
		JTextArea textArea_1_1 = new JTextArea();
		textArea_1_1.setText("\\\r\n    \\\r\n        \\\r\n            \\\r\n               \\");
		textArea_1_1.setOpaque(false);
		textArea_1_1.setEditable(false);
		textArea_1_1.setBackground(UIManager.getColor("Button.background"));
		textArea_1_1.setBounds(609, 560, 62, 76);
		frame.getContentPane().add(textArea_1_1);
		
		JTextArea textArea_2 = new JTextArea();
		textArea_2.setText("\\\r\n    \\\r\n        \\\r\n            \\\r\n               \\");
		textArea_2.setOpaque(false);
		textArea_2.setEditable(false);
		textArea_2.setBackground(UIManager.getColor("Button.background"));
		textArea_2.setBounds(663, 188, 62, 76);
		frame.getContentPane().add(textArea_2);
		
		JTextArea textArea_3 = new JTextArea();
		textArea_3.setText("\\\r\n    \\\r\n        \\\r\n            \\\r\n               \\");
		textArea_3.setOpaque(false);
		textArea_3.setEditable(false);
		textArea_3.setBackground(UIManager.getColor("Button.background"));
		textArea_3.setBounds(806, 338, 62, 76);
		frame.getContentPane().add(textArea_3);
		
	}
}
