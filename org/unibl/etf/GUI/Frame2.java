package org.unibl.etf.GUI;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextArea;

import org.unibl.etf.vehicles.Vehicle;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JSlider;
import java.awt.ScrollPane;


public class Frame2 extends JFrame {

	private JPanel contentPane;

	
	/**
	 * Create the frame.
	 */
	public Frame2() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 955, 564);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JLabel lblNewLabel = new JLabel("--- Police evidentation ---");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setBounds(125, 10, 186, 13);
		contentPane.add(lblNewLabel);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(35, 33, 326, 187);
		contentPane.add(scrollPane_1);
		
		JTextArea textArea = new JTextArea();
		textArea.setBackground(new Color(240, 240, 240));
		textArea.setEditable(false);
		scrollPane_1.setViewportView(textArea);
		textArea.setText(Vehicle.readEvidentation(Vehicle.POLICE_EVIDENTATION));
		
		
		
		JLabel lblNewLabel_1 = new JLabel("--- Border evidentation ---");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblNewLabel_1.setForeground(Color.RED);
		lblNewLabel_1.setBounds(125, 250, 222, 13);
		contentPane.add(lblNewLabel_1);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(35, 273, 326, 200);
		contentPane.add(scrollPane_2);
		
		JTextArea textArea1 = new JTextArea();
		scrollPane_2.setViewportView(textArea1);
		textArea1.setBounds(35, 273, 326, 200);
		contentPane.add(scrollPane_2);
		textArea1.setBackground(new Color(240, 240, 240));
		textArea1.setEditable(false);
		textArea1.setText(Vehicle.readEvidentation(Vehicle.BORDER_EVIDENTATION));
		
		
		
		JLabel lblNewLabel_2 = new JLabel("--- Fined passengers ---");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblNewLabel_2.setForeground(Color.RED);
		lblNewLabel_2.setBounds(660, 10, 159, 13);
		contentPane.add(lblNewLabel_2);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(523, 42, 369, 431);
		contentPane.add(scrollPane_3);
		
		JTextArea textArea2 = new JTextArea();
		textArea2.setLineWrap(true);
		textArea2.setEditable(false);
		textArea2.setBackground(new Color(240, 240, 240));
		scrollPane_3.setViewportView(textArea2);
		textArea2.setText(Vehicle.deserializeBadPassengers());
		
	}
}
