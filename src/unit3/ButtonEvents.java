package unit3;

import javax.swing.*;

public class ButtonEvents {

	JFrame window = new JFrame();
	JPanel panel = new JPanel();


	ButtonEvents(){
		setUpWindow();
		JButton btn = new JButton("CLICK ME");
		MyAL myAL = new MyAL();
		btn.addActionListener(myAL); //This says where the A.L. is
		
		
		panel.add(btn);
		window.add(panel);
		window.setVisible(true); //Important
	}

	void setUpWindow() {
		window.setSize(800,600);
		window.setTitle("Button Events");
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		new ButtonEvents();
	}

}
