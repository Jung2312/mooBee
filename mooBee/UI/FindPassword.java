package UI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;

public class FindPassword {

	private JFrame frame;
	private JTextField Password_Field;
	private JTextField RePassword_Field;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FindPassword window = new FindPassword();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FindPassword() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("비밀번호 재설정 창");
		frame.setBounds(50, 20, 414, 293);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setLayout(null);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 398, 254);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		Password_Field = new JTextField();
		Password_Field.setBounds(46, 51, 310, 43);
		panel.add(Password_Field);
		Password_Field.setColumns(10);
		
		RePassword_Field = new JTextField();
		RePassword_Field.setColumns(10);
		RePassword_Field.setBounds(46, 131, 310, 43);
		panel.add(RePassword_Field);
		
		JLabel Passworkd_Label = new JLabel("비밀번호");
		Passworkd_Label.setBounds(45, 26, 57, 15);
		panel.add(Passworkd_Label);
		
		JLabel RePassword_Label = new JLabel("비밀번호 재설정");
		RePassword_Label.setBounds(46, 116, 201, 15);
		panel.add(RePassword_Label);
		
		JButton Confirm_Btn = new HoneyButton("확인");
		Confirm_Btn.setBounds(66, 195, 271, 34);
		panel.add(Confirm_Btn);
	}
}
