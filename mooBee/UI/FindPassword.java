package UI;

import java.awt.EventQueue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JTextField;

import user.UserMgr;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;

public class FindPassword {

	private JFrame frame;
	private JPasswordField Password_Field;
	private JPasswordField RePassword_Field;
	private static String userId;
	private UserMgr userMgr;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FindPassword window = new FindPassword(userId);

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
	public FindPassword(String userId) {
		this.userId = userId;

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

		Password_Field = new JPasswordField();
		Password_Field.setBounds(46, 51, 310, 43);
		Password_Field.setEchoChar('*');
		panel.add(Password_Field);
		Password_Field.setColumns(10);

		RePassword_Field = new JPasswordField();
		RePassword_Field.setColumns(10);
		RePassword_Field.setEchoChar('*');
		RePassword_Field.setBounds(46, 131, 310, 43);
		panel.add(RePassword_Field);

		JLabel Passworkd_Label = new JLabel("비밀번호");
		Passworkd_Label.setBounds(45, 26, 57, 15);
		panel.add(Passworkd_Label);

		JLabel RePassword_Label = new JLabel("비밀번호 재설정");
		RePassword_Label.setBounds(46, 116, 201, 15);
		panel.add(RePassword_Label);

		userMgr = new UserMgr();

		JButton Confirm_Btn = new HoneyButton("확인");
		Confirm_Btn.setBounds(66, 195, 271, 34);
		panel.add(Confirm_Btn);

		Confirm_Btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String password = new String(Password_Field.getPassword());
				String rePassword = new String(RePassword_Field.getPassword());
				
				if (password.equals("") || rePassword.equals("")) {
					JOptionPane.showMessageDialog(frame, "빈 칸 없이 입력하세요.");
				}else {
					if (password.equals(rePassword)) {
						if (userMgr.updatePassword(userId, password)) {
							JOptionPane.showMessageDialog(frame, "비밀번호가 변경되었습니다.");
							frame.dispose();
						}
					} else {
						JOptionPane.showMessageDialog(frame, "비밀번호를 동일하게 입력하세요.");
					}
				}

			}
		});
		
		frame.setVisible(true);

	}
}
