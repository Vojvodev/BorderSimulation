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


public class Frame2 extends JFrame {

	private JPanel contentPane;

	
	/**
	 * Create the frame.
	 */
	public Frame2() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 743, 510);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		

				// Labels for evidentations

		JLabel lblNewLabel = new JLabel("--- Police evidentation ---");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setBounds(25, 10, 186, 13);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("--- Border evidentation ---");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblNewLabel_1.setForeground(Color.RED);
		lblNewLabel_1.setBounds(25, 250, 222, 13);
		contentPane.add(lblNewLabel_1);
		

				// Scrollable TextAreas for police evidentation and border customs evidentation
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(35, 33, 649, 187);
		contentPane.add(scrollPane_1);
		
		JTextArea textArea = new JTextArea();
		textArea.setBackground(new Color(240, 240, 240));
		textArea.setEditable(false);
		scrollPane_1.setViewportView(textArea);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(35, 273, 649, 190);
		contentPane.add(scrollPane_2);
		
		JTextArea textArea1 = new JTextArea();
		textArea1.setBackground(new Color(240, 240, 240));
		textArea1.setEditable(false);
		scrollPane_2.setViewportView(textArea1);
		
		textArea.setText(Vehicle.readEvidentation(Vehicle.POLICE_EVIDENTATION));
		textArea1.setText(Vehicle.readEvidentation(Vehicle.BORDER_EVIDENTATION));
		
	}
}
